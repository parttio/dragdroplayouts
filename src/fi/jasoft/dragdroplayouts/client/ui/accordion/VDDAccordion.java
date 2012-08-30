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
package fi.jasoft.dragdroplayouts.client.ui.accordion;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.dd.VerticalDropLocation;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.ConnectorMap;
import com.vaadin.terminal.gwt.client.MouseEventDetailsBuilder;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.ui.accordion.VAccordion;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;

import fi.jasoft.dragdroplayouts.DDAccordion;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler;
import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.VTabDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

/**
 * Client side implementation for {@link DDAccordion}
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.4.0
 */
public class VDDAccordion extends VAccordion implements VHasDragMode,
        VHasDropHandler, DragStartListener, VDDTabContainer, VHasDragFilter {

    public static final String CLASSNAME_OVER = "dd-over";
    public static final String CLASSNAME_SPACER = "spacer";

    private VDDAccordionDropHandler dropHandler;

    private StackItem currentlyEmphasised;

    private ApplicationConnection client;

    private final Map<Element, StackItem> elementTabMap = new HashMap<Element, StackItem>();

    private final Widget spacer;

    // The drag mouse handler which handles the creation of the transferable
    private final VLayoutDragDropMouseHandler ddMouseHandler = new VLayoutDragDropMouseHandler(
            this, LayoutDragMode.NONE);

    private final VDragFilter dragFilter = new VTabDragFilter(this);

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();

    public VDDAccordion() {
        spacer = GWT.create(HTML.class);
        spacer.setWidth("100%");
        spacer.setStyleName(CLASSNAME_SPACER);
        ddMouseHandler.addDragStartListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.Widget#onUnload()
     */
    @Override
    protected void onUnload() {
        super.onUnload();
        ddMouseHandler.updateDragMode(LayoutDragMode.NONE);
        iframeCoverUtility.setIframeCoversEnabled(false, getElement(),
                LayoutDragMode.NONE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler#getDropHandler()
     */
    public VDDAccordionDropHandler getDropHandler() {
        return dropHandler;
    }

    public void setDropHandler(VDDAccordionDropHandler dropHandler) {
        this.dropHandler = dropHandler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fi.jasoft.dragdroplayouts.client.ui.VHasDragMode#getDragMode()
     */
    public LayoutDragMode getDragMode() {
        return ddMouseHandler.getDragMode();
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
        return ddMouseHandler.getDragMode() != LayoutDragMode.NONE
                && dragFilter.isDraggable(widget);
    }

    /**
     * Updates the drop details while dragging. This is needed to ensure client
     * side criterias can validate the drop location.
     * 
     * @param widget
     *            The container which we are hovering over
     * @param event
     *            The drag event
     */
    public void updateDropDetails(VDragEvent event) {
        StackItem tab = getTabByElement(event.getElementOver());
        if (tab != null) {
            // Add index
            int index = getWidgetIndex(tab);
            event.getDropDetails().put(Constants.DROP_DETAIL_TO, index);

            // Add drop location
            VerticalDropLocation location = getDropLocation(tab, event);
            event.getDropDetails().put(
                    Constants.DROP_DETAIL_VERTICAL_DROP_LOCATION, location);

            // Add mouse event details
            MouseEventDetails details = MouseEventDetailsBuilder
                    .buildMouseEventDetails(event.getCurrentGwtEvent(),
                            getElement());

            event.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                    details.serialize());
        }
    }

    private StackItem getTabByElement(Element element) {
        StackItem item = elementTabMap.get(element);
        if (item == null) {
            for (int i = 0; i < getTabCount(); i++) {
                StackItem tab = (StackItem) getWidget(i);
                if (tab.getElement().isOrHasChild(element)) {
                    item = tab;
                    elementTabMap.put(element, tab);
                }
            }
        }

        return item;
    }

    /**
     * Returns the drop location of a tab
     * 
     * @param tab
     *            The tab that was dragged
     * @param event
     *            The drag event
     * @return
     */
    protected VerticalDropLocation getDropLocation(StackItem tab,
            VDragEvent event) {
        VerticalDropLocation location;
        float ratio = ((DDAccordionState) ConnectorMap.get(client)
                .getConnector(this).getState()).getTabTopBottomDropRatio();
        if (tab.isOpen()) {
            location = VDragDropUtil.getVerticalDropLocation(tab.getElement(),
                    Util.getTouchOrMouseClientY(event.getCurrentGwtEvent()),
                    ratio);
        } else {
            location = VDragDropUtil.getVerticalDropLocation(tab.getWidget(0)
                    .getElement(), Util.getTouchOrMouseClientY(event
                    .getCurrentGwtEvent()), ratio);
        }
        return location;
    }

    /**
     * Emphasisizes a container element
     * 
     * @param element
     */
    protected void emphasis(Element element, VDragEvent event) {

        // Find the tab
        StackItem tab = getTabByElement(element);

        if (tab != null && currentlyEmphasised != tab) {

            VerticalDropLocation location = getDropLocation(tab, event);

            if (location == VerticalDropLocation.MIDDLE) {
                if (tab.isOpen()) {
                    tab.addStyleName(CLASSNAME_OVER);
                } else {
                    tab.getWidget(0).addStyleName(CLASSNAME_OVER);
                }
            } else if (!spacer.isAttached()) {
                if (location == VerticalDropLocation.TOP) {
                    insert(spacer, getElement(), getWidgetIndex(tab), true);
                    tab.setHeight((tab.getOffsetHeight() - spacer
                            .getOffsetHeight()) + "px");
                } else if (location == VerticalDropLocation.BOTTOM) {
                    insert(spacer, getElement(), getWidgetIndex(tab) + 1, true);
                    int newHeight = tab.getOffsetHeight()
                            - spacer.getOffsetHeight();
                    if (getWidgetIndex(spacer) == getWidgetCount() - 1) {
                        newHeight -= spacer.getOffsetHeight();
                    }
                    if (newHeight >= 0) {
                        tab.setHeight(newHeight + "px");
                    }
                }
            }
            currentlyEmphasised = tab;
        }
    }

    /**
     * Removes any previous emphasis made by drag&drop
     */
    protected void deEmphasis() {
        if (currentlyEmphasised != null) {
            currentlyEmphasised.removeStyleName(CLASSNAME_OVER);
            currentlyEmphasised.getWidget(0).removeStyleName(CLASSNAME_OVER);
            if (spacer.isAttached()) {

                int newHeight = currentlyEmphasised.getHeight()
                        + spacer.getOffsetHeight();

                if (getWidgetIndex(spacer) == getWidgetCount() - 1) {
                    newHeight += spacer.getOffsetHeight();
                }

                currentlyEmphasised.setHeight(newHeight + "px");

                remove(spacer);
            }
            currentlyEmphasised = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer#
     * getTabContentPosition(com.google.gwt.user.client.ui.Widget)
     */
    public int getTabContentPosition(Widget w) {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer#getTabPosition
     * (com.google.gwt.user.client.ui.Widget)
     */
    public int getTabPosition(Widget tab) {
        // TODO Auto-generated method stub
        return 0;
    }

    public VDragFilter getDragFilter() {
        return dragFilter;
    }

    IframeCoverUtility getIframeCoverUtility() {
        return iframeCoverUtility;
    }

    VLayoutDragDropMouseHandler getMouseHandler() {
        return ddMouseHandler;
    }
}
