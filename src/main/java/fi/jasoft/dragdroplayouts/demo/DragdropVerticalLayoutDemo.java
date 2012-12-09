package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import fi.jasoft.dragdroplayouts.DDVerticalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultVerticalLayoutDropHandler;

@SuppressWarnings("serial")
public class DragdropVerticalLayoutDemo extends CustomComponent {

    private static final float EQUAL_VERTICAL_RATIO = 0.3f;

    public DragdropVerticalLayoutDemo() {
        setCaption("Vertical layout");
        setSizeFull();

        final DDVerticalLayout layout = new DDVerticalLayout();
        setCompositionRoot(layout);
        layout.setComponentVerticalDropRatio(EQUAL_VERTICAL_RATIO);
        layout.setDragMode(LayoutDragMode.CLONE);
        layout.setDropHandler(new DefaultVerticalLayoutDropHandler());

        layout.addComponent(new Label(
                "These components are stacked vertically, try reordering them"));
        Button btn = new Button("Button 1");
        btn.setWidth("100px");
        layout.addComponent(btn);
        btn = new Button("Button 2");
        btn.setWidth("150px");
        layout.addComponent(btn);
        btn = new Button("Button 3");
        btn.setWidth("200px");
        layout.addComponent(btn);
    }
}
