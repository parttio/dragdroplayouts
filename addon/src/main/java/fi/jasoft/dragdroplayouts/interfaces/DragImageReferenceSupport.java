package fi.jasoft.dragdroplayouts.interfaces;

public interface DragImageReferenceSupport {

    /**
     * Set a component as a drag image for a component in the layout. The drag
     * image will be shown instead of the component when the user drag a
     * component in the layout.
     * 
     * @param provider
     *            The image provider
     * 
     */
    void setDragImageProvider(DragImageProvider provider);

    /**
     * Returns the drag image provider
     * 
     * @return the image provider
     */
    DragImageProvider getDragImageProvider();
}
