package fi.jasoft.dragdroplayouts.client.ui.accordion;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.accordion.AccordionConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDAccordion;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;

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
    @Override
    protected void init() {
        super.init();
        VDragDropUtil.listenToStateChangeEvents(this, getWidget()
                .getMouseHandler(), getWidget().getIframeCoverUtility(),
                getWidget());
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
        return getState().getDragMode();
    }

    /**
     * {@inheritDoc}
     * 
     * TODO Remove this when drag & drop is done properly in core
     */
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {

        // Drop handlers
        UIDL ac = uidl.getChildByTagName("-ac");
        if (ac == null) {
            if (getWidget().getDropHandler() != null) {
                // remove dropHandler if not present anymore
                getWidget().setDropHandler(null);
            }
        } else {
            if (getWidget().getDropHandler() == null) {
                getWidget().setDropHandler(
                        new VDDAccordionDropHandler(getWidget(), this));
            }
            getWidget().getDropHandler().updateAcceptRules(ac);
        }
    }

}
