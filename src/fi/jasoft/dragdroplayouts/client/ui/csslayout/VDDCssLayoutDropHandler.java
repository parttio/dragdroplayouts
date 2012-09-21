package fi.jasoft.dragdroplayouts.client.ui.csslayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;

public class VDDCssLayoutDropHandler extends VAbstractDropHandler {

    private final VDDCssLayout layout;

    private final ComponentConnector connector;

    public VDDCssLayoutDropHandler(VDDCssLayout layout,
            ComponentConnector connector) {
        this.layout = layout;
        this.connector = connector;
    }

    public ApplicationConnection getApplicationConnection() {
        return connector.getConnection();
    }

    @Override
    protected void dragAccepted(VDragEvent drag) {

    }

    @Override
    public ComponentConnector getConnector() {
        return connector;
    }

    @Override
    public boolean drop(VDragEvent drag) {
        layout.updateDragDetails(drag);
        layout.detachDragImageFromLayout(drag);
        return layout.postDropHook(drag) && super.drop(drag);
    };

    @Override
    public void dragEnter(VDragEvent drag) {
        super.dragEnter(drag);
        layout.attachDragImageToLayout(drag);
        layout.updateDragDetails(drag);
        layout.postEnterHook(drag);
    };

    @Override
    public void dragLeave(VDragEvent drag) {
        super.dragLeave(drag);
        layout.detachDragImageFromLayout(drag);
        layout.postLeaveHook(drag);
    };

    @Override
    public void dragOver(VDragEvent drag) {
        layout.updateDragDetails(drag);
        layout.postOverHook(drag);

        // Validate the drop
        validate(new VAcceptCallback() {
            public void accepted(VDragEvent event) {
                layout.updateDrag(event);
            }
        }, drag);
    }

}
