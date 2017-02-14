package fi.jasoft.dragdroplayouts.client;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.Util;
import com.vaadin.client.VCaption;
import com.vaadin.client.ui.VAccordion;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;

public class VGrabFilter {
    private final DDLayoutState state;

    public VGrabFilter(DDLayoutState state) {
        this.state = state;
    }

    public boolean isGrabbable(Widget root, Widget widget) {
        if (state.nonGrabbable != null) {
            return findConnectorFor(root, widget);
        }
        return true;
    }

    private boolean findConnectorFor(Widget root, Widget widget) {
        ComponentConnector connector;
        if (!isCaptionForAccordion(widget)) {
            connector = Util.findConnectorFor(widget);
        } else {
            connector = findConnectorForAccordionCaption(widget);
        }

        if (connector != null && state.nonGrabbable.contains(connector)) {
            return false;
        }

        Widget parent = widget.getParent();
        if (parent != null && parent == root) {
            return true;
        }

        return findConnectorFor(root, parent);
    }

    private ComponentConnector findConnectorForAccordionCaption(Widget widget) {
        VAccordion.StackItem parent = (VAccordion.StackItem) widget.getParent();
        return Util.findConnectorFor(parent.getChildWidget());
    }

    private boolean isCaptionForAccordion(Widget widget) {
        if (widget == null) {
            return false;
        }
        if (!(widget instanceof VCaption)) {
            return false;
        }
        Widget parent = widget.getParent();
        return parent instanceof VAccordion.StackItem;
    }
}
