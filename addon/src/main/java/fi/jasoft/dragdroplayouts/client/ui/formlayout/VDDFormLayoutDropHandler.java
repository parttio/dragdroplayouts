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
package fi.jasoft.dragdroplayouts.client.ui.formlayout;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.ui.dd.VAbstractDropHandler;
import com.vaadin.client.ui.dd.VAcceptCallback;
import com.vaadin.client.ui.dd.VDragEvent;

public class VDDFormLayoutDropHandler extends VAbstractDropHandler {

    private final VDDFormLayout layout;
    private final ApplicationConnection client;

    public VDDFormLayoutDropHandler(VDDFormLayout layout,
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

        // Un-emphasis any selections
        layout.emphasis(null, null);

        // Update the details
        layout.updateDropDetails(getTableRowWidgetFromDragEvent(drag), drag);
        return layout.postDropHook(drag) && super.drop(drag);
    };

    private Widget getTableRowWidgetFromDragEvent(VDragEvent event) {

        /**
         * Find the widget of the row
         */
        Element e = event.getElementOver();

        if (layout.table.getRowCount() == 0) {
            /*
             * Empty layout
             */
            return layout;
        }

        /**
         * Check if element is inside one of the table widgets
         */
        for (int i = 0; i < layout.table.getRowCount(); i++) {
            Element caption = layout.table.getWidget(i, layout.COLUMN_CAPTION)
                    .getElement();
            Element error = layout.table.getWidget(i, layout.COLUMN_ERRORFLAG)
                    .getElement();
            Element widget = layout.table.getWidget(i, layout.COLUMN_WIDGET)
                    .getElement();
            if (caption.isOrHasChild(e) || error.isOrHasChild(e)
                    || widget.isOrHasChild(e)) {
                return layout.table.getWidget(i, layout.COLUMN_WIDGET);
            }
        }

        /*
         * Is the element a element outside the row structure but inside the
         * layout
         */
        Element rowElement = layout.getRowFromChildElement(e,
                layout.getElement());
        if (rowElement != null) {
            Element tableElement = rowElement.getParentElement();
            for (int i = 0; i < tableElement.getChildCount(); i++) {
                Element r = tableElement.getChild(i).cast();
                if (r.equals(rowElement)) {
                    return layout.table.getWidget(i, layout.COLUMN_WIDGET);
                }
            }
        }

        /*
         * Element was not found in rows so defaulting to the form layout
         * instead
         */
        return layout;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #dragOver(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    public void dragOver(VDragEvent drag) {

        // Remove any emphasis
        layout.emphasis(null, null);

        // Update the drop details so we can validate the drop
        Widget c = getTableRowWidgetFromDragEvent(drag);
        if (c != null) {
            layout.updateDropDetails(c, drag);
        } else {
            layout.updateDropDetails(layout, drag);
        }

        layout.postOverHook(drag);

        // Validate the drop
        validate(new VAcceptCallback() {
            public void accepted(VDragEvent event) {
                Widget c = getTableRowWidgetFromDragEvent(event);
                if (c != null) {
                    layout.emphasis(c, event);
                } else {
                    layout.emphasis(layout, event);
                }
            }
        }, drag);
    };

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #dragEnter(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    public void dragEnter(VDragEvent drag) {
        layout.emphasis(null, null);

        Widget c = getTableRowWidgetFromDragEvent(drag);
        if (c != null) {
            layout.updateDropDetails(c, drag);
        } else {
            layout.updateDropDetails(layout, drag);
        }
        super.dragEnter(drag);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
     * #dragLeave(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
     */
    @Override
    public void dragLeave(VDragEvent drag) {
        layout.emphasis(null, drag);
        layout.postLeaveHook(drag);
    }

    @Override
    public ComponentConnector getConnector() {
        return ConnectorMap.get(client).getConnector(layout);
    };
}
