package fi.jasoft.dragdroplayouts.client.ui.absolutelayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VDragEvent;

public class VDDAbsoluteLayoutDropHandler extends VAbstractDropHandler {

    private final VDDAbsoluteLayout layout;

    private final ComponentConnector connector;

    public VDDAbsoluteLayoutDropHandler(VDDAbsoluteLayout layout,
            ComponentConnector connector) {
        this.layout = layout;
        this.connector = connector;
    }

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

    @Override
    public boolean drop(VDragEvent drag) {
        if (super.drop(drag)) {

            layout.updateDragDetails(drag);

            return layout.postDropHook(drag);
        }
        return false;
    };

    @Override
    public void dragEnter(VDragEvent drag) {
        super.dragEnter(drag);

        layout.postEnterHook(drag);
    };

    @Override
    public void dragLeave(VDragEvent drag) {
        super.dragLeave(drag);

        layout.postLeaveHook(drag);
    };

    @Override
    public void dragOver(VDragEvent drag) {
        drag.getDragImage().getStyle().setProperty("display", "");

        layout.updateDragDetails(drag);

        layout.postOverHook(drag);
    }
}
