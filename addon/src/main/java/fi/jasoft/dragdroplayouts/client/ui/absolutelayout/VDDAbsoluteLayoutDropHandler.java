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
package fi.jasoft.dragdroplayouts.client.ui.absolutelayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VDragEvent;

public class VDDAbsoluteLayoutDropHandler extends VAbstractDropHandler {

    private final VDDAbsoluteLayout layout;

    private final ApplicationConnection client;

    /**
     * @param vddAbsoluteLayout
     */
    public VDDAbsoluteLayoutDropHandler(VDDAbsoluteLayout layout,
            ApplicationConnection client) {
        this.layout = layout;
        this.client = client;
    }

    public ApplicationConnection getApplicationConnection() {
        return client;
    }

    @Override
    protected void dragAccepted(VDragEvent drag) {
        // NOP
    }

    @Override
    public ComponentConnector getConnector() {
        return ConnectorMap.get(client).getConnector(layout);
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