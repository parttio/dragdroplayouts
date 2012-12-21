package fi.jasoft.dragdroplayouts.client.ui.accordion;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;

public class VDDAccordionDropHandler extends VAbstractDropHandler {

    private final VDDAccordion layout;

    private final ComponentConnector connector;

    public VDDAccordionDropHandler(VDDAccordion layout,
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
        layout.deEmphasis();

        layout.updateDropDetails(drag);

        return layout.postDropHook(drag) && super.drop(drag);
    };

    @Override
    public void dragOver(VDragEvent drag) {

        layout.deEmphasis();

        layout.updateDropDetails(drag);

        layout.postOverHook(drag);

        // Validate the drop
        validate(new VAcceptCallback() {
            public void accepted(VDragEvent event) {
                layout.emphasis(event.getElementOver(), event);
            }
        }, drag);
    };

    @Override
    public void dragLeave(VDragEvent drag) {
        layout.deEmphasis();
        layout.updateDropDetails(drag);
        layout.postLeaveHook(drag);
    }
}
