package fi.jasoft.dragdroplayouts.client.ui.interfaces;

import com.vaadin.client.ui.dd.VHasDropHandler;

import fi.jasoft.dragdroplayouts.client.ui.VDDAbstractDropHandler;

public interface VDDHasDropHandler<T extends VDDAbstractDropHandler> extends
	VHasDropHandler {

    public void setDropHandler(T drophandler);

    @Override
    public T getDropHandler();
}
