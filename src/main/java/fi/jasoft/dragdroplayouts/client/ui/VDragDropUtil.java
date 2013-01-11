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
package fi.jasoft.dragdroplayouts.client.ui;

import java.util.List;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConfiguration;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ConnectorMap;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.Util;
import com.vaadin.client.VCaption;
import com.vaadin.client.VConsole;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.communication.StateChangeEvent.StateChangeHandler;
import com.vaadin.client.ui.AbstractConnector;
import com.vaadin.client.ui.VButton;
import com.vaadin.client.ui.VFormLayout;
import com.vaadin.client.ui.VLink;
import com.vaadin.client.ui.VScrollTable;
import com.vaadin.client.ui.VTabsheet.TabCaption;
import com.vaadin.client.ui.dd.VTransferable;
import com.vaadin.shared.Connector;
import com.vaadin.shared.ui.dd.HorizontalDropLocation;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.ui.Button;
import com.vaadin.ui.Link;

import fi.jasoft.dragdroplayouts.client.ui.accordion.VDDAccordion;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.tabsheet.VDDTabSheet;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

/**
 * Utility class for Drag and Drop operations
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.5.0
 */
public final class VDragDropUtil {

    private VDragDropUtil() {
        // Prevent instantiation
    }

    /**
     * Get the vertical drop location in a ordered layout
     * 
     * @param element
     *            The target element or cell
     * @param clientY
     *            The client y-coordinate
     * @param topBottomRatio
     *            The ratio how the cell has been divided
     * @return The drop location
     */
    public static VerticalDropLocation getVerticalDropLocation(Element element,
            int clientY, double topBottomRatio) {
        int offsetHeight = element.getOffsetHeight();
        return getVerticalDropLocation(element, offsetHeight, clientY,
                topBottomRatio);
    }

    /**
     * Get the vertical drop location in a ordered layout
     * 
     * @param element
     *            The target element or cell
     * @param offsetHeight
     *            The height of the cell
     * @param clientY
     *            The width of the cell
     * @param topBottomRatio
     *            The ratio of the cell
     * @return The location of the drop
     */
    public static VerticalDropLocation getVerticalDropLocation(Element element,
            int offsetHeight, int clientY, double topBottomRatio) {

        int absoluteTop = element.getAbsoluteTop();
        int fromTop = clientY - absoluteTop;

        float percentageFromTop = (fromTop / (float) offsetHeight);
        if (percentageFromTop < topBottomRatio) {
            return VerticalDropLocation.TOP;
        } else if (percentageFromTop > 1 - topBottomRatio) {
            return VerticalDropLocation.BOTTOM;
        } else {
            return VerticalDropLocation.MIDDLE;
        }
    }

    /**
     * Get the horizontal drop location in an ordered layout
     * 
     * @param element
     *            The target element or cell
     * @param clientX
     *            The x-coordinate of the drop
     * @param leftRightRatio
     *            The ratio of how the cell has been divided
     * @return the drop location relative to the cell
     */
    public static HorizontalDropLocation getHorizontalDropLocation(
            Element element, int clientX, double leftRightRatio) {

        int absoluteLeft = element.getAbsoluteLeft();
        int offsetWidth = element.getOffsetWidth();
        int fromTop = clientX - absoluteLeft;

        float percentageFromTop = (fromTop / (float) offsetWidth);
        if (percentageFromTop < leftRightRatio) {
            return HorizontalDropLocation.LEFT;
        } else if (percentageFromTop > 1 - leftRightRatio) {
            return HorizontalDropLocation.RIGHT;
        } else {
            return HorizontalDropLocation.CENTER;
        }
    }

    /**
     * Creates a transferable for the tabsheet
     * 
     * @param tabsheet
     *            The tabsheet the event occurred
     * @param tab
     *            The tab on which the event occurred
     * @param event
     *            The event
     * @param root
     *            The root widget
     * @return
     */
    private static VTransferable createTabsheetTransferableFromMouseDown(
            VDDTabSheet tabsheet, Widget tab, NativeEvent event) {

        // Create transferable
        VTransferable transferable = new VTransferable();
        transferable.setDragSource(VDragDropUtil.findConnectorFor(tabsheet));
        if (tabsheet != tab) {
            transferable.setData(Constants.TRANSFERABLE_DETAIL_COMPONENT,
                    VDragDropUtil.findConnectorFor(tabsheet));
            transferable.setData(Constants.TRANSFERABLE_DETAIL_INDEX,
                    tabsheet.getTabPosition(tab));
        }
        transferable.setData(Constants.TRANSFERABLE_DETAIL_MOUSEDOWN,
                MouseEventDetailsBuilder.buildMouseEventDetails(event)
                .serialize());

        return transferable;
    }

