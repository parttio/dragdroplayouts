package fi.jasoft.dragdroplayouts.client.ui.formlayout;

import java.util.List;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.orderedlayout.AbstractOrderedLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;

public class DDFormLayoutState extends AbstractOrderedLayoutState implements
        DDLayoutState {

    public static final float DEFAULT_VERTICAL_DROP_RATIO = 0.3333f;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    private float cellTopBottomDropRatio = DEFAULT_VERTICAL_DROP_RATIO;

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

    public float getCellTopBottomDropRatio() {
        return cellTopBottomDropRatio;
    }

    public void setCellTopBottomDropRatio(float cellTopBottomDropRatio) {
        this.cellTopBottomDropRatio = cellTopBottomDropRatio;
    }

    @Override
    public List<Connector> getDraggableComponents() {
        return draggable;
    }
}
