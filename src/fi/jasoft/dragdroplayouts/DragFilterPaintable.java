/*
 * Copyright 2012 John Ahlroos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.jasoft.dragdroplayouts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;

import fi.jasoft.dragdroplayouts.client.ui.VDragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;

/**
 * A DragFilter wrapper to add common painting operations. Only used internally.
 * 
 * @author John Ahlroos
 * @since 0.6.3
 */
public class DragFilterPaintable {

    private final LayoutDragSource source;

    /**
     * Constructor
     * 
     * @param source
     *            The drag source
     * 
     */
    public DragFilterPaintable(LayoutDragSource source) {
        this.source = source;
    }

    /**
     * Paint the drag filter into the target
     * 
     * @param target
     *            The paint target
     * @throws PaintException
     *             Thrown is painting failed
     */
    public void paint(PaintTarget target) throws PaintException {
        if (source instanceof TabSheet) {
            paintWithIndexes(target);
        } else if (source instanceof Layout) {
            paintWithPids(target);
        } else {
            throw new UnsupportedOperationException(
                    "Cannot paint filter for paint target");
        }
    }

    private void paintWithPids(PaintTarget target) throws PaintException {
        Layout layout = (Layout) source;
        if (source.getDragFilter() != null) {
            // Get components with dragging disabled
            Map<Component, Boolean> dragmap = new HashMap<Component, Boolean>();
            Iterator<Component> iter = layout.getComponentIterator();
            while (iter.hasNext()) {
                Component c = iter.next();
                boolean draggable = source.getDragFilter().isDraggable(c);
                dragmap.put(c, draggable);
            }
            target.addAttribute(VDragFilter.DRAGMAP_ATTRIBUTE, dragmap);
        }
    }

    private void paintWithIndexes(PaintTarget target) throws PaintException {
        TabSheet tabsheet = (TabSheet) source;
        if (source.getDragFilter() != null) {
            // Get components with dragging disabled
            Map<Integer, Boolean> dragmap = new HashMap<Integer, Boolean>();
            Iterator<Component> iter = tabsheet.getComponentIterator();
            while (iter.hasNext()) {
                Component c = iter.next();
                if (tabsheet.getTab(c).isVisible()) {
                    boolean draggable = source.getDragFilter().isDraggable(c);
                    int index = tabsheet.getTabPosition(tabsheet.getTab(c));
                    dragmap.put(index, draggable);
                }
            }
            target.addAttribute(VDragFilter.DRAGMAP_ATTRIBUTE, dragmap);
        }
    }
}
