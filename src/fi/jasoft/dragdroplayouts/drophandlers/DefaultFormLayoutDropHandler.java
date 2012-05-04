package fi.jasoft.dragdroplayouts.drophandlers;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Or;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

import fi.jasoft.dragdroplayouts.DDFormLayout;
import fi.jasoft.dragdroplayouts.DDFormLayout.FormLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.events.VerticalLocationIs;

public class DefaultFormLayoutDropHandler extends
        AbstractDefaultLayoutDropHandler {

    private Alignment dropAlignment;

    /**
     * Constructor
     * 
     * @param dropCellAlignment
     *            The cell alignment of the component after it has been dropped
     */
    public DefaultFormLayoutDropHandler() {

    }

    /**
     * Constructor
     * 
     * @param dropCellAlignment
     *            The cell alignment of the component after it has been dropped
     */
    public DefaultFormLayoutDropHandler(Alignment dropCellAlignment) {
        this.dropAlignment = dropCellAlignment;
    }

    @Override
    protected void handleComponentReordering(DragAndDropEvent event) {
        LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                .getTransferable();
        FormLayoutTargetDetails details = (FormLayoutTargetDetails) event
                .getTargetDetails();
        DDFormLayout layout = (DDFormLayout) details.getTarget();

        Component comp = transferable.getComponent();
        int idx = details.getOverIndex();
        int oldIdx = layout.getComponentIndex(comp);

        // Detach
        layout.removeComponent(comp);
        if (oldIdx < idx) {
            idx--;
        }

        // Add component
        if (idx >= 0) {
            layout.addComponent(comp, idx);
        } else {
            layout.addComponent(comp);
        }

        // Add component alignment if given
        if (dropAlignment != null) {
            layout.setComponentAlignment(comp, dropAlignment);
        }
    }

    @Override
    protected void handleDropFromLayout(DragAndDropEvent event) {
        System.out.println("Component from external layout drop");
    }

    @Override
    public AcceptCriterion getAcceptCriterion() {
        /**
         * By default the form layout accepts drops between components if
         */
        return new Or(VerticalLocationIs.BOTTOM, VerticalLocationIs.TOP);
    }

}
