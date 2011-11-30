/*
 * Copyright 2011 John Ahlroos
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package fi.jasoft.dragdroplayouts.drophandlers;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.DDVerticalLayout.VerticalLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;

/**
 * A default drop handler for vertical layouts
 */
@SuppressWarnings("serial")
public class DefaultVerticalLayoutDropHandler implements DropHandler {

    private Alignment dropAlignment;

    /**
     * Construcor
     */
    public DefaultVerticalLayoutDropHandler() {
        // Default
    }

    /**
     * Constructor
     * 
     * @param dropCellAlignment
     *            The cell alignment of the component after it has been dropped
     */
    public DefaultVerticalLayoutDropHandler(Alignment dropCellAlignment) {
        this.dropAlignment = dropCellAlignment;
    }

    /**
     * Called when a component changed location within the layout
     * 
     * @param event
     *            The drag and drop event
     */
    protected void handleComponentReordering(DragAndDropEvent event) {
        // Component re-ordering
        LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                .getTransferable();
        VerticalLayoutTargetDetails details = (VerticalLayoutTargetDetails) event
                .getTargetDetails();
        AbstractOrderedLayout layout = (AbstractOrderedLayout) details
                .getTarget();
        Component comp = transferable.getComponent();
        int idx = (details).getOverIndex();

        // Detach
        layout.removeComponent(comp);
        idx--;

        // Increase index if component is dropped after or above a previous
        // component
        VerticalDropLocation loc = details.getDropLocation();
        if (loc == VerticalDropLocation.MIDDLE
                || loc == VerticalDropLocation.BOTTOM) {
            idx++;
        }

        // Add component
        if (idx >= 0) {
            layout.addComponent(comp, idx);
        } else {
            layout.addComponent(comp);
        }

        // Add component alignment if given
        if (dropAlignment != null) {
            layout.setComponentAlignment(comp, dropAlignment);
        }

    }

    /**
     * Handles a drop by a component which has an absolute layout as parent. In
     * this case the component is moved.
     * 
     * @param event
     *            The drag and drop event
     */
    protected void handleDropFromAbsoluteParentLayout(DragAndDropEvent event) {
        LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                .getTransferable();
        VerticalLayoutTargetDetails details = (VerticalLayoutTargetDetails) event
                .getTargetDetails();
        MouseEventDetails mouseDown = transferable.getMouseDownEvent();
        MouseEventDetails mouseUp = details.getMouseEvent();
        int movex = mouseUp.getClientX() - mouseDown.getClientX();
        int movey = mouseUp.getClientY() - mouseDown.getClientY();
        Component comp = transferable.getComponent();

        DDAbsoluteLayout parent = (DDAbsoluteLayout) comp.getParent();
        ComponentPosition position = parent.getPosition(comp);

        float x = position.getLeftValue() + movex;
        float y = position.getTopValue() + movey;
        position.setLeft(x, Sizeable.UNITS_PIXELS);
        position.setTop(y, Sizeable.UNITS_PIXELS);
    }

    /**
     * Handle a drop from another layout
     * 
     * @param event
     *            The drag and drop event
     */
    protected void handleDropFromLayout(DragAndDropEvent event) {
        LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                .getTransferable();
        VerticalLayoutTargetDetails details = (VerticalLayoutTargetDetails) event
                .getTargetDetails();
        AbstractOrderedLayout layout = (AbstractOrderedLayout) details
                .getTarget();
        Component source = event.getTransferable().getSourceComponent();
        int idx = (details).getOverIndex();
        Component comp = transferable.getComponent();

        // Check that we are not dragging an outer layout into an inner
        // layout
        Component parent = layout.getParent();
        while (parent != null) {
            if (parent == comp) {
                return;
            }
            parent = parent.getParent();
        }

        // If source is an instance of a component container then remove
        // it
        // from there,
        // the component cannot have two parents.
        if (source instanceof ComponentContainer) {
            ComponentContainer sourceLayout = (ComponentContainer) source;
            sourceLayout.removeComponent(comp);
        }

        // Increase index if component is dropped after or above a
        // previous
        // component
        VerticalDropLocation loc = (details).getDropLocation();
        if (loc == VerticalDropLocation.MIDDLE
                || loc == VerticalDropLocation.BOTTOM) {
            idx++;
        }

        // Add component
        if (idx >= 0) {
            layout.addComponent(comp, idx);
        } else {
            layout.addComponent(comp);
        }

        // Add component alignment if given
        if (dropAlignment != null) {
            layout.setComponentAlignment(comp, dropAlignment);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.event.dd.DropHandler#drop(com.vaadin.event.dd.DragAndDropEvent
     * )
     */
    public void drop(DragAndDropEvent event) {
        // Get information about the drop
        VerticalLayoutTargetDetails details = (VerticalLayoutTargetDetails) event
                .getTargetDetails();
        AbstractOrderedLayout layout = (AbstractOrderedLayout) details
                .getTarget();
        Component source = event.getTransferable().getSourceComponent();

        if (layout == source) {
            handleComponentReordering(event);
        } else if (event.getTransferable() instanceof LayoutBoundTransferable) {
            LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                    .getTransferable();
            Component comp = transferable.getComponent();
            if (comp == layout) {
                if (comp.getParent() instanceof DDAbsoluteLayout) {
                    handleDropFromAbsoluteParentLayout(event);
                }
            } else {
                handleDropFromLayout(event);
            }
        }
    }

    public AcceptCriterion getAcceptCriterion() {
        // By default we accept drops from everything
        return AcceptAll.get();
    }
}
