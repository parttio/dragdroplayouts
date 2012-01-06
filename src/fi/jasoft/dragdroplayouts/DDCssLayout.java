package fi.jasoft.dragdroplayouts;

import java.util.Map;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.TargetDetailsImpl;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.CssLayout;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDDCssLayout;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;
import fi.jasoft.dragdroplayouts.interfaces.ShimSupport;

/**
 * CssLayout with drag and drop support
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.6.4
 *
 */
@SuppressWarnings("serial")
@ClientWidget(VDDCssLayout.class)
public class DDCssLayout extends CssLayout implements
LayoutDragSource, DropTarget, ShimSupport{
	
	/**
     * A filter for dragging components.
     */
    private DragFilter dragFilter = DragFilter.ALL;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;
    
    // Are the iframes shimmed
    private boolean iframeShims = true;
    
    // Drop handler which handles dd drop events
    private DropHandler dropHandler;
    
    /**
     * Target details for dropping on a absolute layout. 
     */
    public class CssLayoutTargetDetails extends TargetDetailsImpl{

    	/**
    	 * Constructor
    	 * 
    	 * @param rawDropData
    	 * 		The drop data
    	 */
		protected CssLayoutTargetDetails(Map<String, Object> rawDropData) {
			super(rawDropData);
		}
    }
    
    /**
     * {@inheritDoc}
     */
	public Transferable getTransferable(Map<String, Object> rawVariables) {
		return new LayoutBoundTransferable(this, rawVariables);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setShim(boolean shim) {
		if(iframeShims != shim){
   		 iframeShims = shim;
   	     requestRepaint();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isShimmed() {
		 return iframeShims;
	}

	/**
	 * gets the drop handler which handles component drops on the layout
	 */
	public DropHandler getDropHandler() {
		return dropHandler;
	}
	
	/**
     * Sets the drop handler which handles component drops on the layout
     * 
     * @param dropHandler
     *            The drop handler to set
     */
    public void setDropHandler(DropHandler dropHandler) {
    	if(this.dropHandler != dropHandler){
    		 this.dropHandler = dropHandler;
    	     requestRepaint();
    	}
    }

	/**
	 * {@inheritDoc}
	 */
	public TargetDetails translateDropTargetDetails(
			Map<String, Object> clientVariables) {
		return new CssLayoutTargetDetails(clientVariables);
	}

	/**
	 * {@inheritDoc}
	 */
	public LayoutDragMode getDragMode() {
		return dragMode;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDragMode(LayoutDragMode mode) {
		if(dragMode != mode){
    		dragMode = mode;
            requestRepaint();
    	}
	}

	/**
	 * {@inheritDoc}
	 */
	public DragFilter getDragFilter() {
		return this.dragFilter;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDragFilter(DragFilter dragFilter) {
		if(this.dragFilter != dragFilter){
			this.dragFilter = dragFilter;
			requestRepaint();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vaadin.ui.CssLayout#paintContent(com.vaadin.terminal.PaintTarget)
	 */
	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);
		
		  // Paint the drop handler criterions
        if (dropHandler != null) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        // Adds the drag mode (the default is none)
        target.addAttribute(VHasDragMode.DRAGMODE_ATTRIBUTE, dragMode.ordinal());
        
        // Should shims be used
        target.addAttribute(IframeCoverUtility.SHIM_ATTRIBUTE, iframeShims);
        
        // Paint the dragfilter into the paint target
        new DragFilterPaintable(this).paint(target);
	}
}
