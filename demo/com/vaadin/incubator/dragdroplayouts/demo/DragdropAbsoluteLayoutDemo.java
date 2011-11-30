package com.vaadin.incubator.dragdroplayouts.demo;

import com.vaadin.incubator.dragdroplayouts.DDAbsoluteLayout;
import com.vaadin.incubator.dragdroplayouts.DDAccordion;
import com.vaadin.incubator.dragdroplayouts.DDGridLayout;
import com.vaadin.incubator.dragdroplayouts.DDHorizontalLayout;
import com.vaadin.incubator.dragdroplayouts.DDHorizontalSplitPanel;
import com.vaadin.incubator.dragdroplayouts.DDTabSheet;
import com.vaadin.incubator.dragdroplayouts.DDVerticalLayout;
import com.vaadin.incubator.dragdroplayouts.DDVerticalSplitPanel;
import com.vaadin.incubator.dragdroplayouts.client.ui.LayoutDragMode;
import com.vaadin.incubator.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;
import com.vaadin.incubator.dragdroplayouts.drophandlers.DefaultAccordionDropHandler;
import com.vaadin.incubator.dragdroplayouts.drophandlers.DefaultGridLayoutDropHandler;
import com.vaadin.incubator.dragdroplayouts.drophandlers.DefaultHorizontalLayoutDropHandler;
import com.vaadin.incubator.dragdroplayouts.drophandlers.DefaultHorizontalSplitPanelDropHandler;
import com.vaadin.incubator.dragdroplayouts.drophandlers.DefaultTabSheetDropHandler;
import com.vaadin.incubator.dragdroplayouts.drophandlers.DefaultVerticalLayoutDropHandler;
import com.vaadin.incubator.dragdroplayouts.drophandlers.DefaultVerticalSplitPanelDropHandler;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

public class DragdropAbsoluteLayoutDemo extends CustomComponent implements
        DragdropDemo {

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
    }

    public String getCodePath() {
        return "com/vaadin/incubator/dragdroplayouts/demo/code/absolutelayout.txt";
    }
}
