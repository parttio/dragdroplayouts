package fi.jasoft.dragdroplayouts.client.ui.interfaces;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public interface DDLayoutState {

    DragFilter getDragFilter();

    void setDragFilter(DragFilter dragFilter);

    boolean isIframeShims();

    void setIframeShims(boolean iframeShims);

    LayoutDragMode getDragMode();

    public void setDragMode(LayoutDragMode dragMode);
}
