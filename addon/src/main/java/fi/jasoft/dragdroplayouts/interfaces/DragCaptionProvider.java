package fi.jasoft.dragdroplayouts.interfaces;

import com.vaadin.ui.Component;
import fi.jasoft.dragdroplayouts.DragCaption;

/**
 * Interface that provides custom {@link DragCaption} for a child component. <br>
 * Drag caption is shown as simple DIV with icon and SPAN with caption on client side for a dragged component. <br>
 * It is the analogue of {@link DragImageProvider} but it does not require additional components for a dragged caption.
 */
public interface DragCaptionProvider {
    DragCaption getDragCaption(Component component);
}