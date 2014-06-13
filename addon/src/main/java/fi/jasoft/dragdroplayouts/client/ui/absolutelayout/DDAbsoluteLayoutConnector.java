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
package fi.jasoft.dragdroplayouts.client.ui.absolutelayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.absolutelayout.AbsoluteLayoutConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDAbsoluteLayout;
import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;

@Connect(DDAbsoluteLayout.class)
public class DDAbsoluteLayoutConnector extends AbsoluteLayoutConnector
	implements Paintable, VHasDragFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    public VDDAbsoluteLayout getWidget() {
	return (VDDAbsoluteLayout) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDAbsoluteLayoutState getState() {
	return (DDAbsoluteLayoutState) super.getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
	super.init();
	VDragDropUtil.listenToStateChangeEvents(this, getWidget());
    }

    /**
     * {@inheritDoc}
     * 
     * TODO Remove this when drag & drop is done properly in core
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
	VDragDropUtil.updateDropHandlerFromUIDL(uidl, this,
		new VDDAbsoluteLayoutDropHandler(this));
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
	return getWidget().getDragMode();
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
