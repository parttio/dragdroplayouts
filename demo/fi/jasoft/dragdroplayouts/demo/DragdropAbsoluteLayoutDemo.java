package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.DDFormLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultFormLayoutDropHandler;

public class DragdropAbsoluteLayoutDemo extends CustomComponent {

    public DragdropAbsoluteLayoutDemo() {
        setCaption("Absolute Layout");
        setSizeFull();

        final DDAbsoluteLayout layout = new DDAbsoluteLayout();
        layout.setSizeFull();
        setCompositionRoot(layout);

        // Enable dragging components
        layout.setDragMode(LayoutDragMode.CLONE);

        // Enable dropping components
        layout.setDropHandler(new DefaultAbsoluteLayoutDropHandler());

        // Add some components
        Label lbl = new Label(
                "This is an Absolute layout, you can freely drag the components around");
        layout.addComponent(lbl);
        Button btn = new Button("Button 1", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                getApplication().getMainWindow().showNotification("Click!");
            }
        });
        layout.addComponent(btn, "left:50px; top:50px");
        Link link = new Link("A link to Vaadin", new ExternalResource(
                "http://www.vaadin.com"));
        layout.addComponent(link, "left:200px; top:100px");

        DDFormLayout form = new DDFormLayout();
        form.setWidth("200px");
        form.setHeight("200px");
        form.setDropHandler(new DefaultFormLayoutDropHandler());
        layout.addComponent(form, "left:200px; top:200px");

    }
}
