package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.event.dd.acceptcriteria.Or;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.event.dd.acceptcriteria.SourceIsTarget;
import com.vaadin.shared.ui.dd.HorizontalDropLocation;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.DDHorizontalLayout.HorizontalLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.DDVerticalSplitPanel;
import fi.jasoft.dragdroplayouts.DDVerticalSplitPanel.VerticalSplitPanelTargetDetails;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultVerticalLayoutDropHandler;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultVerticalSplitPanelDropHandler;
import fi.jasoft.dragdroplayouts.events.HorizontalLocationIs;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;

@SuppressWarnings("serial")
public class DragdropVerticalSplitPanelDemo extends CustomComponent {

    private int buttonCount = 1;

    public DragdropVerticalSplitPanelDemo() {
        setCaption("Vertical SplitPanel");
        setSizeFull();

        VerticalLayout root = new VerticalLayout();
        root.setSpacing(true);
        root.setSizeFull();
        setCompositionRoot(root);

        Label lbl = new Label(
                "On top are some buttons, and below them is a vertical split panel. "
                        + "Try dragging the buttons on to the splitpanel. If a component already exists in the SplitPanel it is replaced with the dragged one.");
        root.addComponent(lbl);

        // Add some buttons to a vertical layout with dragging enabled
        final DDHorizontalLayout btns = new DDHorizontalLayout();
        btns.setSpacing(true);
        btns.setDragMode(LayoutDragMode.CLONE);
        String caption = "Button ";
        btns.addComponent(new Button(caption + buttonCount++));
        btns.addComponent(new Button(caption + buttonCount++));
        btns.addComponent(new Button(caption + buttonCount++));
        btns.addComponent(new Button(caption + buttonCount++));
        btns.addComponent(new Button(caption + buttonCount++));
        root.addComponent(btns);

        // Create a drag & drop horizontal split panel
        final DDVerticalSplitPanel panel = new DDVerticalSplitPanel();
        panel.setSizeFull();
        
        root.addComponent(panel);
        root.setExpandRatio(panel, 1);

        // Enable dragging
        panel.setDragMode(LayoutDragMode.CLONE);

        // Enable dropping
        panel.setDropHandler(new DefaultVerticalSplitPanelDropHandler());
    }
}
