package fi.jasoft.dragdroplayouts.client.ui.verticalsplitpanel;

import com.vaadin.shared.ui.splitpanel.VerticalSplitPanelState;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDVerticalSplitPanelState extends VerticalSplitPanelState
        implements DragAndDropAwareState {

	public DDLayoutState dd = new DDLayoutState();
	
	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}
}
