package fi.jasoft.dragdroplayouts.client.ui.absolutelayout;

import com.vaadin.shared.ui.absolutelayout.AbsoluteLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDAbsoluteLayoutState extends AbsoluteLayoutState implements DragAndDropAwareState{
	
	public DDLayoutState dd = new DDLayoutState();

	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}	
}
