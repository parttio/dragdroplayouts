package fi.jasoft.dragdroplayouts.client.ui.horizontallayout;

import java.util.Iterator;

import com.vaadin.shared.ui.Connect;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.orderedlayout.HorizontalLayoutConnector;

import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

@Connect(DDHorizontalLayout.class)
public class DDHorizontalLayoutConnector extends HorizontalLayoutConnector
        implements Paintable, VHasDragMode {

    /**
     * {@inheritDoc}
     */
    @Override
    public VDDHorizontalLayout getWidget() {
        return (VDDHorizontalLayout) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDHorizontalLayoutState getState() {
        return (DDHorizontalLayoutState) super.getState();
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
        return getState().getDragMode();
    }

    /**
     * {@inheritDoc}
     */
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {

        // Drag mode
        handleDragModeUpdate(uidl);

        // Handle drop ratio settings
        handleCellDropRatioUpdate(uidl);

        // Drop handlers
        UIDL c = null;
        for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
            c = (UIDL) it.next();
            if (c.getTag().equals("-ac")) {
                getWidget().updateDropHandler(c);
                break;
            }
        }

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
    }
}
