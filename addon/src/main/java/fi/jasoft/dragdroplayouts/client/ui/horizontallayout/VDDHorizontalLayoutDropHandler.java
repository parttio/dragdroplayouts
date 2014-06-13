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
package fi.jasoft.dragdroplayouts.client.ui.horizontallayout;

import com.google.gwt.dom.client.Element;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.Util;
import com.vaadin.client.VConsole;
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

	VConsole.error("init:" + layout.getStyleName());
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
        layout.emphasis(null, null);
	updateDropDetails(drag);
        return layout.postDropHook(drag) && super.drop(drag);
    };

    private Slot getSlot(Element e) {
	Slot slot = Util.findWidget(e, Slot.class);
	assert layout.getElement().isOrHasChild(e) : "Slot is not inside layout";
	return slot;
    }
    
    private void updateDropDetails(VDragEvent drag) {
	Slot slot = getSlot(drag.getElementOver());
	if (slot != null) {
	    layout.updateDropDetails(slot.getWidget(), drag);
	} else {
	    layout.updateDropDetails(layout, drag);
	}
    }

    @Override
    public void dragOver(VDragEvent drag) {
	layout.deEmphasis();
	updateDropDetails(drag);
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
	layout.deEmphasis();
        layout.postLeaveHook(drag);
    }

}
