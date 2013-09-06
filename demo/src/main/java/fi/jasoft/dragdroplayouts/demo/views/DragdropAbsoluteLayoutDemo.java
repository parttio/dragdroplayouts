package fi.jasoft.dragdroplayouts.demo.views;

import java.util.Arrays;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;

public class DragdropAbsoluteLayoutDemo extends DemoView{

	public static final String NAME = "dd-absolute-layout";

	public DragdropAbsoluteLayoutDemo(Navigator navigator) {
		super(navigator);	
	}
	
    @Override   
    public Component getLayout() {
    	 //start-source
    	 final DDAbsoluteLayout layout = new DDAbsoluteLayout();
         layout.setSizeFull();      
         
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
                 Notification.show("Click!");
             }
         });
         layout.addComponent(btn, "left:50px; top:50px");
         Link link = new Link("A link to Vaadin", new ExternalResource(
                 "http://www.vaadin.com"));
         layout.addComponent(link, "left:200px; top:100px");

       //end-source
    	return layout;
    }

	@Override
	public String getCaption() {		
		return "Absolute layout";
	}    
}
