package fi.jasoft.dragdroplayouts.interfaces;

import com.vaadin.ui.Component;

import java.io.Serializable;

/**
 * Grab filter. If filter is set and returned false for some child/nested component then you will not be able to
 * drag component grabbing this child. E.g. if we have composite Panel inside of some layout that includes Button and
 * Label you can deny dragging of Panel using Button. <br>
 *
 * This feature is similar to {@link DragFilter} but more powerful, since it can filter out deep nested components.
 */
public interface DragGrabFilter extends Serializable {
    /**
     * @param component nested component (can be deep child of Layout)
     * @return true if this nested/deep child can be grabbed to drag child component
     */
    boolean canBeGrabbed(Component component);
}