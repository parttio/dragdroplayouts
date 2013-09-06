package fi.jasoft.dragdroplayouts.client.ui.horizontalsplitpanel;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.splitpanel.HorizontalSplitPanelConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDHorizontalSplitPanel;
import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;

@Connect(DDHorizontalSplitPanel.class)
public class DDHorizontalSplitPanelConnector extends
        HorizontalSplitPanelConnector implements Paintable,VHasDragFilter {

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
    @Override
    public VDDHorizontalSplitPanel getWidget() {
        return (VDDHorizontalSplitPanel) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDHorizontalSplitPanelState getState() {
        return (DDHorizontalSplitPanelState) super.getState();
    }

    /**
     * {@inheritDoc}
     */
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (isRealUpdate(uidl) && !uidl.hasAttribute("hidden")) {
            UIDL acceptCrit = uidl.getChildByTagName("-ac");
            if (acceptCrit == null) {
                getWidget().setDropHandler(null);
            } else {
                if (getWidget().getDropHandler() == null) {
                    getWidget().setDropHandler(
                            new VDDHorizontalSplitPanelDropHandler(getWidget(),
                                    client));
                }
                getWidget().getDropHandler().updateAcceptRules(acceptCrit);
            }
        }
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        getWidget().setDragFilter(new VDragFilter(getState().dd));
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
