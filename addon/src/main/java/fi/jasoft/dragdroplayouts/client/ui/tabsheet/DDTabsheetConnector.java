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
package fi.jasoft.dragdroplayouts.client.ui.tabsheet;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.Paintable;
import com.vaadin.client.UIDL;
import com.vaadin.client.ui.tabsheet.TabsheetConnector;
import com.vaadin.shared.ui.Connect;

import fi.jasoft.dragdroplayouts.DDTabSheet;
import fi.jasoft.dragdroplayouts.client.VDragFilter;
import fi.jasoft.dragdroplayouts.client.ui.VDragDropUtil;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragFilter;

@Connect(DDTabSheet.class)
public class DDTabsheetConnector extends TabsheetConnector implements
	Paintable, VHasDragFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void init() {
	super.init();
	VDragDropUtil.listenToStateChangeEvents(this, getWidget());
    }

    @Override
    public VDDTabSheet getWidget() {
	return (VDDTabSheet) super.getWidget();
    }

    @Override
    public DDTabSheetState getState() {
	return (DDTabSheetState) super.getState();
    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
	if (isRealUpdate(uidl) && !uidl.hasAttribute("hidden")) {
	    UIDL acceptCrit = uidl.getChildByTagName("-ac");
	    if (acceptCrit == null) {
		getWidget().setDropHandler(null);
	    } else {
		if (getWidget().getDropHandler() == null) {
		    getWidget()
			    .setDropHandler(new VDDTabsheetDropHandler(this));
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
