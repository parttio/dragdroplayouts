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
