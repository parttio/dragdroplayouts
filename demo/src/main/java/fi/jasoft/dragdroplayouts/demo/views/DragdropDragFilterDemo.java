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
package fi.jasoft.dragdroplayouts.demo.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DragdropDragFilterDemo extends DemoView {

  public static final String NAME = "dd-drag-filters";

  public DragdropDragFilterDemo(Navigator navigator) {
    super(navigator);
  }

  @Override
  public Component getLayout() {
    // start-source
    // Create an absolute layout
    DDAbsoluteLayout layout = new DDAbsoluteLayout();
    layout.setSizeFull();

    // Enable dragging (of all) components
    layout.setDragMode(LayoutDragMode.CLONE);

    // Limit dragging to only buttons
    layout.setDragFilter((DragFilter) component -> component instanceof Button);

    // Enable dropping components
    layout.setDropHandler(new DefaultAbsoluteLayoutDropHandler());

    // Add some components to layout
    layout.addComponent(new Label(
        "DragFilters allow you to control which components are draggable."
            + " All components in this example are in the same layout but only buttons are "
            + "draggable"));
    layout.addComponent(new Button("Drag Me!"), "left:50px;top:100px");
    layout.addComponent(new Button("Drag Me Too!"), "left:50px;top:150px");
    layout.addComponent(new TextField(null, "You cannot drag me!"), "left:50px;top:200px");
    // end-source
    return layout;
  }

  @Override
  public String getCaption() {
    return "Drag filters";
  }
}
