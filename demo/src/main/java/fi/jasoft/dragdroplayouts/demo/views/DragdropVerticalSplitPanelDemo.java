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
package fi.jasoft.dragdroplayouts.demo.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.DDVerticalSplitPanel;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultVerticalSplitPanelDropHandler;

@SuppressWarnings("serial")
public class DragdropVerticalSplitPanelDemo extends DemoView {
	
	public static final String NAME = "dd-vertical-splitpanel";
	
    private int buttonCount = 1;    

	public DragdropVerticalSplitPanelDemo(Navigator navigator) {
		super(navigator);		
	}

	@Override
	public Component getLayout() {
		//start-source
		  VerticalLayout root = new VerticalLayout();
	        root.setSpacing(true);
	        root.setSizeFull();
	     
	        Label lbl = new Label(
	                "On top are some buttons, and below them is a vertical split panel. "
	                        + "Try dragging the buttons on to the splitpanel. If a component already exists in the SplitPanel it is replaced with the dragged one.");
	        root.addComponent(lbl);

	        // Add some buttons to a vertical layout with dragging enabled
	        final DDHorizontalLayout btns = new DDHorizontalLayout();
	        btns.setSpacing(true);
	        btns.setDragMode(LayoutDragMode.CLONE);
	        String caption = "Button ";
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        root.addComponent(btns);

	        // Create a drag & drop horizontal split panel
	        final DDVerticalSplitPanel panel = new DDVerticalSplitPanel();
	        panel.setSizeFull();
	        
	        root.addComponent(panel);
	        root.setExpandRatio(panel, 1);

	        // Enable dragging
	        panel.setDragMode(LayoutDragMode.CLONE);

	        // Enable dropping
	        panel.setDropHandler(new DefaultVerticalSplitPanelDropHandler());
	        //end-source
	        return root;
	}

	@Override
	public String getCaption() {	
		return "Vertical split panel";
	}
}
