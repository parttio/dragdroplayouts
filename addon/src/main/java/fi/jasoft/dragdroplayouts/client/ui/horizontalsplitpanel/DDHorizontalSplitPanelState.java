package fi.jasoft.dragdroplayouts.client.ui.horizontalsplitpanel;

import com.vaadin.shared.ui.splitpanel.HorizontalSplitPanelState;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDHorizontalSplitPanelState extends HorizontalSplitPanelState
        implements DragAndDropAwareState {

	public DDLayoutState dd = new DDLayoutState();

	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}
}
