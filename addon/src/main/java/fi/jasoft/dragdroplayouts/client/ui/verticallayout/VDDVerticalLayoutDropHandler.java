/*
 * Copyright 2014 John Ahlroos
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package fi.jasoft.dragdroplayouts.client.ui.verticallayout;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.Util;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.client.ui.orderedlayout.Slot;

import fi.jasoft.dragdroplayouts.client.ui.VDDAbstractDropHandler;

public class VDDVerticalLayoutDropHandler
        extends VDDAbstractDropHandler<VDDVerticalLayout> {

    public VDDVerticalLayoutDropHandler(ComponentConnector connector) {
        super(connector);
    }

    @Override
    protected void dragAccepted(VDragEvent drag) {
        dragOver(drag);
    }

    @Override
    public boolean drop(VDragEvent drag) {

        // Un-emphasis any selections
        getLayout().emphasis(null, null);

        // Update the details
        Widget slot = getSlot(drag.getElementOver());
        getLayout().updateDragDetails(slot, drag);

        return getLayout().postDropHook(drag) && super.drop(drag);
    };

    private Slot getSlot(Element e) {
        return Util.findWidget(e, Slot.class);
    }

    @Override
    public void dragOver(VDragEvent drag) {

        // Remove any emphasis
        getLayout().emphasis(null, null);

        // Update the dropdetails so we can validate the drop
        Slot slot = getSlot(drag.getElementOver());

        if (slot != null) {
            getLayout().updateDragDetails(slot, drag);
        } else {
            getLayout().updateDragDetails(getLayout(), drag);
        }

        getLayout().postOverHook(drag);

        // Validate the drop
        validate(new VAcceptCallback() {
            public void accepted(VDragEvent event) {
                Slot slot = getSlot(event.getElementOver());
                if (slot != null) {
                    getLayout().emphasis(slot, event);
                } else {
                    getLayout().emphasis(getLayout(), event);
                }
            }
        }, drag);
    };

    @Override
    public void dragLeave(VDragEvent drag) {
        getLayout().emphasis(null, drag);

        getLayout().postLeaveHook(drag);
    };

}
