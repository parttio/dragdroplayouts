package fi.jasoft.dragdroplayouts.client.ui.verticallayout;

import com.vaadin.shared.annotations.DelegateToWidget;
import com.vaadin.shared.ui.orderedlayout.VerticalLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DDVerticalLayoutState extends VerticalLayoutState implements
        DDLayoutState {

    public static final float DEFAULT_VERTICAL_DROP_RATIO = 0.2f;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    // A filter for dragging components.
    private DragFilter dragFilter = DragFilter.ALL;

    private float cellTopBottomDropRatio = DEFAULT_VERTICAL_DROP_RATIO;

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

    public float getCellTopBottomDropRatio() {
        return cellTopBottomDropRatio;
    }

    @DelegateToWidget
    public void setCellTopBottomDropRatio(float cellTopBottomDropRatio) {
        this.cellTopBottomDropRatio = cellTopBottomDropRatio;
    }
}
