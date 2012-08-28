package fi.jasoft.dragdroplayouts.client.ui.accordion;

import java.util.Iterator;

import com.vaadin.shared.ui.Connect;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.accordion.AccordionConnector;

import fi.jasoft.dragdroplayouts.DDAccordion;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

@Connect(DDAccordion.class)
public class DDAccordionConnector extends AccordionConnector implements
        Paintable, VHasDragMode {

    /**
     * {@inheritDoc}
     */
    @Override
    public VDDAccordion getWidget() {
        return (VDDAccordion) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDAccordionState getState() {
        return (DDAccordionState) super.getState();
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
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        super.updateFromUIDL(uidl, client);

        // Handles changes in dropHandler
        handleDragModeUpdate(uidl);

        // Handle drop ratio settings
        handleCellDropRatioUpdate(uidl);

        // Drop handlers
        for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
            final UIDL c = (UIDL) it.next();
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
            IframeCoverUtility iframes = getWidget().getIframeCoverUtility();
            iframes.setIframeCoversEnabled(
                    uidl.getBooleanAttribute(IframeCoverUtility.SHIM_ATTRIBUTE),
                    getWidget().getElement(), getState().getDragMode());
        }
    }

    /**
     * Handles updates the the hoover zones of the tab which specifies at which
     * position a component is dropped over a tab
     * 
     * @param uidl
     *            The UIDL
     */
    private void handleCellDropRatioUpdate(UIDL uidl) {
        if (uidl.hasAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO)) {
            getState()
                    .setTabTopBottomDropRatio(
                            uidl.getFloatAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO));
        }
    }

}
