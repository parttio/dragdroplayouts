/*
 * Copyright 2015 Yuriy Artamonov
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

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import fi.jasoft.dragdroplayouts.DDPanel;
import fi.jasoft.dragdroplayouts.DragCaption;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultPanelDropHandler;

public class DragdropDragCaptionDemo extends DemoView {

    public static final String NAME = "dd-caption";

    public DragdropDragCaptionDemo(Navigator navigator) {
        super(navigator);
    }

    @Override
    public Component getLayout() {
        // start-source
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        DDPanel panel1 = new DDPanel("Source");
        panel1.setWidth("200px");
        panel1.setHeight("200px");
        panel1.setDragMode(LayoutDragMode.CLONE);
        panel1.setDropHandler(new DefaultPanelDropHandler());

        panel1.setDragCaptionProvider(component ->
                new DragCaption(
                        "<u>Custom drag caption:</u><br/>" + component.getClass().getSimpleName(),
                        VaadinIcons.AIRPLANE,
                        ContentMode.HTML)
        );

        layout.addComponent(panel1);

        Button content = new Button("Drag me!");
        content.setSizeFull();
        panel1.setContent(content);

        DDPanel panel2 = new DDPanel("Destination");
        panel2.setWidth("200px");
        panel2.setHeight("200px");
        panel2.setDragMode(LayoutDragMode.CLONE);
        panel2.setDropHandler(new DefaultPanelDropHandler());

        panel2.setDragCaptionProvider(component ->
                new DragCaption("Drag caption goes back! "
                        + component.getClass().getSimpleName(), VaadinIcons.AIRPLANE));

        layout.addComponent(panel2);

        // end-source
        Label label = new Label(
                "In this demo you can drag the button." +
                        "\nYou will see custom drag caption(with icon) provided by DragCaptionProvider");
        label.setContentMode(ContentMode.PREFORMATTED);
        return new VerticalLayout(
                label,
                layout);
    }

    @Override
    public String getCaption() {
        return "DragCaption";
    }

    @Override
    public String getName() {
        return NAME;
    }
}