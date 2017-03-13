package fi.jasoft.dragdroplayouts.interfaces;

/**
 * Container that has {@link DragGrabFilter}.
 */
public interface DragGrabFilterSupport {
    /**
     * @return Drag grab filter
     */
    DragGrabFilter getDragGrabFilter();

    /**
     * @param dragGrabFilter drag grab filter
     */
    void setDragGrabFilter(DragGrabFilter dragGrabFilter);
}