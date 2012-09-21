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

import java.util.Map;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.server.PaintException;
import com.vaadin.server.PaintTarget;
import com.vaadin.ui.AbsoluteLayout;

import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.absolutelayout.DDAbsoluteLayoutState;
import fi.jasoft.dragdroplayouts.details.AbsoluteLayoutTargetDetails;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;
import fi.jasoft.dragdroplayouts.interfaces.DragFilter;
import fi.jasoft.dragdroplayouts.interfaces.LayoutDragSource;
import fi.jasoft.dragdroplayouts.interfaces.ShimSupport;

/**
 * Absolute layout with drag and drop support
 * 
 * @author John Ahlroos / www.jasoft.fi
 */
@SuppressWarnings("serial")
public class DDAbsoluteLayout extends AbsoluteLayout implements
        LayoutDragSource, DropTarget, ShimSupport {

    // Drop handler which handles dd drop events
    private DropHandler dropHandler;

    /**
     * Creates an AbsoluteLayout with full size.
     */
    public DDAbsoluteLayout() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public void paintContent(PaintTarget target) throws PaintException {

        // Paint the drop handler criterions
        if (dropHandler != null && isEnabled()) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        // Paint the dragfilter into the paint target
        new DragFilterPaintable(this).paint(target);
    }

    /**
     * Get the drophandler which handles component drops on the layout
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
        return new AbsoluteLayoutTargetDetails(this, clientVariables);
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
     * {@inheritDoc}
     */
    public DragFilter getDragFilter() {
        return getState().getDragFilter();
    }

    /**
     * {@inheritDoc}
     */
    public void setDragFilter(DragFilter dragFilter) {
        getState().setDragFilter(dragFilter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDAbsoluteLayoutState getState() {
        return (DDAbsoluteLayoutState) super.getState();
    }
}
