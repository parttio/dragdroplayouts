package fi.jasoft.dragdroplayouts.client.ui.interfaces;

import java.util.List;

import com.vaadin.shared.Connector;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

public interface DDLayoutState {

    boolean isIframeShims();

    LayoutDragMode getDragMode();

    List<Connector> getDraggableComponents();
}
