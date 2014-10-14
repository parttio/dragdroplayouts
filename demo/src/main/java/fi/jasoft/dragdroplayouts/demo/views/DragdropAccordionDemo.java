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
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDAccordion;
import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAccordionDropHandler;

@SuppressWarnings("serial")
public class DragdropAccordionDemo extends DemoView {

  public static final String NAME = "dd-accordion";

  private static final float EQUAL_DROP_RATIO = 0.5f;

  public DragdropAccordionDemo(Navigator navigator) {
    super(navigator);
  }

  @Override
  public Component getLayout() {
    // start-source
    VerticalLayout v = new VerticalLayout();
    v.setSizeFull();
    v.setSpacing(true);

    Label lb =
        new Label(
            "This demo shows you how you can drag components into a Accordion and reorder the tabs. "
                + "Try dragging some Buttons on to the accordion to add them as tabs. You can "
                + "reorder the tabs by dragging on them");
    v.addComponent(lb);

    // Add some buttons to a vertical layout with dragging enabled
    final DDHorizontalLayout btns = new DDHorizontalLayout();
    btns.setSpacing(true);
    btns.setDragMode(LayoutDragMode.CLONE);
    btns.addComponent(new Button("One Button"));
    btns.addComponent(new Button("Second Button"));
    btns.addComponent(new Button("Third Button"));
    btns.addComponent(new Button("Fourth Button"));
    btns.addComponent(new Button("Fifth Button"));
    v.addComponent(btns);

    // Create an Accordion
    final DDAccordion acc = new DDAccordion();
    acc.setSizeFull();

    acc.setDragMode(LayoutDragMode.CLONE);

    acc.setComponentVerticalDropRatio(EQUAL_DROP_RATIO);

    acc.setDropHandler(new DefaultAccordionDropHandler());

    // Add a tab to the accordion
    VerticalLayout layout = new VerticalLayout();
    layout.setCaption("Tab 1");
    layout.addComponent(new Label("This is an example tab already in the accordion."));
    acc.addComponent(layout);

    layout = new VerticalLayout();
    layout.setCaption("Tab 2");
    layout.addComponent(new Label("This is an example tab already in the accordion."));
    acc.addComponent(layout);

    layout = new VerticalLayout();
    layout.setCaption("Tab 3");
    layout.addComponent(new Label("This is an example tab already in the accordion."));
    acc.addComponent(layout);

    v.addComponent(acc);
    v.setExpandRatio(acc, 1);

    // end-source
    return v;
  }

  @Override
  public String getCaption() {
    return "Accordion";
  }
}
