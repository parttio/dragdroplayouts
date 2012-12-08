package fi.jasoft.dragdroplayouts.client.ui.horizontalsplitpanel;

import com.vaadin.shared.ui.splitpanel.HorizontalSplitPanelState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DDHorizontalSplitPanelState extends HorizontalSplitPanelState {

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    // A filter for dragging components.
    private DragFilter dragFilter = DragFilter.ALL;

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

    public DragFilter getDragFilter() {
        return dragFilter;
    }

    public void setDragFilter(DragFilter dragFilter) {
        this.dragFilter = dragFilter;
    }
}
