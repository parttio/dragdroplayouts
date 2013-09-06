/*
 * Copyright 2013 John Ahlroos
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
package fi.jasoft.dragdroplayouts.client.ui.formlayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.formlayout.FormLayoutConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDFormLayout;
import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.Constants;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

@Connect(DDFormLayout.class)
public class DDFormLayoutConnector extends FormLayoutConnector implements
        Paintable, VHasDragFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    public VDDFormLayout getWidget() {
        return (VDDFormLayout) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDFormLayoutState getState() {
        return (DDFormLayoutState) super.getState();
    }

    /**
     * {@inheritDoc}
     */
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (isRealUpdate(uidl) && !uidl.hasAttribute("hidden")) {
            UIDL acceptCrit = uidl.getChildByTagName("-ac");
            if (acceptCrit == null) {
                getWidget().setDropHandler(null);
            } else {
                if (getWidget().getDropHandler() == null) {
                    getWidget().setDropHandler(
                            new VDDFormLayoutDropHandler(getWidget(), client));
                }
                getWidget().getDropHandler().updateAcceptRules(acceptCrit);
            }
        }

        // Handles changes in dropHandler
        handleDragModeUpdate(uidl);

        // Handle drop ratio settings
        handleCellDropRatioUpdate(uidl);

        /*
         * Always check for iframe covers so new added/removed components get
         * covered
         */
        IframeCoverUtility iframes = getWidget().getIframeCoverUtility();
        iframes.setIframeCoversEnabled(iframes.isIframeCoversEnabled(),
                getWidget().getElement(), getState().dd.dragMode);
    }

    /**
     * Handles drag mode changes recieved from the server
     * 
     * @param uidl
     *            The UIDL
     */
    private void handleDragModeUpdate(UIDL uidl) {
        if (uidl.hasAttribute(Constants.DRAGMODE_ATTRIBUTE)) {
            LayoutDragMode[] modes = LayoutDragMode.values();
            getState().dd.dragMode = 
                    modes[uidl.getIntAttribute(Constants.DRAGMODE_ATTRIBUTE)];
            getWidget().getMouseHandler().updateDragMode(
                    getState().dd.dragMode);
            getWidget()
                    .getIframeCoverUtility()
                    .setIframeCoversEnabled(
                            uidl.getBooleanAttribute(IframeCoverUtility.SHIM_ATTRIBUTE),
                            getWidget().getElement(), getState().dd.dragMode);
        }
    }

    /**
     * Handles updates the the hoover zones of the cell which specifies at which
     * position a component is dropped over a cell
     * 
     * @param uidl
     *            The UIDL
     */
    private void handleCellDropRatioUpdate(UIDL uidl) {
        if (uidl.hasAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO)) {
            getState()
                    .cellTopBottomDropRatio = 
                            uidl.getFloatAttribute(Constants.ATTRIBUTE_VERTICAL_DROP_RATIO);
        }
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        getWidget().setDragFilter(new VDragFilter(getState().dd));
    }

    @Override
    public VDragFilter getDragFilter() {
        return getWidget().getDragFilter();
    }

    @Override
    public void setDragFilter(VDragFilter filter) {
        getWidget().setDragFilter(filter);
    }

}
