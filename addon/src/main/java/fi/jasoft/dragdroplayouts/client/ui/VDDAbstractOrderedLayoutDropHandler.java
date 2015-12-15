package fi.jasoft.dragdroplayouts.client.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.WidgetUtil;
import com.vaadin.client.ui.orderedlayout.Slot;
import com.vaadin.client.ui.orderedlayout.VAbstractOrderedLayout;

public abstract class VDDAbstractOrderedLayoutDropHandler<W extends VAbstractOrderedLayout>
        extends VDDAbstractDropHandler<W> {

    public VDDAbstractOrderedLayoutDropHandler(ComponentConnector connector) {
        super(connector);
    }

    protected abstract Slot getSlot(Element e, NativeEvent event);

    protected Slot findSlotAtPosition(int clientX, int clientY,
            NativeEvent event) {
        com.google.gwt.dom.client.Element elementUnderMouse = WidgetUtil
                .getElementFromPoint(clientX, clientY);
        if (getLayout().getElement() != elementUnderMouse) {
            return getSlot(DOM.asOld(elementUnderMouse), event);
        }
        return null;
    }

    protected Slot findSlotHorizontally(int spacerSize, NativeEvent event) {
        int counter = 0;
        Slot slotLeft, slotRight;
        int clientX = event.getClientX();
        int clientY = event.getClientY();
        while (counter < spacerSize) {
            counter++;
            slotRight = findSlotAtPosition(clientX + counter, clientY, event);
            slotLeft = findSlotAtPosition(clientX - counter, clientY, event);
            if (slotRight != null) {
                return slotRight;
            }
            if (slotLeft != null) {
                return slotLeft;
            }
        }
        return null;
    }

    protected Slot findSlotVertically(int spacerSize, NativeEvent event) {
        int counter = 0;
        Slot slotTop, slotBottom;
        int clientX = event.getClientX();
        int clientY = event.getClientY();
        while (counter < spacerSize) {
            counter++;
            slotBottom = findSlotAtPosition(clientX, clientY + counter, event);
            slotTop = findSlotAtPosition(clientX, clientY - counter, event);
            if (slotBottom != null) {
                return slotBottom;
            }
            if (slotTop != null) {
                return slotTop;
            }
        }
        return null;
    }
}
