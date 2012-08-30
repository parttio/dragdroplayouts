package fi.jasoft.dragdroplayouts.details;

import java.util.Iterator;
import java.util.Map;

import com.vaadin.event.dd.TargetDetailsImpl;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Component;

import fi.jasoft.dragdroplayouts.DDAccordion;

public class AccordionTargetDetails extends TargetDetailsImpl {

    private Component over;

    private int index = -1;

    public AccordionTargetDetails(DDAccordion accordion,
            Map<String, Object> rawDropData) {
        super(rawDropData, accordion);

        // Get over which component (if any) the drop was made and the
        // index of it
        Object to = rawDropData.get("to");
        if (to != null) {
            index = Integer.valueOf(to.toString());

            if (index < accordion.getComponentCount()) {
                Iterator<Component> iter = accordion.getComponentIterator();
                int counter = 0;
                while (iter.hasNext()) {
                    over = iter.next();
                    if (counter == index) {
                        break;
                    }
                    counter++;
                }
            } else {
                over = accordion;
            }
        } else {
            over = accordion;
        }
    }

    /**
     * The component over which the drop was made.
     * 
     * @return Null if the drop was not over a component, else the component
     */
    public Component getOverComponent() {
        return over;
    }

    /**
     * The index over which the drop was made. If the drop was not made over any
     * component then it returns -1.
     * 
     * @return The index of the component or -1 if over no component.
     */
    public int getOverIndex() {
        return index;
    }

    /**
     * Some details about the mouse event
     * 
     * @return details about the actual event that caused the event details.
     *         Practically mouse move or mouse up.
     */
    public MouseEventDetails getMouseEvent() {
        return MouseEventDetails.deSerialize((String) getData("mouseEvent"));
    }

    /**
     * Get the horizontal position of the dropped component within the
     * underlying cell.
     * 
     * @return The drop location
     */
    public VerticalDropLocation getDropLocation() {
        if (getData("vdetail") != null) {
            return VerticalDropLocation.valueOf((String) getData("vdetail"));
        } else {
            return null;
        }
    }
}