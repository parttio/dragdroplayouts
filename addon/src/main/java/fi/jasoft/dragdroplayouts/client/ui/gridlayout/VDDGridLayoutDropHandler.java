/*
 * Copyright 2013 John Ahlroos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.jasoft.dragdroplayouts.client.ui.gridlayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;

import fi.jasoft.dragdroplayouts.client.ui.gridlayout.VDDGridLayout.CellDetails;

public class VDDGridLayoutDropHandler extends VAbstractDropHandler {

    private final VDDGridLayout layout;

    private final ApplicationConnection client;

    public VDDGridLayoutDropHandler(VDDGridLayout layout,
            ApplicationConnection client) {
        this.layout = layout;
        this.client = client;
    }

    public ApplicationConnection getApplicationConnection() {
        return client;
    };

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #dragAccepted (com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    protected void dragAccepted(VDragEvent drag) {
        // Nop
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #dragEnter(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    public void dragEnter(VDragEvent drag) {
        // Add the marker that shows the drop location while
        // dragging
        layout.postEnterHook(drag);
    };

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #drop(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    public boolean drop(VDragEvent drag) {

        // Update the detail of the drop
        layout.updateDropDetails(drag);

        // Remove emphasis
        layout.deEmphasis();

        return layout.postDropHook(drag);
    };

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #dragOver(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    public void dragOver(VDragEvent drag) {

        // Remove emphasis from previous selection
        layout.deEmphasis();

        // Update the drop details so we can then validate them
        layout.updateDropDetails(drag);

        layout.postOverHook(drag);

        // Emphasis drop location
        validate(new VAcceptCallback() {
            public void accepted(VDragEvent event) {
                CellDetails cd = layout.getCellDetails(event);
                if (cd != null) {
                    layout.emphasis(cd, event);
                }
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
        layout.postLeaveHook(drag);
        super.dragLeave(drag);
    }

    @Override
    public ComponentConnector getConnector() {
        return ConnectorMap.get(client).getConnector(layout);
    }
}
