package fi.jasoft.dragdroplayouts.client.ui.verticalsplitpanel;

import java.util.Iterator;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.Connect;
import com.vaadin.terminal.gwt.client.ui.splitpanel.VerticalSplitPanelConnector;

import fi.jasoft.dragdroplayouts.DDVerticalSplitPanel;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

@Connect(DDVerticalSplitPanel.class)
public class DDVerticalSplitPanelConnector extends VerticalSplitPanelConnector
        implements Paintable {

    /**
     * {@inheritDoc}
     */
    @Override
    public VDDVerticalSplitPanel getWidget() {
        return (VDDVerticalSplitPanel) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDVerticalSplitPanelState getState() {
        return (DDVerticalSplitPanelState) super.getState();
    }

    /**
     * {@inheritDoc}
     * 
     * @param uidl
     * @param client
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

}
