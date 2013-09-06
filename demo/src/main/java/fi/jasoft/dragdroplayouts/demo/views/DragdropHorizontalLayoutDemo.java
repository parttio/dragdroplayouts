package fi.jasoft.dragdroplayouts.demo.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultHorizontalLayoutDropHandler;

@SuppressWarnings("serial")
public class DragdropHorizontalLayoutDemo extends DemoView {

	public static final String NAME = "dd-horizontal-layout";
	
    private static final float EQUAL_HORIZONTAL_RATIO = 0.3f;

	public DragdropHorizontalLayoutDemo(Navigator navigator) {
		super(navigator);		
	}
    
	@Override
	public Component getLayout() {
		//start-source
		final DDHorizontalLayout layout = new DDHorizontalLayout();      
        layout.setComponentHorizontalDropRatio(EQUAL_HORIZONTAL_RATIO);
        layout.setDragMode(LayoutDragMode.CLONE);
        layout.setDropHandler(new DefaultHorizontalLayoutDropHandler());

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
        //end-source
        return layout;
	}

	@Override
	public String getCaption() {	
		return "Horizontal layout";
	}
}
