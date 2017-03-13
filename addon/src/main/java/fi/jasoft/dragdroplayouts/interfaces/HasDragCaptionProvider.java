package fi.jasoft.dragdroplayouts.interfaces;

/**
 * Container that has {@link DragCaptionProvider}.
 */
public interface HasDragCaptionProvider {
    void setDragCaptionProvider(DragCaptionProvider provider);
    DragCaptionProvider getDragCaptionProvider();
}