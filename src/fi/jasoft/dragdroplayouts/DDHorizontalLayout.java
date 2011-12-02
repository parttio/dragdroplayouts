/*
 * Copyright 2011 John Ahlroos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.jasoft.dragdroplayouts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.TargetDetailsImpl;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDDHorizontalLayout;
import fi.jasoft.dragdroplayouts.client.ui.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;
import fi.jasoft.dragdroplayouts.interfaces.ShimSupport;


/**
 * Server side component for the VDDHorizontalLayout widget.
 */
@SuppressWarnings("serial")
@ClientWidget(VDDHorizontalLayout.class)
public class DDHorizontalLayout extends HorizontalLayout implements
        LayoutDragSource, DropTarget, ShimSupport {

    /**
     * The drop handler which handles dropped components in the layout.
     */
    private DropHandler dropHandler;

    /**
     * Specifies if dragging components is allowed and if so how it should be
     * visualized
     */
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private float horizontalDropRatio = VDDHorizontalLayout.DEFAULT_HORIZONTAL_DROP_RATIO;

    // Are the iframes shimmed
    private boolean iframeShims = true;
    
    /**
     * A filter for dragging components.
     */
    private DragFilter dragFilter = DragFilter.ALL;

    /**
     * Contains the component over which the drop was made and the index on
     * which the drop was made.
     */
    public class HorizontalLayoutTargetDetails extends TargetDetailsImpl {

        private Component over;

        private int index = -1;

        protected HorizontalLayoutTargetDetails(
                Map<String, Object> rawDropData) {
            super(rawDropData, DDHorizontalLayout.this);
            
            // Get over which component (if any) the drop was made and the
            // index of it
            Object to = rawDropData.get("to");
            if (to != null) {
                index = Integer.valueOf(to.toString());
                if(index >= 0 && index < components.size()) {
                    over = components.get(index);
                }
            }

            // Was the drop over no specific cell
            if (over == null) {
                over = DDHorizontalLayout.this;
            }
        }

        /**
         * The component over which the drop was made.
         * 
         * @return Null if the drop was not over a component, else the component
         */
        public Component getOverComponent() {
            return over;
        }

        /**
         * The index over which the drop was made. If the drop was not made over
         * any component then it returns -1.
         * 
         * @return The index of the component or -1 if over no component.
         */
        public int getOverIndex() {
            return index;
        }

        /**
         * Some details about the mouse event
         * 
         * @return details about the actual event that caused the event details.
         *         Practically mouse move or mouse up.
         */
        public MouseEventDetails getMouseEvent() {
            return MouseEventDetails
                    .deSerialize((String) getData("mouseEvent"));
        }

        /**
         * Get the horizontal position of the dropped component within the
         * underlying cell.
         * 
         * @return The drop location
         */
        public HorizontalDropLocation getDropLocation() {
            if (getData("hdetail") != null) {
                return HorizontalDropLocation
                        .valueOf((String) getData("hdetail"));
            } else {
                return null;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.ui.AbstractOrderedLayout#translateDropTargetDetails(java.util
     * .Map)
     */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        if (dropHandler != null) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        // Drop ratios
        target.addAttribute("hDropRatio", horizontalDropRatio);

        // Drag mode
        target.addAttribute("dragMode", dragMode.ordinal());

        // Shims
        target.addAttribute(IframeCoverUtility.SHIM_ATTRIBUTE, iframeShims);
        
        if(getDragFilter() != null){
        	// Get components with dragging disabled
        	Map<Component, Boolean> dragmap = new HashMap<Component, Boolean>();
        	Iterator<Component> iter = getComponentIterator();
        	while(iter.hasNext()){
        		Component c = iter.next();
        		boolean draggable = getDragFilter().isDraggable(c);
        		dragmap.put(c, draggable);
        	}
        	target.addAttribute(VDragFilter.DRAGMAP_ATTRIBUTE, dragmap);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.event.dd.DropTarget#translateDropTargetDetails(java.util.Map)
     */
    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return new HorizontalLayoutTargetDetails(clientVariables);
    }

    /**
     * Get the transferable created by a drag event.
     */
    public Transferable getTransferable(Map<String, Object> rawVariables) {
        return new LayoutBoundTransferable(this, rawVariables);
    }

    /**
     * Returns the drop handler which handles drop events from dropping
     * components on the layout. Returns Null if dropping is disabled.
     */
    public DropHandler getDropHandler() {
        return dropHandler;
    }

    /**
     * Sets the current handler which handles dropped components on the layout.
     * By setting a drop handler dropping components on the layout is enabled.
     * By setting the dropHandler to null dropping is disabled.
     * 
     * @param dropHandler
     *            The drop handler to handle drop events or null to disable
     *            dropping
     */
    public void setDropHandler(DropHandler dropHandler) {
        this.dropHandler = dropHandler;
        requestRepaint();
    }

    /**
     * Returns the mode of which dragging is visualized.
     * 
     * @return
     */
    public LayoutDragMode getDragMode() {
        return dragMode;
    }

    /**
     * Enables dragging components from the layout.
     * 
     * @param mode
     *            The mode of which how the dragging should be visualized.
     */
    public void setDragMode(LayoutDragMode mode) {
        dragMode = mode;
        requestRepaint();
    }

    /**
     * Sets the ratio which determines how a cell is divided into drop zones.
     * The ratio is measured from the left and right borders. For example,
     * setting the ratio to 0.3 will divide the drop zone in three equal parts
     * (left,middle,right). Setting the ratio to 0.5 will disable dropping in
     * the middle and setting it to 0 will disable dropping at the sides.
     * 
     * @param ratio
     *            A ratio between 0 and 0.5. Default is 0.2
     */
    public void setComponentHorizontalDropRatio(float ratio) {
        if (ratio >= 0 && ratio <= 0.5) {
            horizontalDropRatio = ratio;
            requestRepaint();
        } else {
            throw new IllegalArgumentException(
                    "Ratio must be between 0 and 0.5");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setShim(boolean shim) {
        iframeShims = shim;
        requestRepaint();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isShimmed() {
        return iframeShims;
    }
    
    /**
     * {@inheritDoc}
     */
	public DragFilter getDragFilter() {
		return dragFilter;
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
}
