package fi.jasoft.dragdroplayouts.interfaces;

import com.vaadin.ui.Component;
import fi.jasoft.dragdroplayouts.DragCaption;

public interface DragCaptionProvider {
    DragCaption getDragCaption(Component component);
}
