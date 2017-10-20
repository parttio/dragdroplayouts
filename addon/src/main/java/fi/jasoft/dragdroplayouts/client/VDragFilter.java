/*
 * Copyright 2015 John Ahlroos
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
package fi.jasoft.dragdroplayouts.client;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.Util;
import com.vaadin.client.VCaption;
import com.vaadin.client.ui.VAccordion.StackItem;
import com.vaadin.client.ui.composite.CompositeConnector;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;

public class VDragFilter {

    private final DDLayoutState state;

    public VDragFilter(DDLayoutState state) {
        this.state = state;
    }

    public boolean isDraggable(Widget widget) {
        ComponentConnector connector = findConnectorFor(widget);
        if (state.draggable != null) {
            if (state.draggable.contains(connector)) {
                return true;
            }

            // parent is the layout
            // child is the child of the layout, i.e. possibly a composite
            while (connector != null
                    && connector.getParent() instanceof CompositeConnector) {
                connector = (ComponentConnector) connector.getParent();
                if (state.draggable.contains(connector)) {
                    return true;
                }
            }
        }
        return false;
    }

    private ComponentConnector findConnectorFor(Widget widget) {
        if (!isCaptionForAccordion(widget)) {
            return Util.findConnectorFor(widget);
        } else {
            return findConnectorForAccordionCaption(widget);
        }
    }

    private ComponentConnector findConnectorForAccordionCaption(Widget widget) {
        StackItem parent = (StackItem) widget.getParent();
        return Util.findConnectorFor(parent.getChildWidget());
    }

    private boolean isCaptionForAccordion(Widget widget) {
        if (widget == null) {
            return false;
        }
        if (!(widget instanceof VCaption)) {
            return false;
        }
        Widget parent = widget.getParent();
        return parent instanceof StackItem;
    }
}
