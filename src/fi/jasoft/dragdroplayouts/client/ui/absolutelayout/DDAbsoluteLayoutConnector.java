package fi.jasoft.dragdroplayouts.client.ui.absolutelayout;

import java.util.Iterator;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.Connect;
import com.vaadin.terminal.gwt.client.ui.absolutelayout.AbsoluteLayoutConnector;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

@Connect(DDAbsoluteLayout.class)
public class DDAbsoluteLayoutConnector extends AbsoluteLayoutConnector
        implements Paintable {

    /**
     * {@inheritDoc}
     */
    @Override
    public VDDAbsoluteLayout getWidget() {
        return (VDDAbsoluteLayout) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDAbsoluteLayoutState getState() {
        return (DDAbsoluteLayoutState) super.getState();
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

    private void handleDragModeUpdate(UIDL uidl) {
        if (uidl.hasAttribute(Constants.DRAGMODE_ATTRIBUTE)) {
            LayoutDragMode[] modes = LayoutDragMode.values();
            getState().setDragMode(
                    modes[uidl.getIntAttribute(Constants.DRAGMODE_ATTRIBUTE)]);

            getWidget().getMouseHandler().updateDragMode(
                    getState().getDragMode());

            IframeCoverUtility iframes = getWidget().getIframeCoverUtility();
            iframes.setIframeCoversEnabled(
                    uidl.getBooleanAttribute(IframeCoverUtility.SHIM_ATTRIBUTE),
                    getWidget().getElement(), getState().getDragMode());
        }
    }
}
