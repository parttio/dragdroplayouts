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
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.ComponentConnector;
import com.vaadin.terminal.gwt.client.ConnectorMap;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.MouseEventDetailsBuilder;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.absolutelayout.VAbsoluteLayout;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;

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

    private VAbstractDropHandler dropHandler;

    private final VLayoutDragDropMouseHandler ddHandler = new VLayoutDragDropMouseHandler(
            this, LayoutDragMode.NONE);

    private final VDragFilter dragFilter = new VDragFilter();

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();

    public VDDAbsoluteLayout() {
        super();
        ddHandler.addDragStartListener(this);
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
     * Updates the drag details while a component is dragged
     * 
     * @param drag
     *            The drag event to update the details from
     */
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
     * Updates the drop handler. Creates a drop handler if it does not exist.
     * 
     * @param childUidl
     *            The child UIDL containing the rules
     */
    void updateDropHandler(UIDL childUidl) {
        if (dropHandler == null) {
            dropHandler = new VAbstractDropHandler() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragAccepted
                 * (com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                protected void dragAccepted(VDragEvent drag) {
                }

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #drop(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public boolean drop(VDragEvent drag) {
                    if (super.drop(drag)) {
                        updateDragDetails(drag);
                        return postDropHook(drag);
                    }
                    return false;
                };

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragEnter(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public void dragEnter(VDragEvent drag) {
                    super.dragEnter(drag);
                    Object w = drag.getTransferable().getData(
                            Constants.TRANSFERABLE_DETAIL_COMPONENT);
                    // FIXME
                    // if (w instanceof Container) {
                    // drag.getDragImage().addClassName(
                    // CLASSNAME + "-drag-shadow");
                    // }
                    postEnterHook(drag);
                };

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragLeave(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public void dragLeave(VDragEvent drag) {
                    super.dragLeave(drag);
                    Object w = drag.getTransferable().getData(
                            Constants.TRANSFERABLE_DETAIL_COMPONENT);
                    // FIXME
                    // if (w instanceof Container) {
                    // drag.getDragImage().removeClassName(
                    // CLASSNAME + "-drag-shadow");
                    // }
                    postLeaveHook(drag);
                };

                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragOver(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public void dragOver(VDragEvent drag) {
                    drag.getDragImage().getStyle().setProperty("display", "");

                    // Update drop details with the location so we can
                    // validate it
                    updateDragDetails(drag);
                    postOverHook(drag);
                }

                public ApplicationConnection getApplicationConnection() {
                    return client;
                }

                @Override
                public ComponentConnector getConnector() {
                    return ConnectorMap.get(client).getConnector(
                            VDDAbsoluteLayout.this);
                }
            };
        }
        dropHandler.updateAcceptRules(childUidl);
    }

    /**
     * Returns the drop handler which handles the drop events
     */
    public VDropHandler getDropHandler() {
        return dropHandler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter#getDragFilter
     * ()
     */
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
}
