package fi.jasoft.dragdroplayouts.client.ui.tabsheet;

import java.util.Iterator;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.tabsheet.TabsheetConnector;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

public class DDTabsheetConnector extends TabsheetConnector implements Paintable {

    @Override
    public VDDTabSheet getWidget() {
        // TODO Auto-generated method stub
        return (VDDTabSheet) super.getWidget();
    }

    @Override
    public DDTabSheetState getState() {
        // TODO Auto-generated method stub
        return (DDTabSheetState) super.getState();
    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        super.updateFromUIDL(uidl, client);

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
     * Handles updates the the hoover zones of the tab which specifies at which
     * position a component is dropped over a tab
     * 
     * @param uidl
     *            The UIDL
     */
    private void handleCellDropRatioUpdate(UIDL uidl) {
        if (uidl.hasAttribute(Constants.ATTRIBUTE_HORIZONTAL_DROP_RATIO)) {
            getState()
                    .setTabLeftRightDropRatio(
                            uidl.getFloatAttribute(Constants.ATTRIBUTE_HORIZONTAL_DROP_RATIO));
        }
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
}
