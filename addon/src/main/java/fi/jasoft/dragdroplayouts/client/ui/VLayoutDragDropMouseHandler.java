/*
 * Copyright 2014 John Ahlroos
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package fi.jasoft.dragdroplayouts.client.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.BrowserInfo;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.Util;
import com.vaadin.client.VCaption;
import com.vaadin.client.VConsole;
import com.vaadin.client.WidgetUtil;
import com.vaadin.client.ui.VAccordion;
import com.vaadin.client.ui.VAccordion.StackItem;
import com.vaadin.client.ui.VCssLayout;
import com.vaadin.client.ui.VFormLayout;
import com.vaadin.client.ui.VPanel;
import com.vaadin.client.ui.VSlider;
import com.vaadin.client.ui.VTabsheet;
import com.vaadin.client.ui.VTabsheet.Tab;
import com.vaadin.client.ui.VTabsheet.TabCaption;
import com.vaadin.client.ui.VTextField;
import com.vaadin.client.ui.dd.VDragAndDropManager;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.client.ui.dd.VTransferable;

import fi.jasoft.dragdroplayouts.client.ui.accordion.VDDAccordion;
import fi.jasoft.dragdroplayouts.client.ui.formlayout.VDDFormLayout;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VDragImageProvider;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragImageReferenceSupport;

/**
 * Mouse handler for starting component drag operations
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.4.0
 */
