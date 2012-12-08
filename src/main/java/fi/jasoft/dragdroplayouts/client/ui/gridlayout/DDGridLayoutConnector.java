package fi.jasoft.dragdroplayouts.client.ui.gridlayout;

import java.util.Iterator;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.gridlayout.GridLayoutConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

@Connect(DDGridLayout.class)
public class DDGridLayoutConnector extends GridLayoutConnector implements
        Paintable, VHasDragMode {

    /**
     * {@inheritDoc}
     */
    @Override
    public VDDGridLayout getWidget() {
        return (VDDGridLayout) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDGridLayoutState getState() {
        return (DDGridLayoutState) super.getState();
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        super.updateFromUIDL(uidl, client);

        // Update drop handler if necessary
        UIDL c = null;
        for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
            c = (UIDL) it.next();
            if (c.getTag().equals("-ac")) {
                getWidget().updateDropHandler(c);
                break;
            }
        }

        handleDragModeUpdate(uidl);
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
     * Handles drag mode changes recieved from the server
     * 
     * @param uidl
     *            The UIDL
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
                    .setCellLeftRightDropRatio(
                            uidl.getFloatAttribute(Constants.ATTRIBUTE_HORIZONTAL_DROP_RATIO));
        }
        if (uidl.hasAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO)) {
            getState()
                    .setCellTopBottomDropRatio(
                            uidl.getFloatAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO));
        }
    }

}
