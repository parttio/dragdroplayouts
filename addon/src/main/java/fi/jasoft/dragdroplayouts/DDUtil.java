package fi.jasoft.dragdroplayouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.vaadin.shared.Connector;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;
import fi.jasoft.dragdroplayouts.interfaces.DragFilterSupport;
import fi.jasoft.dragdroplayouts.interfaces.DragImageProvider;
import fi.jasoft.dragdroplayouts.interfaces.DragImageReferenceSupport;

public class DDUtil {

    public static void onBeforeClientResponse(HasComponents layout,
	    DragAndDropAwareState state) {
	DDLayoutState dragAndDropState = state.getDragAndDropState();
	Iterator<Component> componentIterator = layout.iterator();	
	dragAndDropState.draggable = new ArrayList<Connector>();
	dragAndDropState.referenceImageComponents = new HashMap<Connector, Connector>();
	while (componentIterator.hasNext()) {
	    Component c = componentIterator.next();

	    if(layout instanceof DragFilterSupport && ((DragFilterSupport) layout).getDragFilter().isDraggable(c)){
		dragAndDropState.draggable.add(c);
	    }
	    
	    if (layout instanceof DragImageReferenceSupport) {
		DragImageProvider provider = ((DragImageReferenceSupport) layout)
			.getDragImageProvider();
		if (provider != null) {
		    Component dragImage = provider.getDragImage(c);
		    if (dragImage != null) {
			dragAndDropState.referenceImageComponents.put(c,
				dragImage);
		    }
		}
	    }
	}
    }
}
