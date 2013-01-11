package fi.jasoft.dragdroplayouts.demo;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;

public class DragdropIframeDragging extends CustomComponent {

    public DragdropIframeDragging(){
        setCaption("Dragging iframe based components");
        setSizeFull();

        HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();
        setCompositionRoot(root);

        // Add a layout where shimming is turned on
        root.addComponent(createShimmedLayout());

        // Add a layout where shimming is turned off
        root.addComponent(createUnShimmedLayout());

    }

    private DDAbsoluteLayout createShimmedLayout() {
        DDAbsoluteLayout layout = new DDAbsoluteLayout();
        layout.setSizeFull();
        layout.setDragMode(LayoutDragMode.CLONE);
        layout.setDropHandler(new DefaultAbsoluteLayoutDropHandler());

        /*
         * Enable shimming of iframe based components.
         * 
         * Iframe components will be draggable, but the page behind the iframe
         * cannot be accessed
         */
        layout.setShim(true);

        layout.addComponent(new Label(
                "Adding iframe components to an layout where shimming is"
                        + " turned on makes dragging possible but accessing the iframed component not possible. "
                        + "For instance the tweet button below can be dragged around but not clicked"));

        addComponentsToLayout(layout, null);

        return layout;
    }

    private DDAbsoluteLayout createUnShimmedLayout() {
        DDAbsoluteLayout layout = new DDAbsoluteLayout();
        layout.setSizeFull();
        layout.setDragMode(LayoutDragMode.CLONE);
        layout.setDropHandler(new DefaultAbsoluteLayoutDropHandler());

        /*
         * Disable shimming of iframe based components.
         * 
         * Iframe components cannot be dragged but can be accessed through the
         * layout
         */
        layout.setShim(false);

        layout.addComponent(new Label(
                "Adding iframe components to an layout where shimming is"
                        + " turned off makes dragging those components impossible. "
                        + "For instance the tweet button below can be normally used but not dragged"));

        addComponentsToLayout(layout, null);

        return layout;
    }

    private void addComponentsToLayout(DDAbsoluteLayout layout, String caption) {

        BrowserFrame frame = new BrowserFrame(caption, new ExternalResource(
                "https://platform.twitter.com/widgets/tweet_button.html"));
        frame.setWidth("300px");
        frame.setHeight("300px");

        layout.addComponent(frame, "top:100px;left:50px");

    }

}
