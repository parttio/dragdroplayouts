package fi.jasoft.dragdroplayouts.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.Util;
import com.vaadin.shared.Connector;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VDragImageProvider;

public class VDDLayoutStateDragImageProvider implements VDragImageProvider {

    private final DDLayoutState state;

    public VDDLayoutStateDragImageProvider(DDLayoutState state) {
	this.state = state;
    }

    @Override
    public Element getDragImageElement(Widget w) {
	ComponentConnector component = Util.findConnectorFor(w);
	Connector dragImage = state.referenceImageComponents.get(component);

	if (dragImage != null) {
	    return ConnectorMap.get(component.getConnection()).getElement(
		    dragImage.getConnectorId());
	}

	return null;
    }

}
