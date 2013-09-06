package fi.jasoft.dragdroplayouts.client.ui.csslayout;

import java.util.List;

import com.vaadin.shared.Connector;
import com.vaadin.shared.annotations.DelegateToWidget;
import com.vaadin.shared.ui.csslayout.CssLayoutState;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class DDCssLayoutState extends CssLayoutState implements DragAndDropAwareState {

    public static final float DEFAULT_HORIZONTAL_DROP_RATIO = 0.2f;

    public static final float DEFAULT_VERTICAL_DROP_RATIO = 0.2f;

    private float horizontalDropRatio = DEFAULT_HORIZONTAL_DROP_RATIO;

    private float verticalDropRatio = DEFAULT_VERTICAL_DROP_RATIO;
    
    public DDLayoutState dd = new DDLayoutState();

    public float getHorizontalDropRatio() {
        return horizontalDropRatio;
    }

    @DelegateToWidget
    public void setHorizontalDropRatio(float horizontalDropRatio) {
        this.horizontalDropRatio = horizontalDropRatio;
    }

    public float getVerticalDropRatio() {
        return verticalDropRatio;
    }

    @DelegateToWidget
    public void setVerticalDropRatio(float verticalDropRatio) {
        this.verticalDropRatio = verticalDropRatio;
    }

	@Override
	public DDLayoutState getDragAndDropState() {		
		return dd;
	}
}
