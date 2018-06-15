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

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.*;
import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;
import fi.jasoft.dragdroplayouts.interfaces.DragGrabFilter;

public class DragdropDragGrabFilterDemo extends DemoView {

    public static final String NAME = "dd-grab-filters";

    public DragdropDragGrabFilterDemo(Navigator navigator) {
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
        layout.setDragGrabFilter((DragGrabFilter) component -> {
            // Button/TextField inside of draggable Panel cannot be used/grabbed for Drag-n-Drop.
            return !(component instanceof Button)
                    && !(component instanceof TextField);
        });

        // Enable dropping components
        layout.setDropHandler(new DefaultAbsoluteLayoutDropHandler());

        // Add some components to layout
        Label label = new Label("DragGrabFilter enable you to control which nested components of draggable " +
                "child can be used/grabbed. All components in this example are composite and draggable, " +
                "but you cannot drag Panel using Button/TextField children");
        label.setWidth("650px");
        layout.addComponent(label);

        layout.addComponent(createCompositeChild("1"), "left:50px;top:100px");
        layout.addComponent(createCompositeChild("3"), "left:280px;top:100px");
        layout.addComponent(createCompositeChild("3"), "left:510px;top:100px");

        // end-source
        return layout;
    }

    private Panel createCompositeChild(String caption) {
        Panel compositePanel = new Panel();
        compositePanel.setWidthUndefined();
        compositePanel.setCaption("Composite Widget " + caption);

        VerticalLayout content = new VerticalLayout();
        content.setWidthUndefined();
        compositePanel.setContent(content);

        content.addComponent(new Label("Grab me to drag Panel"));
        content.addComponent(new Button("Cannot be grabbed"));
        content.addComponent(new TextField("Cannot be grabbed TextField"));
        content.addComponent(new Label("Grab me too!"));

        for (int i = 0; i < content.getComponentCount(); i++) {
            content.getComponent(i).setWidth(100, Unit.PERCENTAGE);
        }

        return compositePanel;
    }

    @Override
    public String getCaption() {
        return "DragGrab filters";
    }

    @Override
    public String getName() {
        return NAME;
    }
}