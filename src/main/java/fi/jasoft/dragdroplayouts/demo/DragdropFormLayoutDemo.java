package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import fi.jasoft.dragdroplayouts.DDFormLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultFormLayoutDropHandler;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;

public class DragdropFormLayoutDemo extends CustomComponent {

    public DragdropFormLayoutDemo() {
        setCaption("FormLayout");

        // Create a form layout
        DDFormLayout layout = new DDFormLayout();
        layout.setSizeFull();
        setCompositionRoot(layout);

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
        layout.addComponent(new Label(
                "This is a FormLayout, try re-arranging the fields."));
        layout.addComponent(new TextField("Name"));
        layout.addComponent(new TextField("Street Address"));
        layout.addComponent(new TextField("Email address"));
        layout.addComponent(new Button("Submit", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                Notification.show("Form submitted");
            }
        }));
    }
}
