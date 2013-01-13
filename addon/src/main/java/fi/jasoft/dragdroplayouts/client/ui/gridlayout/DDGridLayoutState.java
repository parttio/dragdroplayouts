package fi.jasoft.dragdroplayouts.client.ui.gridlayout;

import java.util.List;

import com.vaadin.shared.Connector;
import com.vaadin.shared.annotations.DelegateToWidget;
import com.vaadin.shared.ui.gridlayout.GridLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;

public class DDGridLayoutState extends GridLayoutState implements DDLayoutState {

    public static final float DEFAULT_HORIZONTAL_RATIO = 0.2f;
    public static final float DEFAULT_VERTICAL_RATIO = 0.2f;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    private float cellLeftRightDropRatio = DEFAULT_HORIZONTAL_RATIO;

    private float cellTopBottomDropRatio = DEFAULT_VERTICAL_RATIO;

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

    public float getCellLeftRightDropRatio() {
        return cellLeftRightDropRatio;
    }

    @DelegateToWidget
    public void setCellLeftRightDropRatio(float cellLeftRightDropRatio) {
        this.cellLeftRightDropRatio = cellLeftRightDropRatio;
    }

    public float getCellTopBottomDropRatio() {
        return cellTopBottomDropRatio;
    }

    @DelegateToWidget
    public void setCellTopBottomDropRatio(float cellTopBottomDropRatio) {
        this.cellTopBottomDropRatio = cellTopBottomDropRatio;
    }

    @Override
    public List<Connector> getDraggableComponents() {
        return draggable;
    }
}
