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
package fi.jasoft.dragdroplayouts.client.ui.verticallayout;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.Util;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.client.ui.orderedlayout.Slot;

public class VDDVerticalLayoutDropHandler extends VAbstractDropHandler {

    private final VDDVerticalLayout layout;

    private final ComponentConnector connector;

    public VDDVerticalLayoutDropHandler(VDDVerticalLayout layout,
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
	    layout.updateDropDetails(slot, drag);
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
    };

}
