package fi.jasoft.dragdroplayouts.client.ui.horizontallayout;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.Util;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.client.ui.orderedlayout.Slot;

public class VDDHorizontalLayoutDropHandler extends VAbstractDropHandler {

    private final VDDHorizontalLayout layout;

    private final ApplicationConnection client;

    public VDDHorizontalLayoutDropHandler(VDDHorizontalLayout layout,
            ApplicationConnection client) {
        this.layout = layout;
        this.client = client;
    }

    public ApplicationConnection getApplicationConnection() {
        return client;
    }

    @Override
    protected void dragAccepted(VDragEvent drag) {
        dragOver(drag);
    }

    @Override
    public ComponentConnector getConnector() {
        return ConnectorMap.get(client).getConnector(layout);
    }

    @Override
    public boolean drop(VDragEvent drag) {

        // Un-emphasis any selections
        layout.emphasis(null, null);

        // Update the details
        Widget slot = getSlot(drag.getElementOver());
        layout.updateDropDetails(slot, drag);

        return layout.postDropHook(drag) && super.drop(drag);
    };

    private Slot getSlot(Element e) {
        return Util.findWidget(e, Slot.class);
    }

    @Override
    public void dragOver(VDragEvent drag) {

        // Remove any emphasis
        layout.emphasis(null, null);

        // Update the dropdetails so we can validate the drop
        Slot slot = getSlot(drag.getElementOver());
        if (slot != null) {
            layout.updateDropDetails(slot.getWidget(), drag);
        } else {
            layout.updateDropDetails(layout, drag);
        }

        layout.postOverHook(drag);

        // Validate the drop
        validate(new VAcceptCallback() {
            public void accepted(VDragEvent event) {
                Slot slot = getSlot(event.getElementOver());
                if (slot != null) {
                    layout.emphasis(slot.getWidget(), event);
                } else {
                    layout.emphasis(layout, event);
                }
            }
        }, drag);
    };

    @Override
    public void dragLeave(VDragEvent drag) {
        layout.emphasis(null, drag);

        layout.postLeaveHook(drag);
    }

}
