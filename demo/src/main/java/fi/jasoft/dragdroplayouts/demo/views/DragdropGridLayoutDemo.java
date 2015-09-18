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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.DDVerticalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultGridLayoutDropHandler;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultVerticalLayoutDropHandler;

@SuppressWarnings("serial")
public class DragdropGridLayoutDemo extends DemoView {

  public static final String NAME = "dd-grid-layout";

  private static final int ROWS = 4;
  private static final int COLUMNS = 4;

  public DragdropGridLayoutDemo(Navigator navigator) {
    super(navigator);
  }

  @Override
  public Component getLayout() {
    // start-source
    DDVerticalLayout outer = new DDVerticalLayout();
    outer.setSizeFull();
    outer.setDragMode(LayoutDragMode.CLONE);
    outer.setDropHandler(new DefaultVerticalLayoutDropHandler());
    
    Label lbl =
        new Label(
            "This is a grid layout with 16 cells, try dragging the buttons into an empty cell");
    outer.addComponent(lbl);

    // Create a drag and droppable grid layout
    final DDGridLayout layout = new DDGridLayout(COLUMNS, ROWS);
    layout.setWidth("400px");
    layout.setHeight("400px");

    // Only allow dropping in the center of the grid layout cell
    layout.setComponentHorizontalDropRatio(0);
    layout.setComponentVerticalDropRatio(0);

    outer.addComponent(layout);
    outer.setExpandRatio(layout, 1);

    // Enable dragging components
    layout.setDragMode(LayoutDragMode.CLONE);

    // Enable dropping components
    layout.setDropHandler(new DefaultGridLayoutDropHandler());

    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {
        if (row == 0 || row == ROWS - 1 || col == 0 || col == COLUMNS - 1) {
          Button btn = new Button("Button");
          layout.addComponent(btn, col, row);
          layout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
        }
      }
    }

    // end-source
    return outer;
  }

  @Override
  public String getCaption() {
    return "Grid layout";
  }
}
