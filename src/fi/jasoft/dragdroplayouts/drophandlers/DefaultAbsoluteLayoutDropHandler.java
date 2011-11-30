/*
 * Copyright 2011 John Ahlroos
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
package fi.jasoft.dragdroplayouts.drophandlers;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.DDAbsoluteLayout.AbsoluteLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;

/**
 * A default drop handler for absolute layouts
 *
 */
@SuppressWarnings("serial")
public class DefaultAbsoluteLayoutDropHandler implements DropHandler {

	/**
	 * Called when a component changed location within the layout
	 * 
	 * @param event
	 * 		The drag and drop event
	 */
	protected void handleComponentReordering(DragAndDropEvent event){
		AbsoluteLayoutTargetDetails details = (AbsoluteLayoutTargetDetails) event
		            .getTargetDetails();
		DDAbsoluteLayout layout = (DDAbsoluteLayout) details.getTarget();
		LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
		        .getTransferable();
		Component component = transferable.getComponent();
		
		// Get top-left pixel position
		int leftPixelPosition = details.getRelativeLeft();
		int topPixelPosition = details.getRelativeTop();
	    
		ComponentPosition position = layout.getPosition(component);

		position.setLeft((float) leftPixelPosition, Sizeable.UNITS_PIXELS);
		position.setTop((float) topPixelPosition, Sizeable.UNITS_PIXELS);
	}
	
	/**
	 * Handles a drop by a component which has an absolute layout as parent. In this
	 * case the component is moved.
	 * 
	 * @param event
	 * 		The drag and drop event
	 */
	protected void handleDropFromAbsoluteParentLayout(DragAndDropEvent event){
		AbsoluteLayoutTargetDetails details = (AbsoluteLayoutTargetDetails) event
	            .getTargetDetails();
		LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
		        .getTransferable();
		Component component = transferable.getComponent();

		MouseEventDetails mouseDown = transferable.getMouseDownEvent();
		MouseEventDetails mouseUp = details.getMouseEvent();
		int movex = mouseUp.getClientX() - mouseDown.getClientX();
		int movey = mouseUp.getClientY() - mouseDown.getClientY();

		DDAbsoluteLayout parent = (DDAbsoluteLayout) component.getParent();
		ComponentPosition position = parent.getPosition(component);

		float x = position.getLeftValue() + movex;
		float y = position.getTopValue() + movey;
		position.setLeft(x, Sizeable.UNITS_PIXELS);
		position.setTop(y, Sizeable.UNITS_PIXELS);
	}
	
	/**
	 * Handle a drop from another layout
	 * 
	 * @param event
	 * 		The drag and drop event
	 */	
	protected void handleDropFromLayout(DragAndDropEvent event){
		AbsoluteLayoutTargetDetails details = (AbsoluteLayoutTargetDetails) event
	            .getTargetDetails();
		LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
		        .getTransferable();
		Component component = transferable.getComponent();
		Component source = event.getTransferable().getSourceComponent();
		DDAbsoluteLayout layout = (DDAbsoluteLayout) details.getTarget();
		int leftPixelPosition = details.getRelativeLeft();
		int topPixelPosition = details.getRelativeTop();

		 // Check that we are not dragging an outer layout into an
        // inner
        // layout
        Component parent = source.getParent();
        while (parent != null) {
            parent = parent.getParent();
        }

        // remove component from source
        if (source instanceof ComponentContainer) {
            ComponentContainer sourceLayout = (ComponentContainer) source;
            sourceLayout.removeComponent(component);
        }

        // Add component to absolute layout
        layout.addComponent(component, "left:" + leftPixelPosition
                + "px;top:" + topPixelPosition + "px");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vaadin.event.dd.DropHandler#drop(com.vaadin.event.dd.DragAndDropEvent)
	 */
    public void drop(DragAndDropEvent event) {
        AbsoluteLayoutTargetDetails details = (AbsoluteLayoutTargetDetails) event
                .getTargetDetails();
        Component source = event.getTransferable().getSourceComponent();
        DDAbsoluteLayout layout = (DDAbsoluteLayout) details.getTarget();
        LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                .getTransferable();
        Component component = transferable.getComponent();

        if (layout == source) {
        	handleComponentReordering(event);
        } else if (event.getTransferable() instanceof LayoutBoundTransferable) {
            if (component == layout) {
                if (component.getParent() instanceof DDAbsoluteLayout) {
                   handleDropFromAbsoluteParentLayout(event);
                }
            } else {
               handleDropFromLayout(event);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.vaadin.event.dd.DropHandler#getAcceptCriterion()
     */
    public AcceptCriterion getAcceptCriterion() {
        return AcceptAll.get();
    }
}
