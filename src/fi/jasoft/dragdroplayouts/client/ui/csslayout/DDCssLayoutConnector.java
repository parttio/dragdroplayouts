package fi.jasoft.dragdroplayouts.client.ui.csslayout;

import java.util.Iterator;

import com.vaadin.shared.ui.Connect;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.csslayout.CssLayoutConnector;

import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

@Connect(DDCssLayout.class)
public class DDCssLayoutConnector extends CssLayoutConnector implements
        Paintable, VHasDragMode {

    /**
     * {@inheritDoc}
     */
    @Override
    public VDDCssLayout getWidget() {
        return (VDDCssLayout) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDCssLayoutState getState() {
        return (DDCssLayoutState) super.getState();
    }

    /**
     * {@inheritDoc}
     */
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {

        // Drag mode
        handleDragModeUpdate(uidl);

        // Drop handlers
        UIDL c = null;
        for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
            c = (UIDL) it.next();
            if (c.getTag().equals("-ac")) {
                getWidget().updateDropHandler(c);
                break;
            }
        }

        // Handle drop ratio settings
        handleCellDropRatioUpdate(uidl);

        /*
         * Always check for iframe covers so new added/removed components get
         * covered
         */
        IframeCoverUtility iframes = getWidget().getIframeCoverUtility();
        iframes.setIframeCoversEnabled(iframes.isIframeCoversEnabled(),
                getWidget().getElement(), getState().getDragMode());

        // Drag filters
        getWidget().getDragFilter().update(uidl, client);
    }

    /**
     * Handle updates to the dragmode
     * 
     * @param uidl
     *            The recieved UIDL
     */
    private void handleDragModeUpdate(UIDL uidl) {
        if (uidl.hasAttribute(Constants.DRAGMODE_ATTRIBUTE)) {
            LayoutDragMode[] modes = LayoutDragMode.values();
            getState().setDragMode(
                    modes[uidl.getIntAttribute(Constants.DRAGMODE_ATTRIBUTE)]);
            getWidget().getMouseHandler().updateDragMode(
                    getState().getDragMode());
            getWidget()
                    .getIframeCoverUtility()
                    .setIframeCoversEnabled(
                            uidl.getBooleanAttribute(IframeCoverUtility.SHIM_ATTRIBUTE),
                            getWidget().getElement(), getState().getDragMode());
        }
    }

    /**
     * Handles updates the the hoover zones of the cell which specifies at which
     * position a component is dropped over a cell
     * 
     * @param uidl
     *            The UIDL
     */
    private void handleCellDropRatioUpdate(UIDL uidl) {
        if (uidl.hasAttribute(Constants.ATTRIBUTE_HORIZONTAL_DROP_RATIO)) {
            getState()
                    .setHorizontalDropRatio(
                            uidl.getFloatAttribute(Constants.ATTRIBUTE_HORIZONTAL_DROP_RATIO));
        }
        if (uidl.hasAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO)) {
            getState()
                    .setVerticalDropRatio(
                            uidl.getFloatAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO));
        }
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
        return getState().getDragMode();
    }

}
