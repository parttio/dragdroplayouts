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
package fi.jasoft.dragdroplayouts.client.ui.csslayout;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.dd.HorizontalDropLocation;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.ComponentConnector;
import com.vaadin.terminal.gwt.client.ConnectorMap;
import com.vaadin.terminal.gwt.client.MouseEventDetailsBuilder;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.csslayout.VCssLayout;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;

import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler;
import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

/**
 * Client side implementation for {@link DDCssLayout}
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.7.0
 * 
 */
public class VDDCssLayout extends VCssLayout implements VHasDragMode,
        VHasDropHandler, DragStartListener, VHasDragFilter {

    public static final String DRAG_SHADOW_STYLE_NAME = "v-ddcsslayout-drag-shadow";

    private VAbstractDropHandler dropHandler;

    private final VLayoutDragDropMouseHandler ddHandler = new VLayoutDragDropMouseHandler(
            this, LayoutDragMode.NONE);

    protected ApplicationConnection client;

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();

    private final VDragFilter dragFilter = new VDragFilter();

    /**
     * Default constructor
     */
    public VDDCssLayout() {
        super();
        ddHandler.addDragStartListener(this);
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
    public VDropHandler getDropHandler() {
        return dropHandler;
    }

    /**
     * Returns the drag mode
     * 
     * @return
     */
    public LayoutDragMode getDragMode() {
        return ddHandler.getDragMode();
    }

    /**
     * Updates the drop handler. Creates a drop handler if it does not exist.
     * 
     * @param childUidl
     *            The child UIDL containing the rules
     */
    protected void updateDropHandler(UIDL childUidl) {
        if (dropHandler == null) {
            dropHandler = new VAbstractDropHandler() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see com.vaadin.terminal.gwt.client.ui.dd.VDropHandler#
                 * getApplicationConnection()
                 */
                public ApplicationConnection getApplicationConnection() {
                    return client;
                }

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
                    // Intentionally left empty
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
                    updateDragDetails(drag);
                    detachDragImageFromLayout(drag);
                    return postDropHook(drag) && super.drop(drag);
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
                    attachDragImageToLayout(drag);
                    updateDragDetails(drag);
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
                    detachDragImageFromLayout(drag);
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
                    updateDragDetails(drag);
                    postOverHook(drag);

                    // Validate the drop
                    validate(new VAcceptCallback() {
                        public void accepted(VDragEvent event) {
                            moveDragImageInLayout(event);
                        }
                    }, drag);
                }

                @Override
                public ComponentConnector getConnector() {
                    return ConnectorMap.get(client).getConnector(
                            VDDCssLayout.this);
                };

            };
        }
        dropHandler.updateAcceptRules(childUidl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.Widget#onUnload()
     */
    @Override
    protected void onUnload() {
        super.onUnload();
        ddHandler.updateDragMode(LayoutDragMode.NONE);
        iframeCoverUtility.setIframeCoversEnabled(false, getElement(),
                LayoutDragMode.NONE);
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

    private Element placeHolderElement;

    private void attachDragImageToLayout(VDragEvent drag) {
        if (placeHolderElement == null) {
            placeHolderElement = DOM.createDiv();
            placeHolderElement.setInnerHTML("&nbsp;");
        }
    }

    private void updatePlaceHolderStyleProperties(VDragEvent drag) {
        Widget dragged = (Widget) drag.getTransferable().getData(
                Constants.TRANSFERABLE_DETAIL_COMPONENT);
        if (dragged != null) {
            int height = Util.getRequiredHeight(dragged);
            int width = Util.getRequiredWidth(dragged);
            String className = dragged.getElement().getClassName();

            className = className.replaceAll(
                    VLayoutDragDropMouseHandler.ACTIVE_DRAG_SOURCE_STYLENAME,
                    "");

            placeHolderElement.setClassName(className + " "
                    + DRAG_SHADOW_STYLE_NAME);

            placeHolderElement.getStyle().setWidth(width, Unit.PX);
            placeHolderElement.getStyle().setHeight(height, Unit.PX);
        }
    }

    private void detachDragImageFromLayout(VDragEvent drag) {
        if (placeHolderElement != null) {
            if (placeHolderElement.hasParentElement()) {
                placeHolderElement.removeFromParent();
            }
            placeHolderElement = null;
        }
    }

    /**
     * Updates the drop details while dragging. This is needed to ensure client
     * side criterias can validate the drop location.
     * 
     * @param event
     *            The drag event
     */
    protected void updateDragDetails(VDragEvent event) {

        com.google.gwt.user.client.Element over = event.getElementOver();
        if (placeHolderElement.isOrHasChild(over)) {
            // Dragging over the placeholder
            return;
        }

        Widget widget = (Widget) Util.findWidget(over, null);
        if (widget == null) {
            // Null check
            return;
        }

        int offset = 0;
        int index = -1;
        for (int i = 0; i < getWidget().getElement().getChildCount(); i++) {
            Element child = getWidget().getElement().getChild(i).cast();
            if (child.isOrHasChild(placeHolderElement)) {
                offset--;
            } else if (child.isOrHasChild(widget.getElement())) {
                index = i + offset;
                break;
            }
        }
        event.getDropDetails().put(Constants.DROP_DETAIL_TO, index);

        /*
         * The horizontal position within the cell
         */
        event.getDropDetails().put(
                Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION,
                getHorizontalDropLocation(widget, event));

        /*
         * The vertical position within the cell
         */
        event.getDropDetails().put(
                Constants.DROP_DETAIL_VERTICAL_DROP_LOCATION,
                getVerticalDropLocation(widget, event));

        // Add mouse event details
        MouseEventDetails details = MouseEventDetailsBuilder
                .buildMouseEventDetails(event.getCurrentGwtEvent(),
                        getElement());
        event.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                details.serialize());
    }

    private void moveDragImageInLayout(VDragEvent drag) {

        if (placeHolderElement == null) {
            /*
             * Drag image might not have been detach due to lazy attaching in
             * the DragAndDropManager. Detach it again here if it has not been
             * detached.
             */
            attachDragImageToLayout(drag);
            return;
        }

        if (drag.getElementOver().isOrHasChild(placeHolderElement)) {
            return;
        }

        if (placeHolderElement.hasParentElement()) {
            /*
             * Remove the placeholder from the DOM so we can reposition
             */
            placeHolderElement.removeFromParent();
        }

        Widget w = Util.findWidget(drag.getElementOver(), null);
        Widget dragged = (Widget) drag.getTransferable().getData(
                Constants.TRANSFERABLE_DETAIL_COMPONENT);
        if (w == dragged) {
            /*
             * Dragging drag image over the placeholder should not have any
             * effect (except placeholder should be removed)
             */
            return;
        }

        if (w != null && w != this) {

            HorizontalDropLocation hl = getHorizontalDropLocation(w, drag);
            VerticalDropLocation vl = getVerticalDropLocation(w, drag);

            if (hl == HorizontalDropLocation.LEFT
                    || vl == VerticalDropLocation.TOP) {
                Element prev = w.getElement().getPreviousSibling().cast();
                if (prev == null || !dragged.getElement().isOrHasChild(prev)) {

                    w.getElement().getParentElement()
                            .insertBefore(placeHolderElement, w.getElement());

                }
            } else if (hl == HorizontalDropLocation.RIGHT
                    || vl == VerticalDropLocation.BOTTOM) {
                Element next = w.getElement().getNextSibling().cast();
                if (next == null || !dragged.getElement().isOrHasChild(next)) {
                    w.getElement().getParentElement()
                            .insertAfter(placeHolderElement, w.getElement());
                }

            } else {
                Element prev = w.getElement().getPreviousSibling().cast();
                if (prev == null || !dragged.getElement().isOrHasChild(prev)) {
                    w.getElement().getParentElement()
                            .insertBefore(placeHolderElement, w.getElement());
                }
            }

        } else {
            /*
             * First child or hoovering outside of current components
             */
            getWidget().getElement().appendChild(placeHolderElement);
        }

        updatePlaceHolderStyleProperties(drag);
    }

    /**
     * Returns the horizontal location within the cell when hoovering over the
     * cell. By default the cell is devided into three parts: left,center,right
     * with the ratios 10%,80%,10%;
     * 
     * @param container
     *            The widget container
     * @param event
     *            The drag event
     * @return The horizontal drop location
     */
    protected HorizontalDropLocation getHorizontalDropLocation(
            Widget container, VDragEvent event) {
        float ratio = ((DDCssLayoutState) ConnectorMap.get(client)
                .getConnector(this).getState()).getHorizontalDropRatio();
        return VDragDropUtil.getHorizontalDropLocation(container.getElement(),
                Util.getTouchOrMouseClientX(event.getCurrentGwtEvent()), ratio);
    }

    /**
     * Returns the horizontal location within the cell when hoovering over the
     * cell. By default the cell is devided into three parts: left,center,right
     * with the ratios 10%,80%,10%;
     * 
     * @param container
     *            The widget container
     * @param event
     *            The drag event
     * @return The horizontal drop location
     */
    protected VerticalDropLocation getVerticalDropLocation(Widget container,
            VDragEvent event) {
        float ratio = ((DDCssLayoutState) ConnectorMap.get(client)
                .getConnector(this).getState()).getVerticalDropRatio();
        return VDragDropUtil.getVerticalDropLocation(container.getElement(),
                Util.getTouchOrMouseClientY(event.getCurrentGwtEvent()), ratio);
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

}
