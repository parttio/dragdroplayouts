package fi.jasoft.dragdroplayouts.client.ui.horizontallayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;

public class VDDHorizontalLayoutDropHandler extends VAbstractDropHandler {

    private final VDDHorizontalLayout layout;

    private final ComponentConnector connector;

    public VDDHorizontalLayoutDropHandler(VDDHorizontalLayout layout,
            ComponentConnector connector) {
        this.layout = layout;
        this.connector = connector;
    }

    public ApplicationConnection getApplicationConnection() {
        return connector.getConnection();
    }

    @Override
    protected void dragAccepted(VDragEvent drag) {
        dragOver(drag);
    }

    @Override
    public ComponentConnector getConnector() {
        return connector;
    }

    @Override
    public boolean drop(VDragEvent drag) {

        // Un-emphasis any selections
        layout.emphasis(null, null);

        // Update the details
        layout.updateDropDetails(null, drag);

        return layout.postDropHook(drag) && super.drop(drag);
    };

    @Override
    public void dragOver(VDragEvent drag) {

        // Remove any emphasis
        layout.emphasis(null, null);

        // Update the dropdetails so we can validate the drop
        // FIXME
        // ChildComponentContainer c = getContainerFromDragEvent(drag);
        // if (c != null) {
        // updateDropDetails(c, drag);
        // } else {
        // updateDropDetails(VDDHorizontalLayout.this, drag);
        // }

        layout.postOverHook(drag);

        // Validate the drop
        validate(new VAcceptCallback() {
            public void accepted(VDragEvent event) {
                // FIXME
                // ChildComponentContainer c = getContainerFromDragEvent(event);
                // if (c != null) {
                // emphasis(c, event);
                // } else {
                // emphasis(VDDHorizontalLayout.this, event);
                // }
            }
        }, drag);
    };

    @Override
    public void dragLeave(VDragEvent drag) {
        layout.emphasis(null, drag);

        layout.postLeaveHook(drag);
    }

}
