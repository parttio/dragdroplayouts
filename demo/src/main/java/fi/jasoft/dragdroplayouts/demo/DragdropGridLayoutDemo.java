package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultGridLayoutDropHandler;

@SuppressWarnings("serial")
public class DragdropGridLayoutDemo extends CustomComponent {
	
	private static final int ROWS = 4;
	private static final int COLUMNS = 4;
	
    public DragdropGridLayoutDemo() {
        setCaption("Grid layout");
        setSizeFull();

        VerticalLayout outer = new VerticalLayout();
        outer.setSizeFull();
        Label lbl = new Label(
                "This is a grid layout with 16 cells, try dragging the buttons into an empty cell");
        outer.addComponent(lbl);
        setCompositionRoot(outer);

        // Create a drag and droppable grid layout
        final DDGridLayout layout = new DDGridLayout(COLUMNS, ROWS);
        layout.setWidth("400px");
        layout.setHeight("100%");
        
        // Only allow dropping in the center of the grid layout cell
        layout.setComponentHorizontalDropRatio(0);
        layout.setComponentVerticalDropRatio(0);

        outer.addComponent(layout);
        outer.setExpandRatio(layout, 1);

        // Enable dragging components
        layout.setDragMode(LayoutDragMode.CLONE);

        // Enable dropping components
        layout.setDropHandler(new DefaultGridLayoutDropHandler());
        
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (row == 0 || row == ROWS-1 || col == 0 || col == COLUMNS-1) {
                    Button btn = new Button("Button");
                    layout.addComponent(btn, col, row);
                    layout.setComponentAlignment(btn, Alignment.MIDDLE_CENTER);
                }
            }
        }
    }
}
