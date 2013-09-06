/*
 * Copyright 2013 John Ahlroos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.jasoft.dragdroplayouts.demo.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.demo.DemoView;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultAbsoluteLayoutDropHandler;

public class DragdropIframeDragging extends DemoView {

	public static final String NAME = "dd-iframes";
	
	public DragdropIframeDragging(Navigator navigator) {
		super(navigator);		
	}

    private DDAbsoluteLayout createShimmedLayout() {
        DDAbsoluteLayout layout = new DDAbsoluteLayout();
        layout.setStyleName("shimmed-layout");
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
        layout.setStyleName("unshimmed-layout");
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

	@Override
	public Component getLayout() {
		HorizontalLayout root = new HorizontalLayout();
        root.setSizeFull();
        root.setSpacing(true);
        setCompositionRoot(root);

        // Add a layout where shimming is turned on
        root.addComponent(createShimmedLayout());

        // Add a layout where shimming is turned off
        root.addComponent(createUnShimmedLayout());
        
        return root;
	}

	@Override
	public String getCaption() {		
		return "Dragging iframes";
	}

}
