package fi.jasoft.dragdroplayouts.client.ui.horizontalsplitpanel;

import java.util.List;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.splitpanel.HorizontalSplitPanelState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;

public class DDHorizontalSplitPanelState extends HorizontalSplitPanelState
        implements DDLayoutState {

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    public List<Connector> draggable;

    public LayoutDragMode getDragMode() {
        return dragMode;
    }

    public void setDragMode(LayoutDragMode dragMode) {
        this.dragMode = dragMode;
    }

    public boolean isIframeShims() {
        return iframeShims;
    }

    public void setIframeShims(boolean iframeShims) {
        this.iframeShims = iframeShims;
    }

    @Override
    public List<Connector> getDraggableComponents() {
        return draggable;
    }
}
