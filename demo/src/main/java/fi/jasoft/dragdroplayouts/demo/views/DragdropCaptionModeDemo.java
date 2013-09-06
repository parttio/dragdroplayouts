package fi.jasoft.dragdroplayouts.demo.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import de.svenjacobs.loremipsum.LoremIpsum;
import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;

public class DragdropCaptionModeDemo extends DemoView {

	public static final String NAME = "dd-caption-mode";
	
	public DragdropCaptionModeDemo(Navigator navigator) {
		super(navigator);		
	}
	
	@Override
	public Component getLayout() {
		//start-source		
		// Create layout
        DDAbsoluteLayout layout = new DDAbsoluteLayout();
      
        // Enable dragging components by their caption
        layout.setDragMode(LayoutDragMode.CAPTION);

        // Enable dropping components
        layout.setDropHandler(new DefaultAbsoluteLayoutDropHandler());

        // Add some content to the layout
        layout.addComponent(new Label(
                "This layout uses the LayoutDragMode.CAPTION "
                        + "drag mode for dragging the components. This mode is useful "
                        + "when you only want users to drag items by their captions. The Panels below are only draggable by their captions (<< Move >>).",
                Label.CONTENT_XHTML));

        Panel chapter1 = new Panel("<< Move >>");
        chapter1.setWidth("300px");
        Label chapter1Content = new Label(new LoremIpsum().getParagraphs(1),
                Label.CONTENT_TEXT);
        chapter1Content.setCaption("===== Chapter 1 - The beginning ======");
        chapter1.setContent(chapter1Content);
        layout.addComponent(chapter1, "top:50px;left:10px");

        Panel chapter2 = new Panel("<< Move >>");
        chapter2.setWidth("300px");
        Label chapter2Content = new Label(new LoremIpsum().getParagraphs(1),
                Label.CONTENT_TEXT);
        chapter2Content.setCaption("===== Chapter 2 - The finale ======");
        chapter2.setContent(chapter2Content);
        layout.addComponent(chapter2, "top:50px; left:320px");
        
        //end-source
        return layout;
	}

	@Override
	public String getCaption() {		
		return "Caption mode";
	}
}
