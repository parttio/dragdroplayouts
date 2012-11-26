package fi.jasoft.dragdroplayouts.client.ui.absolutelayout;

import com.vaadin.shared.ui.absolutelayout.AbsoluteLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;

public class DDAbsoluteLayoutState extends AbsoluteLayoutState implements
        DDLayoutState {

    // The current drag mode, default is dragging is not supported
    public LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    public boolean iframeShims = true;

    public boolean isIframeShims() {
        return iframeShims;
    }

    public LayoutDragMode getDragMode() {
        return dragMode;
    }

}
