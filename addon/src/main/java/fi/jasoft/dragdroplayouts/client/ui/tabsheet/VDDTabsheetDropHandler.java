package fi.jasoft.dragdroplayouts.client.ui.tabsheet;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;

public class VDDTabsheetDropHandler extends VAbstractDropHandler {

    private final VDDTabSheet layout;

    private final ApplicationConnection client;

    public VDDTabsheetDropHandler(VDDTabSheet layout,
            ApplicationConnection client) {
        this.layout = layout;
        this.client = client;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VDropHandler#
     * getApplicationConnection()
     */
    public ApplicationConnection getApplicationConnection() {
        return client;
    }

    @Override
    public ComponentConnector getConnector() {
        return ConnectorMap.get(client).getConnector(layout);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #dragAccepted (com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    protected void dragAccepted(VDragEvent drag) {
        dragOver(drag);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #drop(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    public boolean drop(VDragEvent drag) {

        layout.deEmphasis();

        // Update the details
        layout.updateDropDetails(drag);
        return layout.postDropHook(drag) && super.drop(drag);
    };

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #dragOver(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #dragLeave(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    public void dragLeave(VDragEvent drag) {
        layout.deEmphasis();
        layout.updateDropDetails(drag);
        layout.postLeaveHook(drag);
    };
}
