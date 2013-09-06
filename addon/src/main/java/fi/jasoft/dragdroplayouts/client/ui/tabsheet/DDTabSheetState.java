package fi.jasoft.dragdroplayouts.client.ui.tabsheet;

import com.vaadin.shared.ui.tabsheet.TabsheetState;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDTabSheetState extends TabsheetState implements DragAndDropAwareState {

    public static final float DEFAULT_HORIZONTAL_DROP_RATIO = 0.2f;

    public float tabLeftRightDropRatio = DEFAULT_HORIZONTAL_DROP_RATIO;

	public DDLayoutState dd = new DDLayoutState();
    
	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}

}
