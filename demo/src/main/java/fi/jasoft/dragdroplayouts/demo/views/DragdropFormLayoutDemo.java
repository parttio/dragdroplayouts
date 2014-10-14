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
package fi.jasoft.dragdroplayouts.demo.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import fi.jasoft.dragdroplayouts.DDFormLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultFormLayoutDropHandler;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DragdropFormLayoutDemo extends DemoView {

  public static final String NAME = "dd-form-layout";

  public DragdropFormLayoutDemo(Navigator navigator) {
    super(navigator);
  }

  @Override
  public Component getLayout() {
    // start-source
    // Create a form layout
    DDFormLayout layout = new DDFormLayout();
    layout.setSizeFull();

    // Allow dragging all components
    layout.setDragMode(LayoutDragMode.CLONE);

    // Limit dragging to the TextFields
    layout.setDragFilter(new DragFilter() {
      public boolean isDraggable(Component component) {
        return component instanceof TextField;
      }
    });

    // Allow dropping
    layout.setDropHandler(new DefaultFormLayoutDropHandler());

    // Add some dummy fields to the layout
    layout.addComponent(new Label("This is a FormLayout, try re-arranging the fields."));
    layout.addComponent(new TextField("Name"));
    layout.addComponent(new TextField("Street Address"));
    layout.addComponent(new TextField("Email address"));
    layout.addComponent(new Button("Submit", new Button.ClickListener() {
      public void buttonClick(ClickEvent event) {
        Notification.show("Form submitted");
      }
    }));
    // end-source
    return layout;
  }

  @Override
  public String getCaption() {
    return "Form layout";
  }
}
