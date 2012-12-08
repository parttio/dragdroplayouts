package fi.jasoft.dragdroplayouts.client.ui.accordion;

import java.util.List;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.Connector;
import com.vaadin.shared.annotations.DelegateToWidget;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DDAccordionState extends AbstractComponentState implements
        DDLayoutState {

    public static final float DEFAULT_VERTICAL_RATIO = 0.2f;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    public List<Connector> draggable;

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

    public float getTabTopBottomDropRatio() {
        return tabTopBottomDropRatio;
    }

    @DelegateToWidget
    public void setTabTopBottomDropRatio(float tabTopBottomDropRatio) {
        this.tabTopBottomDropRatio = tabTopBottomDropRatio;
    }

    @Override
    public List<Connector> getDraggableComponents() {
        return draggable;
    }
}
