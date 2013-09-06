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
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import fi.jasoft.dragdroplayouts.DDHorizontalSplitPanel;
import fi.jasoft.dragdroplayouts.DDVerticalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultHorizontalSplitPanelDropHandler;

@SuppressWarnings("serial")
public class DragdropHorizontalSplitPanelDemo extends DemoView {

	public static final String NAME = "dd-horizontal-splitpanel";
	
    private int buttonCount = 1;
    
	public DragdropHorizontalSplitPanelDemo(Navigator navigator) {
		super(navigator);		
	}

	@Override
	public Component getLayout() {
		//start-source
		 CssLayout root = new CssLayout();
	        root.setSizeFull();
	      
	        Label lbl = new Label(
	                "To the left are some buttons, and to the right is a horizontal split panel. "
	                        + "Try dragging the buttons on to the splitpanel. If a component already exists in the SplitPanel it is replaced with the dragged one.");
	        root.addComponent(lbl);

	        // Wrapping components in a horizontal layout
	        HorizontalLayout inner = new HorizontalLayout();
	        inner.setMargin(true);
	        inner.setSizeFull();
	        inner.setSpacing(true);
	        root.addComponent(inner);

	        // Add some buttons to a vertical layout with dragging enabled
	        final DDVerticalLayout btns = new DDVerticalLayout();
	        btns.setDragMode(LayoutDragMode.CLONE);
	        btns.setSizeUndefined();
	        btns.setSpacing(true);
	        String caption = "Button ";
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        inner.addComponent(btns);

	        // Create a drag & drop horizontal split panel
	        final DDHorizontalSplitPanel panel = new DDHorizontalSplitPanel();
	        panel.setSizeFull();
	        
	        inner.addComponent(panel);
	        inner.setExpandRatio(panel, 1);

	        // Enable dragging
	        panel.setDragMode(LayoutDragMode.CLONE);

	        // Enable dropping
	        panel.setDropHandler(new DefaultHorizontalSplitPanelDropHandler());
	        
	        //end-source
	        return root;
	}

	@Override
	public String getCaption() {		
		return "Horizontal split panel";
	}
}
