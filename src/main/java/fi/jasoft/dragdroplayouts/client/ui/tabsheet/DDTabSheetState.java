package fi.jasoft.dragdroplayouts.client.ui.tabsheet;

import java.util.List;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.tabsheet.TabsheetState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;

public class DDTabSheetState extends TabsheetState implements DDLayoutState {

    public static final float DEFAULT_HORIZONTAL_DROP_RATIO = 0.2f;

    // The current drag mode, default is dragging is not supported
    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    private boolean iframeShims = true;

    private float tabLeftRightDropRatio = DEFAULT_HORIZONTAL_DROP_RATIO;

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

    public float getTabLeftRightDropRatio() {
        return tabLeftRightDropRatio;
    }

    public void setTabLeftRightDropRatio(float tabLeftRightDropRatio) {
        this.tabLeftRightDropRatio = tabLeftRightDropRatio;
    }

    @Override
    public List<Connector> getDraggableComponents() {
        return draggable;
    }

}
