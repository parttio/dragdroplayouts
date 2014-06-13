package fi.jasoft.dragdroplayouts.client.ui;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VDragEvent;

public abstract class VDDAbstractDropHandler<W extends Widget> extends
	VAbstractDropHandler {

    private final ComponentConnector connector;

    public VDDAbstractDropHandler(ComponentConnector connector) {
	this.connector = connector;
    }

    @Override
    public ApplicationConnection getApplicationConnection() {
	return connector.getConnection();
    }

    @Override
    protected void dragAccepted(VDragEvent drag) {
	// NOP
    }

    @Override
    public ComponentConnector getConnector() {
	return connector;
    }

    protected W getLayout() {
	return (W) connector.getWidget();
    }
}
