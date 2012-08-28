package fi.jasoft.dragdroplayouts.client.ui.csslayout;

import com.vaadin.shared.ui.csslayout.CssLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DDCssLayoutState extends CssLayoutState {

    public static final float DEFAULT_HORIZONTAL_DROP_RATIO = 0.2f;

    public static final float DEFAULT_VERTICAL_DROP_RATIO = 0.2f;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    // A filter for dragging components.
    private DragFilter dragFilter = DragFilter.ALL;

    private float horizontalDropRatio = DEFAULT_HORIZONTAL_DROP_RATIO;

    private float verticalDropRatio = DEFAULT_VERTICAL_DROP_RATIO;

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

    public float getHorizontalDropRatio() {
        return horizontalDropRatio;
    }

    public void setHorizontalDropRatio(float horizontalDropRatio) {
        this.horizontalDropRatio = horizontalDropRatio;
    }

    public float getVerticalDropRatio() {
        return verticalDropRatio;
    }

    public void setVerticalDropRatio(float verticalDropRatio) {
        this.verticalDropRatio = verticalDropRatio;
    }
}
