package fi.jasoft.dragdroplayouts.client.ui.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.DragEnterEvent;
import com.google.gwt.event.dom.client.DragEnterHandler;
import com.google.gwt.event.dom.client.DragLeaveEvent;
import com.google.gwt.event.dom.client.DragLeaveHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ui.dd.VDragAndDropManager;
import com.vaadin.client.ui.dd.VDragEvent;
import com.vaadin.client.ui.dd.VTransferable;

import fi.jasoft.dragdroplayouts.client.ui.VDDAbstractDropHandler;

public class HTML5Support {

  private VDragEvent vaadinDragEvent;

  private final List<HandlerRegistration> handlers = new ArrayList<HandlerRegistration>();

  public static final HTML5Support enable(final ComponentConnector connector,
      final VDDAbstractDropHandler<? extends Widget> handler) {
    if (handler == null) {
      return null;
    }

    Widget w = connector.getWidget();
    final HTML5Support support = new HTML5Support();

    support.handlers.add(w.addDomHandler(new DragEnterHandler() {

      @Override
      public void onDragEnter(DragEnterEvent event) {
        event.preventDefault();
        event.stopPropagation();

        VTransferable transferable = new VTransferable();
        transferable.setDragSource(connector);
        support.vaadinDragEvent =
            VDragAndDropManager.get().startDrag(transferable, event.getNativeEvent(), false);

        VDragAndDropManager.get().setCurrentDropHandler(handler);

      }
    }, DragEnterEvent.getType()));

    support.handlers.add(w.addDomHandler(new DragLeaveHandler() {

      @Override
      public void onDragLeave(DragLeaveEvent event) {
        event.preventDefault();
        event.stopPropagation();

        support.vaadinDragEvent.setCurrentGwtEvent(event.getNativeEvent());
        handler.dragLeave(support.vaadinDragEvent);

      }
    }, DragLeaveEvent.getType()));

    support.handlers.add(w.addDomHandler(new DragOverHandler() {

      @Override
      public void onDragOver(DragOverEvent event) {
        event.preventDefault();
        event.stopPropagation();

        support.vaadinDragEvent.setCurrentGwtEvent(event.getNativeEvent());
        handler.dragOver(support.vaadinDragEvent);

      }
    }, DragOverEvent.getType()));

    support.handlers.add(w.addDomHandler(new DropHandler() {

      @Override
      public void onDrop(DropEvent event) {
        event.preventDefault();
        event.stopPropagation();

        support.vaadinDragEvent.setCurrentGwtEvent(event.getNativeEvent());

        // FIXME only text currently supported
        String data = event.getData("text/plain");
        support.vaadinDragEvent.getTransferable().setData("html5Data", data);

        if (handler.drop(support.vaadinDragEvent)) {
          VDragAndDropManager.get().endDrag();
        } else {
          VDragAndDropManager.get().interruptDrag();
        }
      }
    }, DropEvent.getType()));

    return support;
  }

  public void disable() {
    for (HandlerRegistration handlerRegistration : handlers) {
      handlerRegistration.removeHandler();
    }
    handlers.clear();
  }
}
