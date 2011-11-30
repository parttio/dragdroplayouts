package com.vaadin.incubator.dragdroplayouts.demo;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.incubator.dragdroplayouts.DDHorizontalLayout;
import com.vaadin.incubator.dragdroplayouts.DDHorizontalLayout.HorizontalLayoutTargetDetails;
import com.vaadin.incubator.dragdroplayouts.client.ui.LayoutDragMode;
import com.vaadin.incubator.dragdroplayouts.events.HorizontalLocationIs;
import com.vaadin.incubator.dragdroplayouts.events.LayoutBoundTransferable;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class DragdropHorizontalLayoutDemo extends CustomComponent implements
        DragdropDemo {
	
	private static final float EQUAL_HORIZONTAL_RATIO = 0.3f;
	
    public DragdropHorizontalLayoutDemo() {
        setCaption("Horizontal layout");
        setSizeFull();

        final DDHorizontalLayout layout = new DDHorizontalLayout();
        setCompositionRoot(layout);
        layout.setComponentHorizontalDropRatio(EQUAL_HORIZONTAL_RATIO);
        layout.setDragMode(LayoutDragMode.CLONE);
        layout.setDropHandler(new DropHandler() {

            public AcceptCriterion getAcceptCriterion() {
                return new Not(HorizontalLocationIs.CENTER);
            }

            public void drop(DragAndDropEvent event) {
                LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                        .getTransferable();

                HorizontalLayoutTargetDetails details = (HorizontalLayoutTargetDetails) event
                        .getTargetDetails();

                Component comp = transferable.getComponent();

                int currentIndex = layout.getComponentIndex(comp);
                int newIndex = details.getOverIndex();

                layout.removeComponent(comp);

                if (currentIndex > newIndex
                        && details.getDropLocation() == HorizontalDropLocation.RIGHT) {
                    newIndex++;
                }

                layout.addComponent(comp, newIndex);
            }
        });

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

    public String getCodePath() {
        return "com/vaadin/incubator/dragdroplayouts/demo/code/horizontallayout.txt";
    }
}
