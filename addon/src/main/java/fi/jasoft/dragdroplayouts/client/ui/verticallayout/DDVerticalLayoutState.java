package fi.jasoft.dragdroplayouts.client.ui.verticallayout;

import com.vaadin.shared.annotations.DelegateToWidget;
import com.vaadin.shared.ui.orderedlayout.VerticalLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDVerticalLayoutState extends VerticalLayoutState implements
        DragAndDropAwareState {

    public static final float DEFAULT_VERTICAL_DROP_RATIO = 0.2f;

    @DelegateToWidget
    public float cellTopBottomDropRatio = DEFAULT_VERTICAL_DROP_RATIO;
    
    public DDLayoutState dd = new DDLayoutState();

	@Override
	public DDLayoutState getDragAndDropState() {	
		return dd;
	}
}
