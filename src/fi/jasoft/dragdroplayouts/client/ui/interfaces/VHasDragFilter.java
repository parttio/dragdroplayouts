package fi.jasoft.dragdroplayouts.client.ui.interfaces;

import fi.jasoft.dragdroplayouts.client.ui.VDragFilter;

/**
 * Layouts which supports drag filters should implement this
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.7.2
 */
public interface VHasDragFilter {

    /**
     * Returns the drag filter used by the layout
     * 
     * @return
     */
    VDragFilter getDragFilter();
}
