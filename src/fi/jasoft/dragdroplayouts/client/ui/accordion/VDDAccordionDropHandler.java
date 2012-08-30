package fi.jasoft.dragdroplayouts.client.ui.accordion;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.ComponentConnector;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;

import fi.jasoft.dragdroplayouts.client.ui.Constants;

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

        Widget w = (Widget) drag.getTransferable().getData(
                Constants.TRANSFERABLE_DETAIL_COMPONENT);
        if (layout.equals(w)) {
            return;
        }

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
