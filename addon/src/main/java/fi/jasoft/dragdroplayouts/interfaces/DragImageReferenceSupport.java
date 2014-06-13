package fi.jasoft.dragdroplayouts.interfaces;


public interface DragImageReferenceSupport {
        
    /**
     * Set a component as a drag image for a component in the layout. The drag
     * image will be shown instead of the component when the user drag a
     * component in the layout.
     * 
     * @param component
     *            The component in the layout
     * 
     * @param reference
     *            The reference component to show
     */
    void setDragImageProvider(DragImageProvider provider);

    /**
     * Returns the drag image provider
     * 
     */
    DragImageProvider getDragImageProvider();
}
