/*
 * Copyright 2015 John Ahlroos
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
package fi.jasoft.dragdroplayouts.client.ui.horizontalsplitpanel;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.splitpanel.HorizontalSplitPanelConnector;
import com.vaadin.shared.ui.Connect;
import fi.jasoft.dragdroplayouts.DDHorizontalSplitPanel;
import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.VGrabFilter;
import fi.jasoft.dragdroplayouts.client.ui.VDragCaptionProvider;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragCaptionProvider;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasGrabFilter;
import fi.jasoft.dragdroplayouts.client.ui.util.HTML5Support;

@Connect(DDHorizontalSplitPanel.class)
public class DDHorizontalSplitPanelConnector extends
        HorizontalSplitPanelConnector implements Paintable, VHasDragFilter,
        VHasGrabFilter, VHasDragCaptionProvider {

    private HTML5Support html5Support;

    @Override
    protected void init() {
        super.init();
        VDragDropUtil.listenToStateChangeEvents(this, getWidget());
    }

    @Override
    public void setDragCaptionProvider(VDragCaptionProvider dragCaption) {
        getWidget().setDragCaptionProvider(dragCaption);
    }

    @Override
    public VDragCaptionProvider getDragCaptionProvider() {
        return getWidget().getDragCaptionProvider();
    }

    @Override
    public VGrabFilter getGrabFilter() {
        return getWidget().getGrabFilter();
    }

    @Override
    public void setGrabFilter(VGrabFilter grabFilter) {
        getWidget().setGrabFilter(grabFilter);
    }

    @Override
    public VDDHorizontalSplitPanel getWidget() {
        return (VDDHorizontalSplitPanel) super.getWidget();
    }

    @Override
    public DDHorizontalSplitPanelState getState() {
        return (DDHorizontalSplitPanelState) super.getState();
    }

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        VDragDropUtil.updateDropHandlerFromUIDL(uidl, this, new VDDHorizontalSplitPanelDropHandler(this));
        if (html5Support != null) {
            html5Support.disable();
            html5Support = null;
        }
        VDDHorizontalSplitPanelDropHandler dropHandler = getWidget().getDropHandler();
        if (dropHandler != null) {
            html5Support = HTML5Support.enable(this, dropHandler);
        }
    }

    @Override
    public void onUnregister() {
        if (html5Support != null) {
            html5Support.disable();
            html5Support = null;
        }
        super.onUnregister();
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
