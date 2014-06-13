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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultCssLayoutDropHandler;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DragdropCssLayoutDemo extends DemoView {

    public static final String NAME = "dd-css-layout";

    public DragdropCssLayoutDemo(Navigator navigator) {
	super(navigator);
    }

    @Override
    public Component getLayout() {
	// start-source
	final DDCssLayout cssLayout = new DDCssLayout();
	cssLayout.setSizeFull();

	// Enable dragging
	cssLayout.setDragMode(LayoutDragMode.CLONE);

	// Enable dropping
	cssLayout.setDropHandler(new DefaultCssLayoutDropHandler());

	// Only allow draggin buttons
	cssLayout.setDragFilter(new DragFilter() {
	    public boolean isDraggable(Component component) {
		return component instanceof Button;
	    }
	});

	// Add some components
	Label lbl = new Label(
		"This is an CSS layout, the positions are defined by css rules. Try dragging the components around.");
	cssLayout.addComponent(lbl);
	Button btn = new Button("Button 1", new Button.ClickListener() {
	    public void buttonClick(ClickEvent event) {
		Notification.show("Click!");
	    }
	});
	cssLayout.addComponent(btn);
	btn = new Button("Button 2", new Button.ClickListener() {
	    public void buttonClick(ClickEvent event) {
		Notification.show("Click!");
	    }
	});
	cssLayout.addComponent(btn);
	btn = new Button("Button 3", new Button.ClickListener() {
	    public void buttonClick(ClickEvent event) {
		Notification.show("Click!");
	    }
	});
	cssLayout.addComponent(btn);
	// end-source
	return cssLayout;
    }

    @Override
    public String getCaption() {
	return "CSSLayout";
    }
}
