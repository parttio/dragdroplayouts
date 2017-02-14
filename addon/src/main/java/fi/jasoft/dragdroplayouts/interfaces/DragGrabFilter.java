package fi.jasoft.dragdroplayouts.interfaces;

import com.vaadin.ui.Component;

import java.io.Serializable;

public interface DragGrabFilter extends Serializable {
    boolean canBeGrabbed(Component component);
}