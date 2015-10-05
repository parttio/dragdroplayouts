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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDPanel;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultPanelDropHandler;

public class DragdropPanelDemo extends DemoView {

    public static final String NAME = "dd-panel";

    public DragdropPanelDemo(Navigator navigator) {
        super(navigator);
    }

    @Override
    public Component getLayout() {

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        DDPanel panel1 = new DDPanel("Source");
        panel1.setWidth("200px");
        panel1.setHeight("200px");
        panel1.setDragMode(LayoutDragMode.CLONE);
        panel1.setDropHandler(new DefaultPanelDropHandler());
        layout.addComponent(panel1);

        Button content = new Button("Drag me!");
        content.setSizeFull();
        panel1.setContent(content);

        DDPanel panel2 = new DDPanel("Destination");
        panel2.setWidth("200px");
        panel2.setHeight("200px");
        panel2.setDragMode(LayoutDragMode.CLONE);
        panel2.setDropHandler(new DefaultPanelDropHandler());
        layout.addComponent(panel2);

        return new VerticalLayout(
                new Label(
                        "In this demo you can drag the button from one Panel to the other one"),
                layout);
    }

    @Override
    public String getCaption() {
        return "Panel";
    }

}
