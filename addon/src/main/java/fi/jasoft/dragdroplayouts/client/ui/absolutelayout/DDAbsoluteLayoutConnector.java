package fi.jasoft.dragdroplayouts.client.ui.absolutelayout;

import com.google.gwt.core.shared.GWT;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.absolutelayout.AbsoluteLayoutConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;

@Connect(DDAbsoluteLayout.class)
public class DDAbsoluteLayoutConnector extends AbsoluteLayoutConnector 
	implements Paintable, VHasDragFilter {

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
    protected VDDAbsoluteLayout createWidget() {
        return GWT.create(VDDAbsoluteLayout.class);
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
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (isRealUpdate(uidl) && !uidl.hasAttribute("hidden")) {
            UIDL acceptCrit = uidl.getChildByTagName("-ac");
            if (acceptCrit == null) {
                getWidget().setDropHandler(null);
            } else {
                if (getWidget().getDropHandler() == null) {
                    getWidget().setDropHandler(
                            new VDDAbsoluteLayoutDropHandler(getWidget(),
                                    client));
                }
                getWidget().getDropHandler().updateAcceptRules(acceptCrit);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
        return getState().dd.dragMode;
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
