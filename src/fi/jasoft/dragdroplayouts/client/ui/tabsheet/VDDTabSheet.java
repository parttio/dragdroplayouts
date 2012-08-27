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
package fi.jasoft.dragdroplayouts.client.ui.tabsheet;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.ComponentConnector;
import com.vaadin.terminal.gwt.client.ConnectorMap;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.MouseEventDetailsBuilder;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.VCaption;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VAcceptCallback;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;
import com.vaadin.terminal.gwt.client.ui.tabsheet.VTabsheet;
import com.vaadin.terminal.gwt.client.ui.tabsheet.VTabsheetPanel;

import fi.jasoft.dragdroplayouts.DDTabSheet;
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
 * Client side implementation for {@link DDTabSheet}
 * 
 * @author John Ahlroos / www.jasoft.fi
 * @since 0.4.0
 */
public class VDDTabSheet extends VTabsheet implements VHasDragMode,
        VHasDropHandler, DragStartListener, VDDTabContainer, VHasDragFilter {

    public static final String CLASSNAME_NEW_TAB = "new-tab";
    public static final String CLASSNAME_NEW_TAB_LEFT = "new-tab-left";
    public static final String CLASSNAME_NEW_TAB_RIGHT = "new-tab-right";
    public static final String CLASSNAME_NEW_TAB_CENTER = "new-tab-center";

    private VAbstractDropHandler dropHandler;

    private ApplicationConnection client;

    private final ComplexPanel tabBar;
    private final VTabsheetPanel tabPanel;

    private final Element spacer;

    private Element currentlyEmphasised;

    private final Element newTab = DOM.createDiv();

    private final VDragFilter dragFilter = new VTabDragFilter(this);

    private final IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();

    private final VLayoutDragDropMouseHandler ddMouseHandler = new VLayoutDragDropMouseHandler(
            this, LayoutDragMode.NONE);

    public VDDTabSheet() {
        super();

        newTab.setClassName(CLASSNAME_NEW_TAB);

        // Get the tabBar
        tabBar = (ComplexPanel) getChildren().get(0);

        // Get the content
        tabPanel = (VTabsheetPanel) getChildren().get(1);

        // Get the spacer
        Element tBody = tabBar.getElement();
        spacer = tBody.getChild(tBody.getChildCount() - 1).getChild(0)
                .getChild(0).cast();

        ddMouseHandler.addDragStartListener(this);
        ddMouseHandler.setAttachTarget(tabBar);
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
    public VDropHandler getDropHandler() {
        return dropHandler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fi.jasoft.dragdroplayouts.client.ui.VHasDragMode#getDragMode()
     */
    public LayoutDragMode getDragMode() {
        return ((DDTabSheetState) ConnectorMap.get(client).getConnector(this)
                .getState()).getDragMode();
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
        Widget w = tabPanel.getWidget(getTabPosition(widget));
        return getDragMode() != LayoutDragMode.NONE
                && dragFilter.isDraggable(w);
    }

    /**
     * Creates a drop handler if one does not already exist and updates it from
     * the details received from the server.
     * 
     * @param childUidl
     *            The UIDL
     */
    public void updateDropHandler(UIDL childUidl) {
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

                @Override
                public ComponentConnector getConnector() {
                    return ConnectorMap.get(client).getConnector(
                            VDDTabSheet.this);
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
                    dragOver(drag);
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

                    deEmphasis();

                    // Update the details
                    updateDropDetails(drag);
                    return postDropHook(drag) && super.drop(drag);
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

                    if (drag.getElementOver() == newTab) {
                        return;
                    }

                    deEmphasis();

                    updateDropDetails(drag);

                    postOverHook(drag);

                    // Check if we are dropping on our self
                    if (VDDTabSheet.this.equals(drag.getTransferable().getData(
                            Constants.TRANSFERABLE_DETAIL_COMPONENT))) {
                        return;
                    }

                    // Validate the drop
                    validate(new VAcceptCallback() {
                        public void accepted(VDragEvent event) {
                            emphasis(event.getElementOver(), event);
                        }
                    }, drag);
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
                    deEmphasis();
                    updateDropDetails(drag);
                    postLeaveHook(drag);
                };
            };
        }

        // Update the rules
        dropHandler.updateAcceptRules(childUidl);
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
    protected void updateDropDetails(VDragEvent event) {
        Element element = event.getElementOver();

        if (tabBar.getElement().isOrHasChild(element)) {
            Widget w = Util.findWidget(element, null);

            if (w == tabBar) {
                // Ove3r the spacer

                // Add index
                event.getDropDetails().put(Constants.DROP_DETAIL_TO,
                        tabBar.getWidgetCount() - 1);

                // Add drop location
                event.getDropDetails().put(
                        Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION,
                        HorizontalDropLocation.RIGHT);

            } else {

                // Add index
                event.getDropDetails().put(Constants.DROP_DETAIL_TO,
                        getTabPosition(w));

                // Add drop location
                float ratio = ((DDTabSheetState) ConnectorMap.get(client)
                        .getConnector(VDDTabSheet.this).getState())
                        .getTabLeftRightDropRatio();
                HorizontalDropLocation location = VDragDropUtil
                        .getHorizontalDropLocation(element, Util
                                .getTouchOrMouseClientX(event
                                        .getCurrentGwtEvent()), ratio);
                event.getDropDetails().put(
                        Constants.DROP_DETAIL_HORIZONTAL_DROP_LOCATION,
                        location);
            }

            // Add mouse event details
            MouseEventDetails details = MouseEventDetailsBuilder
                    .buildMouseEventDetails(event.getCurrentGwtEvent(),
                            getElement());
            event.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT,
                    details.serialize());
        }
    }

    /**
     * Emphasisizes a container element
     * 
     * @param element
     */
    protected void emphasis(Element element, VDragEvent event) {

        boolean internalDrag = event.getTransferable().getDragSource() == this;

        if (tabBar.getElement().isOrHasChild(element)) {
            Widget w = Util.findWidget(element, null);

            if (w == tabBar && !internalDrag) {
                // Over spacer
                Element spacerContent = spacer.getChild(0).cast();
                spacerContent.appendChild(newTab);
                currentlyEmphasised = element;

            } else if (w instanceof VCaption) {
                // Over a tab
                VCaption tab = (VCaption) w;
                float ratio = ((DDTabSheetState) ConnectorMap.get(client)
                        .getConnector(VDDTabSheet.this).getState())
                        .getTabLeftRightDropRatio();
                HorizontalDropLocation location = VDragDropUtil
                        .getHorizontalDropLocation(element, Util
                                .getTouchOrMouseClientX(event
                                        .getCurrentGwtEvent()), ratio);

                if (location == HorizontalDropLocation.LEFT) {

                    int index = getTabPosition(w);

                    if (index == 0) {
                        currentlyEmphasised = tab.getElement();
                        currentlyEmphasised
                                .addClassName(CLASSNAME_NEW_TAB_LEFT);
                    } else {
                        Widget prevTab = tabBar.getWidget(index - 1);
                        currentlyEmphasised = prevTab.getElement();
                        currentlyEmphasised
                                .addClassName(CLASSNAME_NEW_TAB_RIGHT);
                    }

                } else if (location == HorizontalDropLocation.RIGHT) {
                    tab.getElement().addClassName(CLASSNAME_NEW_TAB_RIGHT);
                    currentlyEmphasised = tab.getElement();
                } else {
                    tab.getElement().addClassName(CLASSNAME_NEW_TAB_CENTER);
                    currentlyEmphasised = tab.getElement();
                }

            }
        }
    }

    /**
     * Removes any previous emphasis made by drag&drop
     */
    protected void deEmphasis() {
        if (currentlyEmphasised != null
                && tabBar.getElement().isOrHasChild(currentlyEmphasised)) {
            Widget w = Util.findWidget(currentlyEmphasised, null);

            currentlyEmphasised.removeClassName(CLASSNAME_NEW_TAB_LEFT);
            currentlyEmphasised.removeClassName(CLASSNAME_NEW_TAB_RIGHT);
            currentlyEmphasised.removeClassName(CLASSNAME_NEW_TAB_CENTER);

            if (w == tabBar) {
                // Over spacer
                Element spacerContent = spacer.getChild(0).cast();
                spacerContent.removeChild(newTab);
            }

            currentlyEmphasised = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer#getTabPosition
     * (com.google.gwt.user.client.ui.Widget)
     */
    public int getTabPosition(Widget tab) {
        int idx = -1;
        for (int i = 0; i < tabBar.getWidgetCount(); i++) {
            Widget w = tabBar.getWidget(i);
            if (w.getElement().isOrHasChild(tab.getElement())) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fi.jasoft.dragdroplayouts.client.ui.interfaces.VDDTabContainer#
     * getTabContentPosition(com.google.gwt.user.client.ui.Widget)
     */
    public int getTabContentPosition(Widget content) {
        return tabPanel.getWidgetIndex(content);
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
        return ddMouseHandler;
    }
}