    /**
     * Creates a transferable for the Accordion
     * 
     * @param accordion
     *            The Accordion where the event occurred
     * @param tab
     *            The tab on which the event occurred
     * @param event
     *            The event
     * @param root
     *            The root widget
     * @return
     */
    private static VTransferable createAccordionTransferableFromMouseDown(
            VDDAccordion accordion, VCaption tabCaption, NativeEvent event) {

        // Create transferable
        VTransferable transferable = new VTransferable();
        transferable.setDragSource(VDragDropUtil.findConnectorFor(accordion));
        transferable.setData(Constants.TRANSFERABLE_DETAIL_COMPONENT,
                VDragDropUtil.findConnectorFor(tabCaption.getParent()));
        transferable.setData(Constants.TRANSFERABLE_DETAIL_INDEX,
                accordion.getWidgetIndex(tabCaption.getParent()));
        transferable.setData(Constants.TRANSFERABLE_DETAIL_MOUSEDOWN,
                MouseEventDetailsBuilder.buildMouseEventDetails(event)
                .serialize());

        return transferable;
    }

    /**
     * Creates a transferable from a mouse down event. Returns null if creation
     * was not successful.
     * 
     * @param event
     *            The mouse down event
     * @param root
     *            The root layout from where the component is dragged
     * @return A transferable or NULL if something failed
     */
    public static VTransferable createLayoutTransferableFromMouseDown(
            NativeEvent event, Widget root, Widget target) {

        // NPE check
        if (target == null) {
            VConsole.error("Could not find widget");
            return null;
        }

        // Special treatment for Tabsheet
        if (root instanceof VDDTabSheet) {
            if (isCaption(target) || target == root) {
                // We are dragging a tabsheet tab, handle it
                return createTabsheetTransferableFromMouseDown(
                        (VDDTabSheet) root, target, event);
            } else {
                return null;
            }
        }

        // Special treatment for Accordion
        if (root instanceof VDDAccordion) {
            if (isCaption(target) || target == root) {
                // We are dragging a Accordion tab, handle it
                return createAccordionTransferableFromMouseDown(
                        (VDDAccordion) root, (VCaption) target, event);
            } else {
                // Do not allow dragging content, only the "tab"
                return null;
            }
        }

        // Ensure we have the right widget
        target = getTransferableWidget(target);

        // Find the containing layout of the component
        ComponentConnector widgetConnector = VDragDropUtil
                .findConnectorFor(target);
        if (widgetConnector == null) {
            VConsole.error("No connector found for " + target);
            return null;
        }

        ComponentConnector layoutConnector = (ComponentConnector) widgetConnector
                .getParent();

        // Iterate until parent either is the root or a layout with drag and
        // drop enabled
        Widget layout = layoutConnector.getWidget();

        while (layout != root && layout != null && layoutConnector != null) {
            if (layout instanceof VHasDragMode
                    && ((VHasDragMode) layout).getDragMode() != LayoutDragMode.NONE) {
                // Found parent layout with support for drag and drop
                break;
            }
            target = layout;
            widgetConnector = layoutConnector;
            layoutConnector = (ComponentConnector) layoutConnector.getParent();
            layout = layoutConnector.getWidget();
        }

        // Consistency check
        if (target == null || root == target || layoutConnector == null) {
            VConsole.error("Consistency check failed");
            return null;
        }

        // Ensure layout allows dragging
        if (!isDraggingEnabled(layoutConnector, target)) {
            return null;
        }

        return createTransferable(layoutConnector, widgetConnector, event);
    }

    private static VTransferable createTransferable(ComponentConnector layout,
            ComponentConnector widgetConnector, NativeEvent event) {
        VTransferable transferable = new VTransferable();
        transferable.setDragSource(layout);
        transferable.setData(Constants.TRANSFERABLE_DETAIL_COMPONENT,
                widgetConnector);
        transferable.setData(Constants.TRANSFERABLE_DETAIL_MOUSEDOWN,
                MouseEventDetailsBuilder.buildMouseEventDetails(event)
                .serialize());
        return transferable;
    }

    /**
     * Resolve if widget is a Vaadin Caption
     * 
     * @param w
     *            Widget to check
     * @return True if the widget is a caption widget, false otherwise
     */
    public static boolean isCaption(Widget w) {
        return w instanceof VCaption || w instanceof VFormLayout.Caption;
    }

    /**
     * Does the same as {@link #isCaption(Widget)} but also returns true for
     * Vaadin widgets that do not have a caption like {@link Button} and
     * {@link Link}
     * 
     * @param w
     *            The widget to check
     * @return True if the widget is a caption widget, false otherwise
     */
    public static boolean isCaptionOrCaptionless(Widget w) {
        return isCaption(w) || w instanceof VButton || w instanceof VLink;
    }

    private static final native Widget getTabsheetTabOwner(TabCaption tab)
    /*-{
        return tab.@com.vaadin.client.ui.VTabsheet.TabCaption::getTab().getTabsheet();
    }-*/;

