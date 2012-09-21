package fi.jasoft.dragdroplayouts.client.ui.horizontallayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.orderedlayout.HorizontalLayoutConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;

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

    @Override
    public void init() {
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
                        new VDDHorizontalLayoutDropHandler(getWidget(), this));
            }
            getWidget().getDropHandler().updateAcceptRules(ac);
        }
    }
}
