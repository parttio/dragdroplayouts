package fi.jasoft.dragdroplayouts.client;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.Util;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;

public class VDragFilter {

    private final DDLayoutState state;

    public VDragFilter(DDLayoutState state) {
        this.state = state;
    }

    public boolean isDraggable(Widget widget) {
        ComponentConnector component = Util.findConnectorFor(widget);
        if (state.getDraggableComponents() != null) {
            return state.getDraggableComponents().contains(component);
        }
        return false;
    }
}