    public static Widget getTransferableWidget(Widget w) {
        // Ensure we are dealing with a Vaadin component
        ComponentConnector connector = Util.findConnectorFor(w);
        if (connector != null) {
            w = connector.getWidget();
        }

        if (isCaption(w)) {
            // Dragging caption means dragging component the caption belongs to
            Widget owner = null;
            if (w instanceof TabCaption) {
                TabCaption caption = (TabCaption) w;
                owner = getTabsheetTabOwner(caption);
            }
            if (w instanceof VCaption) {
                ComponentConnector ownerConnector = ((VCaption) w).getOwner();
                owner = ownerConnector.getWidget();
            } else if (w instanceof VFormLayout.Caption) {
                ComponentConnector ownerConnector = ((VFormLayout.Caption) w)
                        .getOwner();
                owner = ownerConnector.getWidget();
            }
            if (owner != null) {
                w = owner;
            }
        } else if (w instanceof VScrollTable.VScrollTableBody.VScrollTableRow) {
            // Table rows are paintable but we do not want to drag them so
            // search for next paintable which should be the table itself
            w = w.getParent();
            while (!(w instanceof Paintable)) {
                w = w.getParent();
            }
        }
        // else if (w.getParent().getParent().getParent() instanceof
        // VTwinColSelect) {
        // // TwinColSelect has paintable buttons..
        // w = w.getParent().getParent().getParent();
        // }

        return w;
    }

    /**
     * Is dragging enabled for a component container
     * 
     * @param layout
     *            The component container to check
     * @return
     */
    public static boolean isDraggingEnabled(Connector layout, Widget w) {
        boolean draggingEnabled = false;
        if (layout instanceof VHasDragMode) {
            LayoutDragMode dm = ((VHasDragMode) layout).getDragMode();
            draggingEnabled = dm != LayoutDragMode.NONE;
        }
        if (layout instanceof VHasDragFilter) {
            draggingEnabled = draggingEnabled
                    && ((VHasDragFilter) layout).getDragFilter().isDraggable(w);
        }
        return draggingEnabled;
    }

    /**
     * Removes the Drag and drop fake paintable from an UIDL
     * 
     * @param uidl
     *            The uidl which contains a dragdrop paintable (-ac)
     * @return UIDL stripped of the paintable
     */
    public static native UIDL removeDragDropCriteraFromUIDL(UIDL uidl)
    /*-{
        var obj = new Array();
        for(key in uidl){
            if(uidl[key][0] != "-ac"){
               obj[key] = uidl[key];
            }
        }
        return obj;
    }-*/;

    /**
     * Measures the left margin of an element
     * 
     * @param element
     *            The element to measure
     * @return Left margin in pixels
     */
    public static int measureMarginLeft(Element element) {
        return element.getAbsoluteLeft()
                - element.getParentElement().getAbsoluteLeft();
    }

    /**
     * Measures the top margin of an element
     * 
     * @param element
     *            The element to measure
     * @return Top margin in pixels
     */
    public static int measureMarginTop(Element element) {
        return element.getAbsoluteTop()
                - element.getParentElement().getAbsoluteTop();
    }

    public static ComponentConnector findConnectorFor(Widget widget) {
        List<ApplicationConnection> runningApplications = ApplicationConfiguration
                .getRunningApplications();
        for (ApplicationConnection applicationConnection : runningApplications) {
            ConnectorMap connectorMap = ConnectorMap.get(applicationConnection);
            ComponentConnector connector = connectorMap.getConnector(widget);
            if (connector == null) {
                continue;
            }
            if (connector.getConnection() == applicationConnection) {
                return connector;
            }
        }

        return null;
    }

    public static void listenToStateChangeEvents(
            final AbstractConnector connector,
            final VLayoutDragDropMouseHandler mouseHandler,
            final IframeCoverUtility iframeUtility, final Widget widget) {
        /*
         * Listen to drag mode updates
         */
        connector.addStateChangeHandler("dragMode", new StateChangeHandler() {
            @Override
            public void onStateChanged(StateChangeEvent stateChangeEvent) {

                DDLayoutState state = (DDLayoutState) connector.getState();

                mouseHandler.updateDragMode(state.getDragMode());

                iframeUtility.setIframeCoversEnabled(state.isIframeShims(),
                        widget.getElement(), state.getDragMode());
            }
        });

        /*
         * Listen to iframe shim updates
         */
        connector.addStateChangeHandler("iframeShims",
                new StateChangeHandler() {
            @Override
            public void onStateChanged(StateChangeEvent stateChangeEvent) {
                DDLayoutState state = (DDLayoutState) connector
                        .getState();
                iframeUtility.setIframeCoversEnabled(
                        state.isIframeShims(), widget.getElement(),
                        state.getDragMode());
            }
        });

    }

}
