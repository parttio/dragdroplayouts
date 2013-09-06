package fi.jasoft.dragdroplayouts.client.ui.tabsheet;

import java.util.List;

import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.tabsheet.TabsheetState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDTabSheetState extends TabsheetState implements DragAndDropAwareState {

    public static final float DEFAULT_HORIZONTAL_DROP_RATIO = 0.2f;

    private float tabLeftRightDropRatio = DEFAULT_HORIZONTAL_DROP_RATIO;

	public DDLayoutState dd = new DDLayoutState();
    
    public float getTabLeftRightDropRatio() {
        return tabLeftRightDropRatio;
    }

    public void setTabLeftRightDropRatio(float tabLeftRightDropRatio) {
        this.tabLeftRightDropRatio = tabLeftRightDropRatio;
    }

	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}

}
