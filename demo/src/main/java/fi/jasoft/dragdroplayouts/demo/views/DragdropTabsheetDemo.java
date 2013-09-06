package fi.jasoft.dragdroplayouts.demo.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.DDTabSheet;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultTabSheetDropHandler;

public class DragdropTabsheetDemo extends DemoView {

	public static final String NAME = "dd-tabsheet";
	
	public DragdropTabsheetDemo(Navigator navigator) {
		super(navigator);		
	}
	
	@Override
	public Component getLayout() {
		//start-source
		VerticalLayout v = new VerticalLayout();
        v.setSizeFull();
        v.setSpacing(true);
                
        Label lb = new Label(
                "This demo shows you how you can drag components into a tabsheet and reorder the tabs. "
                        + "Try dragging some Buttons into the tab area to add them as tabs. You can then "
                        + "reorder the tabs by dragging on them");
        v.addComponent(lb);

        // Add some buttons to a vertical layout with dragging enabled
        final DDHorizontalLayout btns = new DDHorizontalLayout();
        btns.setSpacing(true);
        btns.setDragMode(LayoutDragMode.CLONE);
        btns.addComponent(new Button("One Button"));
        btns.addComponent(new Button("Second Button"));
        btns.addComponent(new Button("Third Button"));
        btns.addComponent(new Button("Fourth Button"));
        btns.addComponent(new Button("Fifth Button"));
        v.addComponent(btns);

        // Create a tabsheet
        final DDTabSheet tabSheet = new DDTabSheet();
        tabSheet.setSizeFull();

        // Add a tab
        VerticalLayout layout = new VerticalLayout();
        layout.setCaption("Example");
        layout.addComponent(new Label(
                "This is an example tab already in the tabsheet."));
        tabSheet.addComponent(layout);

        // Enable dragging
        tabSheet.setDragMode(LayoutDragMode.CLONE);

        // Enable dropping
        tabSheet.setDropHandler(new DefaultTabSheetDropHandler());

        v.addComponent(tabSheet);
        v.setExpandRatio(tabSheet, 1);
        //end-source
        return v;
	}

	@Override
	public String getCaption() {	
		return "Tabsheet";
	}
}
