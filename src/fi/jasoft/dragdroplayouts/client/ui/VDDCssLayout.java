package fi.jasoft.dragdroplayouts.client.ui;

import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ui.VCssLayout;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;

import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;

public class VDDCssLayout extends VCssLayout implements VHasDragMode,
VHasDropHandler, DragStartListener{

	public boolean dragStart(Widget widget, LayoutDragMode mode) {
		// TODO Auto-generated method stub
		return false;
	}

	public VDropHandler getDropHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	public LayoutDragMode getDragMode() {
		// TODO Auto-generated method stub
		return null;
	}

}
