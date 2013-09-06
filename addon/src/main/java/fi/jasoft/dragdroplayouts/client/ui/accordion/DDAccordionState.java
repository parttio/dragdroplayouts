package fi.jasoft.dragdroplayouts.client.ui.accordion;

import com.vaadin.shared.AbstractComponentState;
import com.vaadin.shared.annotations.DelegateToWidget;

import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDAccordionState extends AbstractComponentState implements
        DragAndDropAwareState {

    public static final float DEFAULT_VERTICAL_RATIO = 0.2f;

    private float tabTopBottomDropRatio = DEFAULT_VERTICAL_RATIO;
    
    public DDLayoutState dd = new DDLayoutState();

    public float getTabTopBottomDropRatio() {
        return tabTopBottomDropRatio;
    }

    @DelegateToWidget
    public void setTabTopBottomDropRatio(float tabTopBottomDropRatio) {
        this.tabTopBottomDropRatio = tabTopBottomDropRatio;
    }

	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}

}