public class VLayoutDragDropMouseHandler implements MouseDownHandler,
        TouchStartHandler, VHasDragImageReferenceSupport {

    public static final String ACTIVE_DRAG_SOURCE_STYLENAME = "v-dd-active-drag-source";

    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private final Widget root;

    private Widget currentDraggedWidget;

    private HandlerRegistration mouseUpHandlerReg;

    private final List<HandlerRegistration> handlers = new LinkedList<HandlerRegistration>();

    private final List<DragStartListener> dragStartListeners = new ArrayList<VLayoutDragDropMouseHandler.DragStartListener>();

    private Widget attachTarget;

    private VDragImageProvider dragImageProvider;

    /**
     * A listener to listen for drag start events
     */
    public interface DragStartListener {
        /**
         * Called when a drag is about to begin
         * 
         * @param widget
         *            The widget which is about to be dragged
         * @param mode
         *            The draggin mode
         * @return Should the dragging be commenced.
         */
        boolean dragStart(Widget widget, LayoutDragMode mode);
    }

    /**
     * Constructor
     * 
     * @param root
     *            The root element
     * @param dragMode
     *            The drag mode of the layout
     */
    public VLayoutDragDropMouseHandler(Widget root, LayoutDragMode dragMode) {
        this.dragMode = dragMode;
        this.root = root;
    }

    /**
     * Is the mouse down event a valid mouse drag event, i.e. left mouse button
     * is pressed without any modifier keys
     * 
     * @param event
     *            The mouse event
     * @return Is the mouse event a valid drag event
     */
    private boolean isMouseDragEvent(NativeEvent event) {
        boolean hasModifierKey = event.getAltKey() || event.getCtrlKey()
                || event.getMetaKey() || event.getShiftKey();
        return !(hasModifierKey || event.getButton() > NativeEvent.BUTTON_LEFT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.dom.client.TouchStartHandler#onTouchStart(com.google
     * .gwt.event.dom.client.TouchStartEvent)
     */
    @Override
    public void onTouchStart(TouchStartEvent event) {
        initiateDrag(event.getNativeEvent());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.dom.client.MouseDownHandler#onMouseDown(com.google
     * .gwt.event.dom.client.MouseDownEvent)
     */
    @Override
    public void onMouseDown(MouseDownEvent event) {
        initiateDrag(event.getNativeEvent());
    }

    /**
     * Called when the dragging a component should be initiated by both a mouse
     * down event as well as a touch start event
     * 
     * FIXME This method is a BIG hack to circumvent Vaadin's very poor client
     * side API's. This will break often. Refactor once Vaadin gets a grip.
     * 
     * @param event
     */
    protected void initiateDrag(NativeEvent event) {
        // Check that dragging is enabled
        if (dragMode == LayoutDragMode.NONE) {
            return;
        }

        // Dragging can only be done with left mouse button and no modifier keys
        if (!isMouseDragEvent(event) && !Util.isTouchEvent(event)) {
            return;
        }

        if (!Element.is(event.getEventTarget())) {
            // Only element nodes are draggable
            return;
        }

        // Get target widget
        Element targetElement = Element.as(event.getEventTarget());
        ;
        Widget target = Util.findWidget(targetElement, null);

        if (isEventOnScrollBar(event)) {
            return;
        }

        // Abort if drag mode is caption mode and widget is not a caption
        boolean isPanelCaption = target instanceof VPanel && targetElement
                .getParentElement().getClassName().contains("v-panel-caption");
        boolean isCaption = isPanelCaption
                || VDragDropUtil.isCaptionOrCaptionless(target);

        if (dragMode == LayoutDragMode.CAPTION && !isCaption) {
            /*
             * Ensure target is a caption in caption mode
             */
            return;
        }

        if (dragMode == LayoutDragMode.CAPTION && isCaption) {

            /*
             * Ensure that captions in nested layouts don't get accepted if in
             * caption mode
             */

            Widget w = VDragDropUtil.getTransferableWidget(target);
            ComponentConnector c = Util.findConnectorFor(w);
            ComponentConnector parent = (ComponentConnector) c.getParent();
            if (parent.getWidget() != root) {
                return;
            }
        }

        // Create the transfarable
        VTransferable transferable = VDragDropUtil
                .createLayoutTransferableFromMouseDown(event, root, target);

        // Are we trying to drag the root layout
        if (transferable == null) {
            VConsole.error("Creating transferable on mouse down returned null");
            return;
        }

        // Resolve the component
        final Widget w;
        ComponentConnector c = null, parent = null;

        if (target instanceof TabCaption) {
            TabCaption tabCaption = (TabCaption) target;
            Tab tab = tabCaption.getTab();
            int tabIndex = ((ComplexPanel) tab.getParent()).getWidgetIndex(tab);
            VTabsheet tabsheet = tab.getTabsheet();

            w = tab;
            c = tabsheet.getTab(tabIndex);
            parent = Util.findConnectorFor(tabsheet);

        } else if (root instanceof VDDAccordion) {
            w = target;
            parent = Util.findConnectorFor(root);

            StackItem tab = WidgetUtil.findWidget(targetElement,
                    StackItem.class);
            if (tab != null
                    && root.getElement().isOrHasChild(tab.getElement())) {
                c = ((VDDAccordion) root)
                        .getTab(((VDDAccordion) root).getTabPosition(tab));
            }

        } else if (transferable
                .getData(Constants.TRANSFERABLE_DETAIL_COMPONENT) != null) {

            ComponentConnector connector = (ComponentConnector) transferable
                    .getData(Constants.TRANSFERABLE_DETAIL_COMPONENT);
            w = connector.getWidget();
            c = Util.findConnectorFor(w);
            parent = (ComponentConnector) c.getParent();

        } else {
            // Failsafe if no widget was found
            w = root;
            c = Util.findConnectorFor(w);
            parent = c;
            VConsole.log(
                    "Could not resolve component, using root as component");
        }

        // Ensure component is draggable
        if (!VDragDropUtil.isDraggingEnabled(parent, w)) {
            VConsole.log("Dragging disabled for " + w.getClass().getName()
                    + " in " + parent.getWidget().getClass().getName());
            VDragAndDropManager.get().interruptDrag();
            return;
        }

        event.preventDefault();
        event.stopPropagation();

        // Announce drag start to listeners
        for (DragStartListener dl : dragStartListeners) {
            if (!dl.dragStart(w, dragMode)) {
                VDragAndDropManager.get().interruptDrag();
                return;
            }
        }

        /*
         * A hack to remove slider popup when dragging. This is done by first
         * focusing the slider and then unfocusing so we get a blur event which
         * will remove the popup.
         */
        if (w instanceof VSlider) {
            VSlider slider = (VSlider) w;
            slider.setFocus(true);
            slider.setFocus(false);
        }

        /*
         * Ensure textfields get focus when dragging so they can be used
         */
        if (w instanceof VTextField) {
            ((VTextField) w).setFocus(true);
        }

        currentDraggedWidget = w;

        // Announce to handler that we are starting a drag operation
        VDragEvent currentDragEvent = VDragAndDropManager.get()
                .startDrag(transferable, event, true);

        /*
         * Create the drag image
         */
        com.google.gwt.dom.client.Element dragImageElement = dragImageProvider == null
                ? null : dragImageProvider.getDragImageElement(w);

        if (dragImageElement != null) {

            // Set stylename to proxy component as well
            dragImageElement.addClassName(ACTIVE_DRAG_SOURCE_STYLENAME);

        } else if (root instanceof VCssLayout) {
            /*
             * CSS Layout does not have an enclosing div so we just use the
             * component div
             */
            dragImageElement = w.getElement();

        } else if (root instanceof VTabsheet) {
            /*
             * Tabsheet should use the dragged tab as a drag image
             */
            dragImageElement = targetElement;

        } else if (root instanceof VAccordion) {
            /*
             * Accordion should use the dragged tab as a drag image
             */
            dragImageElement = targetElement;

        } else if (root instanceof VFormLayout) {
            /*
             * Dragging a component in a form layout should include the caption
             * and error indicator as well
             */
            Element rowElement = (Element) VDDFormLayout
                    .getRowFromChildElement(
                            (com.google.gwt.dom.client.Element) w.getElement()
                                    .cast(),
                            (com.google.gwt.dom.client.Element) root
                                    .getElement().cast())
                    .cast();

            dragImageElement = rowElement;

        } else {
            /*
             * For other layouts we just use the target element;
             */
            dragImageElement = targetElement;
        }

        currentDragEvent.createDragImage(dragImageElement, true);
        Element clone = currentDragEvent.getDragImage();
        assert(clone != null);

        // Lock drag image dimensions
        clone.getStyle().setWidth(dragImageElement.getOffsetWidth(), Unit.PX);
        clone.getStyle().setHeight(dragImageElement.getOffsetHeight(), Unit.PX);

        if (c != null && c.delegateCaptionHandling()
                && !(root instanceof VTabsheet)
                && !(root instanceof VAccordion)) {
            /*
             * Captions are not being dragged with the widget since they are
             * separate. Manually add a clone of the caption to the drag image.
             */
            if (target instanceof VCaption) {
                clone.insertFirst(targetElement.cloneNode(true));
            }
        }

        if (BrowserInfo.get().isIE()) {
            // Fix IE not aligning the drag image correctly when dragging
            // layouts
            clone.getStyle().setPosition(Position.ABSOLUTE);
        }

        currentDraggedWidget.addStyleName(ACTIVE_DRAG_SOURCE_STYLENAME);

        // Listen to mouse up for cleanup
        mouseUpHandlerReg = Event
                .addNativePreviewHandler(new Event.NativePreviewHandler() {
                    @Override
                    public void onPreviewNativeEvent(NativePreviewEvent event) {
                        if (event.getTypeInt() == Event.ONMOUSEUP
                                || event.getTypeInt() == Event.ONTOUCHEND
                                || event.getTypeInt() == Event.ONTOUCHCANCEL) {
                            if (mouseUpHandlerReg != null) {
                                mouseUpHandlerReg.removeHandler();
                                if (currentDraggedWidget != null) {

                                    currentDraggedWidget.removeStyleName(
                                            ACTIVE_DRAG_SOURCE_STYLENAME);

                                    if (dragImageProvider != null) {
                                        com.google.gwt.dom.client.Element dragImageElement = dragImageProvider
                                                .getDragImageElement(
                                                        currentDraggedWidget);
                                        if (dragImageElement != null) {
                                            dragImageElement.removeClassName(
                                                    ACTIVE_DRAG_SOURCE_STYLENAME);
                                        }
                                    }

                                    currentDraggedWidget = null;
                                }
                            }

                            // Ensure capturing is turned off at mouse up
                            Event.releaseCapture(RootPanel.getBodyElement());
                        }
                    }
                });

    }

    /*
     * Whether the event was performed on a scrollbar.
     */
    private boolean isEventOnScrollBar(NativeEvent event) {
        Element element = Element.as(event.getEventTarget());
        ;

        if (WidgetUtil.mayHaveScrollBars(element)) {

            final int nativeScrollbarSize = WidgetUtil.getNativeScrollbarSize();

            int x = WidgetUtil.getTouchOrMouseClientX(event)
                    - element.getAbsoluteLeft();
            int y = WidgetUtil.getTouchOrMouseClientY(event)
                    - element.getAbsoluteTop();

            // Hopefully we have horizontal scroll.
            final int scrollWidth = element.getScrollWidth();
            final int clientWidth = element.getClientWidth();
            if (scrollWidth > clientWidth
                    && clientWidth - nativeScrollbarSize < x) {
                return true;
            }

            // Hopefully we have vertical scroll.
            final int scrollHeight = element.getScrollHeight();
            final int clientHeight = element.getClientHeight();
            if (scrollHeight > clientHeight
                    && clientHeight - nativeScrollbarSize < y) {
                return true;
            }

        }

        return false;
    }

    /**
     * Set the current drag mode
     * 
     * @param dragMode
     *            The drag mode to use
     */
    public void updateDragMode(LayoutDragMode dragMode) {
        if (dragMode == this.dragMode) {
            return;
        }

        this.dragMode = dragMode;
        if (dragMode == LayoutDragMode.NONE) {
            detach();
        } else {
            attach();
        }
    }

    /**
     * Add a drag start listener to monitor drag starts
     * 
     * @param listener
     */
    public void addDragStartListener(DragStartListener listener) {
        dragStartListeners.add(listener);
    }

    /**
     * Remove a drag start listener
     * 
     * @param listener
     */
    public void removeDragStartListener(DragStartListener listener) {
        dragStartListeners.remove(listener);
    }

    /**
     * Start listening to events
     */
    private void attach() {
        if (handlers.isEmpty()) {
            if (attachTarget == null) {
                handlers.add(
                        root.addDomHandler(this, MouseDownEvent.getType()));
                handlers.add(
                        root.addDomHandler(this, TouchStartEvent.getType()));
            } else {
                handlers.add(attachTarget.addDomHandler(this,
                        MouseDownEvent.getType()));
                handlers.add(attachTarget.addDomHandler(this,
                        TouchStartEvent.getType()));
            }
        }
    }

    /**
     * Stop listening to events
     */
    private void detach() {
        for (HandlerRegistration reg : handlers) {
            reg.removeHandler();
        }
        handlers.clear();
    }

    public Widget getAttachTarget() {
        return attachTarget;
    }

    public void setAttachTarget(Widget attachTarget) {
        this.attachTarget = attachTarget;
    }

    public LayoutDragMode getDragMode() {
        return dragMode;
    }

    @Override
    public void setDragImageProvider(VDragImageProvider provider) {
        this.dragImageProvider = provider;
    }
}
