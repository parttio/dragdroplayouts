/*
 * Copyright 2012 John Ahlroos
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
import com.vaadin.ui.Component;

import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.DDCssLayout.CssLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;

/**
 * Default CSS Layout drop handler
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.7.0
 *
 */
@SuppressWarnings("serial")
public class DefaultCssLayoutDropHandler implements DropHandler {

	
	/**
	 * Constructor
	 */
	public DefaultCssLayoutDropHandler() {
		// Default
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.vaadin.event.dd.DropHandler#drop(com.vaadin.event.dd.DragAndDropEvent)
	 */
	public void drop(DragAndDropEvent event) {
		CssLayoutTargetDetails details = (CssLayoutTargetDetails)event.getTargetDetails();
		LayoutBoundTransferable transferable = (LayoutBoundTransferable)event.getTransferable();
		DDCssLayout layout = (DDCssLayout) details.getTarget();
	    Component comp = transferable.getComponent();
	    int idx = details.getOverIndex();
		
	    // Detach
        layout.removeComponent(comp);
        
        // Add component
        if (idx >= 0 && idx < layout.getComponentCount()) {
            layout.addComponent(comp, idx);
        } else {
            layout.addComponent(comp);
        }
	}

	/*
	 * (non-Javadoc)
	 * @see com.vaadin.event.dd.DropHandler#getAcceptCriterion()
	 */
	public AcceptCriterion getAcceptCriterion() {
		// Allows dropping everywhere
		return AcceptAll.get();
	}

}
