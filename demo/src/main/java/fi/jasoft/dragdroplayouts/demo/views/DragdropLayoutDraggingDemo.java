/*
 * Copyright 2014 John Ahlroos
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
package fi.jasoft.dragdroplayouts.demo.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;

public class DragdropLayoutDraggingDemo extends DemoView {

	public static final String NAME = "dd-dragging-layouts";
	    
	public DragdropLayoutDraggingDemo(Navigator navigator) {
		super(navigator);		
	}
	
	@Override
	public Component getLayout() {
		//start-source
		 final DDAbsoluteLayout layout = new DDAbsoluteLayout();
	        layout.setSizeFull();
	        
	        // Enable dragging components
	        layout.setDragMode(LayoutDragMode.CLONE);

	        // Enable dropping components
	        layout.setDropHandler(new DefaultAbsoluteLayoutDropHandler());

	        // Add some components
	        layout.addComponent(
	                new Label(
	                        "This demo shows how you can drag a layout with buttons in it. Try to drag the button group to see what happens"),
	                "left:5px;top:5px");

	        // A vertical layout which has dragging turned off by default
	        VerticalLayout buttons = new VerticalLayout();
	        buttons.setSizeUndefined();
	        buttons.setCaption("Button group");
	        buttons.setSpacing(true);
	        buttons.addComponent(new Button("Button1"));
	        buttons.addComponent(new Button("Button2"));
	        buttons.addComponent(new Button("Button3"));
	        buttons.addComponent(new Button("Button4"));

	        layout.addComponent(buttons, "left:50px;top:50px");
	        //end-source
	        return layout;
	}

	@Override
	public String getCaption() {		
		return "Dragging inner layouts";
	}
}
