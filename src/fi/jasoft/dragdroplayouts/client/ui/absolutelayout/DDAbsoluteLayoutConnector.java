package fi.jasoft.dragdroplayouts.client.ui.absolutelayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.absolutelayout.AbsoluteLayoutConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;

@Connect(DDAbsoluteLayout.class)
public class DDAbsoluteLayoutConnector extends AbsoluteLayoutConnector
        implements Paintable, VHasDragMode {

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
    @Override
    protected void init() {
        super.init();
        VDragDropUtil.listenToStateChangeEvents(this, getWidget()
                .getMouseHandler(), getWidget().getIframeCoverUtility(),
                getWidget());
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
                        new VDDAbsoluteLayoutDropHandler(getWidget(), this));
            }
            getWidget().getDropHandler().updateAcceptRules(ac);
        }
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
        return getState().getDragMode();
    }
}
