package fi.jasoft.dragdroplayouts.client.ui.accordion;

import com.vaadin.terminal.gwt.client.ComponentState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DDAccordionState extends ComponentState {

    public static final float DEFAULT_VERTICAL_RATIO = 0.2f;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    // A filter for dragging components.
    private DragFilter dragFilter = DragFilter.ALL;

    private float tabTopBottomDropRatio = DEFAULT_VERTICAL_RATIO;

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

    public float getTabTopBottomDropRatio() {
        return tabTopBottomDropRatio;
    }

    public void setTabTopBottomDropRatio(float tabTopBottomDropRatio) {
        this.tabTopBottomDropRatio = tabTopBottomDropRatio;
    }
}
