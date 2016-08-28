package be.beme.schn.vaadin.dd;

import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

/**
 * Client side criterion for determining axis or indexes
 * Event fired when an item is dropped from a cell to another
 * in a {@link SwapDDGridLayout}
 * @author  Dorian Messina.
 */
public class SwapGridLayoutDropEvent extends Component.Event {

    private int[] coordinates;



    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the GridLayout
     * @param coordinates .
     */
    public SwapGridLayoutDropEvent(GridLayout source, int[] coordinates) {
        super(source);
        this.coordinates=coordinates;
    }

    /**
     * @return the abscissa/column to where the item is dropped
     * **/
    public int getXdest() {return coordinates[0];}
    /**
     * @return the ordinate/row to where the item is dropped
     * **/
    public int getYdest() {return coordinates[1];}

    /**
     * @return index in the GridLayout where the component is dropped
     */
    public int getIndexdest() {return coordinates[2];}

    /**
     * @return the abscissa/column from where the item was dragged
     * **/
    public int getXsrc() {return coordinates[3];}
    /**
     * @return the ordinate/row from where the item was dragged
     * **/
    public int getYsrc() {return coordinates[4];}

    /**
     * @return index int the GridLayout from where the item was dragged
     * **/
    public int getIndexsrc() {return coordinates[5];
    }
}
