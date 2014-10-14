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
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

import fi.jasoft.dragdroplayouts.DDVerticalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultVerticalLayoutDropHandler;

@SuppressWarnings("serial")
public class DragdropVerticalLayoutDemo extends DemoView {

  public static final String NAME = "dd-vertical-layout";

  private static final float EQUAL_VERTICAL_RATIO = 0.3f;

  public DragdropVerticalLayoutDemo(Navigator navigator) {
    super(navigator);
  }

  @Override
  public Component getLayout() {
    // start-source
    final DDVerticalLayout layout = new DDVerticalLayout();
    layout.setComponentVerticalDropRatio(EQUAL_VERTICAL_RATIO);
    layout.setDragMode(LayoutDragMode.CLONE);
    layout.setDropHandler(new DefaultVerticalLayoutDropHandler());

    layout.addComponent(new Label("These components are stacked vertically, try reordering them"));
    Button btn = new Button("Button 1");
    btn.setWidth("100px");
    layout.addComponent(btn);
    btn = new Button("Button 2");
    btn.setWidth("150px");
    layout.addComponent(btn);
    btn = new Button("Button 3");
    btn.setWidth("200px");
    layout.addComponent(btn);
    // end-source
    return layout;
  }

  @Override
  public String getCaption() {
    return "Vertical layout";
  }
}
