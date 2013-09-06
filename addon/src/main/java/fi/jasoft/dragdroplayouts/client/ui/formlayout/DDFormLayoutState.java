package fi.jasoft.dragdroplayouts.client.ui.formlayout;

import java.util.List;

import com.vaadin.shared.Connector;
import com.vaadin.shared.annotations.DelegateToWidget;
import com.vaadin.shared.ui.orderedlayout.AbstractOrderedLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDFormLayoutState extends AbstractOrderedLayoutState implements
        DragAndDropAwareState {

    public static final float DEFAULT_VERTICAL_DROP_RATIO = 0.3333f;

    private float cellTopBottomDropRatio = DEFAULT_VERTICAL_DROP_RATIO;
    
    public DDLayoutState dd = new DDLayoutState();

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
