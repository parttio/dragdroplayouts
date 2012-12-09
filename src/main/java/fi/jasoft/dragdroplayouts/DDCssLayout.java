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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.TargetDetailsImpl;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.shared.Connector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.dd.HorizontalDropLocation;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.LegacyComponent;

import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.csslayout.DDCssLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;
import fi.jasoft.dragdroplayouts.interfaces.ShimSupport;

/**
 * CssLayout with drag and drop support
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.7.0
 * 
 */
@SuppressWarnings("serial")
public class DDCssLayout extends CssLayout implements LayoutDragSource,
        DropTarget, ShimSupport, LegacyComponent {

    // Drop handler which handles dd drop events
    private DropHandler dropHandler;

    // A filter for dragging components.
    private DragFilter dragFilter = DragFilter.ALL;

    /**
     * Target details for dropping on a absolute layout.
     */
    public class CssLayoutTargetDetails extends TargetDetailsImpl {

        private int index = -1;

        private Component over;

        /**
         * Constructor
         * 
         * @param rawDropData
         *            The drop data
         */
        protected CssLayoutTargetDetails(Map<String, Object> rawDropData) {
            super(rawDropData, DDCssLayout.this);

            // Get over which component (if any) the drop was made and the
            // index of it
            if (getData(Constants.DROP_DETAIL_TO) != null) {
                index = Integer.valueOf(getData(Constants.DROP_DETAIL_TO)
                        .toString());
                if (index >= 0 && index < components.size()) {
                    over = components.get(index);
                }
            } else {
                index = components.size();
            }

            // Was the drop over no specific cell
            if (over == null) {
                over = DDCssLayout.this;
            }
        }

        /**
         * Some details about the mouse event
         * 
         * @return details about the actual event that caused the event details.
         *         Practically mouse move or mouse up.
         */
        public MouseEventDetails getMouseEvent() {
            return MouseEventDetails.deSerialize(getData(
                    Constants.DROP_DETAIL_MOUSE_EVENT).toString());
        }

        /**
         * Get the horizontal position of the dropped component within the
         * underlying cell.
         * 
         * @return The drop location
         */
        public HorizontalDropLocation getHorizontalDropLocation() {
            return HorizontalDropLocation
                    .valueOf((String) getData(Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION));
        }

        /**
         * Get the horizontal position of the dropped component within the
         * underlying cell.
         * 
         * @return The drop location
         */
        public VerticalDropLocation getVerticalDropLocation() {
            return VerticalDropLocation
                    .valueOf((String) getData(Constants.DROP_DETAIL_VERTICAL_DROP_LOCATION));
        }

        /**
         * The index over which the drop was made. If the drop was not made over
         * any component then it returns -1.
         * 
         * @return The index of the component or -1 if over no component.
         */
        public int getOverIndex() {
            return index;
        }

        /**
         * The component over which the drop was made.
         * 
         * @return Null if the drop was not over a component, else the component
         */
        public Component getOverComponent() {
            return over;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Transferable getTransferable(Map<String, Object> rawVariables) {
        return new LayoutBoundTransferable(this, rawVariables);
    }

    /**
     * {@inheritDoc}
     */
    public void setShim(boolean shim) {
        getState().setIframeShims(shim);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isShimmed() {
        return getState().isIframeShims();
    }

    /**
     * gets the drop handler which handles component drops on the layout
     */
    public DropHandler getDropHandler() {
        return dropHandler;
    }

    /**
     * Sets the drop handler which handles component drops on the layout
     * 
     * @param dropHandler
     *            The drop handler to set
     */
    public void setDropHandler(DropHandler dropHandler) {
        if (this.dropHandler != dropHandler) {
            this.dropHandler = dropHandler;
            requestRepaint();
        }
    }

    /**
     * {@inheritDoc}
     */
    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return new CssLayoutTargetDetails(clientVariables);
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
        return getState().getDragMode();
    }

    /**
     * {@inheritDoc}
     */
    public void setDragMode(LayoutDragMode mode) {
        getState().setDragMode(mode);
    }

    /**
     * {@inheritDoc}
     */
    public DragFilter getDragFilter() {
        return dragFilter;
    }

    /**
     * {@inheritDoc}
     */
    public void setDragFilter(DragFilter dragFilter) {
        this.dragFilter = dragFilter;
    }

    /**
     * {@inheritDoc}
     * 
     */
    public void paintContent(PaintTarget target) throws PaintException {

        // Paint the drop handler criterions
        if (dropHandler != null && isEnabled()) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        // Adds the drag mode (the default is none)
        if (isEnabled()) {
            target.addAttribute(Constants.DRAGMODE_ATTRIBUTE, getState()
                    .getDragMode().ordinal());
        } else {
            target.addAttribute(Constants.DRAGMODE_ATTRIBUTE,
                    LayoutDragMode.NONE.ordinal());
        }

        // Should shims be used
        target.addAttribute(IframeCoverUtility.SHIM_ATTRIBUTE, getState()
                .isIframeShims());
    }

    @Override
    public DDCssLayoutState getState() {
        return (DDCssLayoutState) super.getState();
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        super.beforeClientResponse(initial);

        // Update draggable filter
        Iterator<Component> componentIterator = getComponentIterator();
        getState().draggable = new ArrayList<Connector>();
        while (componentIterator.hasNext()) {
            Component c = componentIterator.next();
            if (dragFilter.isDraggable(c)) {
                getState().draggable.add(c);
            }
        }
    }

    @Override
    public void changeVariables(Object source, Map<String, Object> variables) {
        // TODO Auto-generated method stub

    }
}
