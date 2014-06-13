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
package fi.jasoft.dragdroplayouts.client.ui.gridlayout;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.gridlayout.GridLayoutConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;

@Connect(DDGridLayout.class)
public class DDGridLayoutConnector extends GridLayoutConnector implements
	Paintable, VHasDragFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    public VDDGridLayout getWidget() {
	return (VDDGridLayout) super.getWidget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DDGridLayoutState getState() {
	return (DDGridLayoutState) super.getState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
	super.init();
	VDragDropUtil.listenToStateChangeEvents(this, getWidget());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
	super.updateFromUIDL(uidl, client);
	if (isRealUpdate(uidl) && !uidl.hasAttribute("hidden")) {
	    UIDL acceptCrit = uidl.getChildByTagName("-ac");
	    if (acceptCrit == null) {
		getWidget().setDropHandler(null);
	    } else {
		if (getWidget().getDropHandler() == null) {
		    getWidget().setDropHandler(
			    new VDDGridLayoutDropHandler(this));
		}
		getWidget().getDropHandler().updateAcceptRules(acceptCrit);
	    }
	}
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
