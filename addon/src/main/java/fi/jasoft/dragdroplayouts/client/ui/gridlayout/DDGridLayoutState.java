package fi.jasoft.dragdroplayouts.client.ui.gridlayout;

import java.util.List;

import com.vaadin.shared.Connector;
import com.vaadin.shared.annotations.DelegateToWidget;
import com.vaadin.shared.ui.gridlayout.GridLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDGridLayoutState extends GridLayoutState implements DragAndDropAwareState {

    public static final float DEFAULT_HORIZONTAL_RATIO = 0.2f;
    public static final float DEFAULT_VERTICAL_RATIO = 0.2f;

    @DelegateToWidget
    public float cellLeftRightDropRatio = DEFAULT_HORIZONTAL_RATIO;

    @DelegateToWidget
    public float cellTopBottomDropRatio = DEFAULT_VERTICAL_RATIO;
    
    public DDLayoutState dd = new DDLayoutState();

	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}
}
