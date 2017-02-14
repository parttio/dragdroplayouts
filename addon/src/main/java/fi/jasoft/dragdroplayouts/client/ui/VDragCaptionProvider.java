package fi.jasoft.dragdroplayouts.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.Util;
import com.vaadin.client.ui.AbstractConnector;
import com.vaadin.client.ui.Icon;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DDLayoutState;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.DragAndDropAwareState;

public class VDragCaptionProvider {
    private final AbstractConnector root;

    public VDragCaptionProvider(AbstractConnector root) {
        this.root = root;
    }

    public Element getDragCaptionElement(Widget w) {
        ComponentConnector component = Util.findConnectorFor(w);
        DDLayoutState state = ((DragAndDropAwareState) root.getState()).getDragAndDropState();
        String dragCaptionText = state.dragCaptions.get(component);
        Document document = Document.get();

        Element dragCaptionImage = document.createElement("div");
        Element dragCaption = document.createElement("span");
        dragCaption.setInnerText(dragCaptionText);

        String dragIconId = state.dragIcons.get(component);
        if (dragIconId != null) {
            String resourceUrl = root.getResourceUrl(dragIconId);
            Icon icon = component.getConnection().getIcon(resourceUrl);
            dragCaptionImage.appendChild(icon.getElement());
        }

        dragCaptionImage.appendChild(dragCaption);

        return dragCaptionImage;
    }
}
