package fi.jasoft.dragdroplayouts.client.ui.interfaces;

import java.io.Serializable;
import java.util.List;

import com.vaadin.shared.Connector;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;

public class DDLayoutState implements Serializable {

    // The current drag mode, default is dragging is not supported
    public LayoutDragMode dragMode = LayoutDragMode.NONE;

    // Are the iframes shimmed
    public boolean iframeShims = true;

    // Which connectors are draggable
    public List<Connector> draggable;

}
