package com.vaadin.incubator.dragdroplayouts.demo;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.incubator.dragdroplayouts.DDVerticalLayout;
import com.vaadin.incubator.dragdroplayouts.DDVerticalLayout.VerticalLayoutTargetDetails;
import com.vaadin.incubator.dragdroplayouts.client.ui.LayoutDragMode;
import com.vaadin.incubator.dragdroplayouts.events.LayoutBoundTransferable;
import com.vaadin.incubator.dragdroplayouts.events.VerticalLocationIs;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class DragdropVerticalLayoutDemo extends CustomComponent implements
        DragdropDemo {

	private static final float EQUAL_VERTICAL_RATIO = 0.3f;
	
    public DragdropVerticalLayoutDemo() {
        setCaption("Vertical layout");
        setSizeFull();

        final DDVerticalLayout layout = new DDVerticalLayout();
        setCompositionRoot(layout);
        layout.setComponentVerticalDropRatio(EQUAL_VERTICAL_RATIO);
        layout.setDragMode(LayoutDragMode.CLONE);
        layout.setDropHandler(new DropHandler() {

            public AcceptCriterion getAcceptCriterion() {
                return new Not(VerticalLocationIs.MIDDLE);
            }

            public void drop(DragAndDropEvent event) {
                LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                        .getTransferable();

                VerticalLayoutTargetDetails details = (VerticalLayoutTargetDetails) event
                        .getTargetDetails();

                Component comp = transferable.getComponent();

                int currentIndex = layout.getComponentIndex(comp);
                int newIndex = details.getOverIndex();

                layout.removeComponent(comp);

                if (currentIndex > newIndex
                        && details.getDropLocation() == VerticalDropLocation.BOTTOM) {
                    newIndex++;
                }

                layout.addComponent(comp, newIndex);
            }
        });

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

    public String getCodePath() {
        return "com/vaadin/incubator/dragdroplayouts/demo/code/verticallayout.txt";
    }
}
