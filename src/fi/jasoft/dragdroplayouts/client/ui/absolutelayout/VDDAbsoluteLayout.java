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
package fi.jasoft.dragdroplayouts.client.ui.absolutelayout;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.Util;
import com.vaadin.client.ui.VAbsoluteLayout;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.client.ui.dd.VHasDropHandler;
import com.vaadin.shared.MouseEventDetails;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler;
import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

/**
 * Client side implementation for {@link DDAbsoluteLayout}
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.4.0
 */
public class VDDAbsoluteLayout extends VAbsoluteLayout implements VHasDragMode,
        VHasDropHandler, DragStartListener, VHasDragFilter {

    public static final String CLASSNAME = "v-ddabsolutelayout";

    private VDDAbsoluteLayoutDropHandler dropHandler;

    private final VLayoutDragDropMouseHandler ddHandler = new VLayoutDragDropMouseHandler(
            this, LayoutDragMode.NONE);

    private final VDragFilter dragFilter = new VDragFilter();

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();

    public VDDAbsoluteLayout() {
        super();
        ddHandler.addDragStartListener(this);
        addStyleName(CLASSNAME);
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        ddHandler.updateDragMode(LayoutDragMode.NONE);
        iframeCoverUtility.setIframeCoversEnabled(false, getElement(),
                LayoutDragMode.NONE);
    }

    // FIXME
    // @Override
    // public boolean requestLayout(Set<Paintable> children) {
    // iframeCoverUtility.setIframeCoversEnabled(
    // iframeCoverUtility.isIframeCoversEnabled(), getElement(),
    // dragMode);
    // return super.requestLayout(children);
    // }

    /**
     * A hook for extended components to post process the the drop before it is
     * sent to the server. Useful if you don't want to override the whole drop
     * handler.
     */
    protected boolean postDropHook(VDragEvent drag) {
        // Extended classes can add content here...
        return true;
    }

    /**
     * A hook for extended components to post process the the enter event.
     * Useful if you don't want to override the whole drophandler.
     */
    protected void postEnterHook(VDragEvent drag) {
        // Extended classes can add content here...
    }

    /**
     * A hook for extended components to post process the the leave event.
     * Useful if you don't want to override the whole drophandler.
     */
    protected void postLeaveHook(VDragEvent drag) {
        // Extended classes can add content here...
    }

    /**
     * A hook for extended components to post process the the over event. Useful
     * if you don't want to override the whole drophandler.
     */
    protected void postOverHook(VDragEvent drag) {
        // Extended classes can add content here...
    }

    /**
     * Can be used to listen to drag start events, must return true for the drag
     * to commence. Return false to interrupt the drag:
     */
    public boolean dragStart(Widget widget, LayoutDragMode mode) {
        return ddHandler.getDragMode() != LayoutDragMode.NONE
                && dragFilter.isDraggable(widget);
    }

    /**
     * Returns the drop handler which handles the drop events
     */
    public VDDAbsoluteLayoutDropHandler getDropHandler() {
        return dropHandler;
    }

    public void setDropHandler(VDDAbsoluteLayoutDropHandler dropHandler) {
        this.dropHandler = dropHandler;
    }

    public VDragFilter getDragFilter() {
        return dragFilter;
    }

    IframeCoverUtility getIframeCoverUtility() {
        return iframeCoverUtility;
    }

    VLayoutDragDropMouseHandler getMouseHandler() {
        return ddHandler;
    }

    public LayoutDragMode getDragMode() {
        return ddHandler.getDragMode();
    }

    protected void updateDragDetails(VDragEvent drag) {
        // Get absolute coordinates
        int absoluteLeft = getAbsoluteLeft();
        int absoluteTop = getAbsoluteTop();

        drag.getDropDetails().put(Constants.DROP_DETAIL_ABSOLUTE_LEFT,
                absoluteLeft);
        drag.getDropDetails().put(Constants.DROP_DETAIL_ABSOLUTE_TOP,
                absoluteTop);

        // Get relative coordinates
        String offsetLeftStr = drag.getDragImage().getStyle().getMarginLeft();
        int offsetLeft = Integer.parseInt(offsetLeftStr.substring(0,
                offsetLeftStr.length() - 2));
        int relativeLeft = Util.getTouchOrMouseClientX(drag
                .getCurrentGwtEvent()) - canvas.getAbsoluteLeft() + offsetLeft;

        String offsetTopStr = drag.getDragImage().getStyle().getMarginTop();
        int offsetTop = Integer.parseInt(offsetTopStr.substring(0,
                offsetTopStr.length() - 2));
        int relativeTop = Util
                .getTouchOrMouseClientY(drag.getCurrentGwtEvent())
                - canvas.getAbsoluteTop() + offsetTop;

        drag.getDropDetails().put(Constants.DROP_DETAIL_RELATIVE_LEFT,
                relativeLeft);
        drag.getDropDetails().put(Constants.DROP_DETAIL_RELATIVE_TOP,
                relativeTop);

        // Get component size
        Widget w = (Widget) drag.getTransferable().getData(
                Constants.TRANSFERABLE_DETAIL_COMPONENT);
        if (w != null) {
            drag.getDropDetails().put(Constants.DROP_DETAIL_COMPONENT_WIDTH,
                    w.getOffsetWidth());
            drag.getDropDetails().put(Constants.DROP_DETAIL_COMPONENT_HEIGHT,
                    w.getOffsetHeight());
        } else {
            drag.getDropDetails()
                    .put(Constants.DROP_DETAIL_COMPONENT_WIDTH, -1);
            drag.getDropDetails().put(Constants.DROP_DETAIL_COMPONENT_HEIGHT,
                    -1);
        }

        // Add mouse event details
        MouseEventDetails details = MouseEventDetailsBuilder
                .buildMouseEventDetails(drag.getCurrentGwtEvent(), getElement());
        drag.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                details.serialize());
    }

}
