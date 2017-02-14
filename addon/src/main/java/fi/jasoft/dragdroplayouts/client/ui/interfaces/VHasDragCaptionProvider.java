package fi.jasoft.dragdroplayouts.client.ui.interfaces;

import fi.jasoft.dragdroplayouts.client.ui.VDragCaptionProvider;

public interface VHasDragCaptionProvider {
    void setDragCaptionProvider(VDragCaptionProvider dragCaption);
    VDragCaptionProvider getDragCaptionProvider();
}
