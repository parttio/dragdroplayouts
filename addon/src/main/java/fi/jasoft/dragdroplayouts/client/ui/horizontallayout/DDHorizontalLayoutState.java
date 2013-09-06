package fi.jasoft.dragdroplayouts.client.ui.horizontallayout;

import com.vaadin.shared.annotations.DelegateToWidget;
import com.vaadin.shared.ui.orderedlayout.HorizontalLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDHorizontalLayoutState extends HorizontalLayoutState implements
        DragAndDropAwareState {

    public static final float DEFAULT_HORIZONTAL_DROP_RATIO = 0.2f;

    @DelegateToWidget
    public float cellLeftRightDropRatio = DEFAULT_HORIZONTAL_DROP_RATIO;
    
    public DDLayoutState dd = new DDLayoutState();

	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}
}
