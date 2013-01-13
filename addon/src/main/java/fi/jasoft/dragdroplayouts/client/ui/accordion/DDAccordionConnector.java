package fi.jasoft.dragdroplayouts.client.ui.accordion;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.accordion.AccordionConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDAccordion;
import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;

@Connect(DDAccordion.class)
public class DDAccordionConnector extends AccordionConnector implements
        Paintable, VHasDragMode, VHasDragFilter {

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
        super.updateFromUIDL(uidl, client);

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

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        getWidget().setDragFilter(new VDragFilter(getState()));
    }

    @Override
    public VDragFilter getDragFilter() {
        return getWidget().getDragFilter();
    }

    @Override
    public void setDragFilter(VDragFilter filter) {
        getWidget().setDragFilter(filter);
    }

}
