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

    private float cellLeftRightDropRatio = DEFAULT_HORIZONTAL_RATIO;

    private float cellTopBottomDropRatio = DEFAULT_VERTICAL_RATIO;
    
    public DDLayoutState dd = new DDLayoutState();

    public float getCellLeftRightDropRatio() {
        return cellLeftRightDropRatio;
    }

    @DelegateToWidget
    public void setCellLeftRightDropRatio(float cellLeftRightDropRatio) {
        this.cellLeftRightDropRatio = cellLeftRightDropRatio;
    }

    public float getCellTopBottomDropRatio() {
        return cellTopBottomDropRatio;
    }

    @DelegateToWidget
    public void setCellTopBottomDropRatio(float cellTopBottomDropRatio) {
        this.cellTopBottomDropRatio = cellTopBottomDropRatio;
    }

	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}
}
