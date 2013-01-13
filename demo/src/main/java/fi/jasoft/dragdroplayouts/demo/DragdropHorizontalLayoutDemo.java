package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultHorizontalLayoutDropHandler;

@SuppressWarnings("serial")
public class DragdropHorizontalLayoutDemo extends CustomComponent {

    private static final float EQUAL_HORIZONTAL_RATIO = 0.3f;

    public DragdropHorizontalLayoutDemo() {
        setCaption("Horizontal layout");
        setSizeFull();

        final DDHorizontalLayout layout = new DDHorizontalLayout();
        setCompositionRoot(layout);
        layout.setComponentHorizontalDropRatio(EQUAL_HORIZONTAL_RATIO);
        layout.setDragMode(LayoutDragMode.CLONE);
        layout.setDropHandler(new DefaultHorizontalLayoutDropHandler());

        layout.addComponent(new Label(
                "These components are stacked horizontally, try reordering them"));
        Button btn1 = new Button("Button 1");
        btn1.setWidth("100px");
        layout.addComponent(btn1);

        Button btn2 = new Button("Button 2");
        btn2.setWidth("150px");
        layout.addComponent(btn2);

        Button btn3 = new Button("Button 3");
        btn3.setWidth("200px");
        layout.addComponent(btn3);
    }
}
