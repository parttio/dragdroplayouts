package fi.jasoft.dragdroplayouts.client.ui.csslayout;

import java.util.List;

import com.vaadin.shared.Connector;
import com.vaadin.shared.annotations.DelegateToWidget;
import com.vaadin.shared.ui.csslayout.CssLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;

public class DDCssLayoutState extends CssLayoutState implements DDLayoutState {

    public static final float DEFAULT_HORIZONTAL_DROP_RATIO = 0.2f;

    public static final float DEFAULT_VERTICAL_DROP_RATIO = 0.2f;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    private float horizontalDropRatio = DEFAULT_HORIZONTAL_DROP_RATIO;

    private float verticalDropRatio = DEFAULT_VERTICAL_DROP_RATIO;

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

    public float getHorizontalDropRatio() {
        return horizontalDropRatio;
    }

    @DelegateToWidget
    public void setHorizontalDropRatio(float horizontalDropRatio) {
        this.horizontalDropRatio = horizontalDropRatio;
    }

    public float getVerticalDropRatio() {
        return verticalDropRatio;
    }

    @DelegateToWidget
    public void setVerticalDropRatio(float verticalDropRatio) {
        this.verticalDropRatio = verticalDropRatio;
    }

    @Override
    public List<Connector> getDraggableComponents() {
        return draggable;
    }
}
