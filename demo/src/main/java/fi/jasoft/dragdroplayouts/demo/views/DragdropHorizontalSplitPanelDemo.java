package fi.jasoft.dragdroplayouts.demo.views;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.And;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.event.dd.acceptcriteria.Or;
import com.vaadin.event.dd.acceptcriteria.SourceIs;
import com.vaadin.event.dd.acceptcriteria.SourceIsTarget;
import com.vaadin.navigator.Navigator;
import com.vaadin.shared.ui.dd.HorizontalDropLocation;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import fi.jasoft.dragdroplayouts.DDHorizontalSplitPanel;
import fi.jasoft.dragdroplayouts.DDHorizontalSplitPanel.HorizontalSplitPanelTargetDetails;
import fi.jasoft.dragdroplayouts.DDVerticalLayout;
import fi.jasoft.dragdroplayouts.DDVerticalLayout.VerticalLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultHorizontalSplitPanelDropHandler;
import fi.jasoft.dragdroplayouts.events.HorizontalLocationIs;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.events.VerticalLocationIs;

@SuppressWarnings("serial")
public class DragdropHorizontalSplitPanelDemo extends DemoView {

	public static final String NAME = "dd-horizontal-splitpanel";
	
    private int buttonCount = 1;
    
	public DragdropHorizontalSplitPanelDemo(Navigator navigator) {
		super(navigator);		
	}

	@Override
	public Component getLayout() {
		//start-source
		 CssLayout root = new CssLayout();
	        root.setSizeFull();
	      
	        Label lbl = new Label(
	                "To the left are some buttons, and to the right is a horizontal split panel. "
	                        + "Try dragging the buttons on to the splitpanel. If a component already exists in the SplitPanel it is replaced with the dragged one.");
	        root.addComponent(lbl);

	        // Wrapping components in a horizontal layout
	        HorizontalLayout inner = new HorizontalLayout();
	        inner.setMargin(true);
	        inner.setSizeFull();
	        inner.setSpacing(true);
	        root.addComponent(inner);

	        // Add some buttons to a vertical layout with dragging enabled
	        final DDVerticalLayout btns = new DDVerticalLayout();
	        btns.setDragMode(LayoutDragMode.CLONE);
	        btns.setSizeUndefined();
	        btns.setSpacing(true);
	        String caption = "Button ";
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        btns.addComponent(new Button(caption + buttonCount++));
	        inner.addComponent(btns);

	        // Create a drag & drop horizontal split panel
	        final DDHorizontalSplitPanel panel = new DDHorizontalSplitPanel();
	        panel.setSizeFull();
	        
	        inner.addComponent(panel);
	        inner.setExpandRatio(panel, 1);

	        // Enable dragging
	        panel.setDragMode(LayoutDragMode.CLONE);

	        // Enable dropping
	        panel.setDropHandler(new DefaultHorizontalSplitPanelDropHandler());
	        
	        //end-source
	        return root;
	}

	@Override
	public String getCaption() {		
		return "Horizontal split panel";
	}
}
