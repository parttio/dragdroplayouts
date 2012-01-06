/*
 * Copyright 2011 John Ahlroos
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
package fi.jasoft.dragdroplayouts.client.ui;

import java.util.Iterator;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VCssLayout;
import com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VDragEvent;
import com.vaadin.terminal.gwt.client.ui.dd.VDropHandler;
import com.vaadin.terminal.gwt.client.ui.dd.VHasDropHandler;

import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.client.ui.VLayoutDragDropMouseHandler.DragStartListener;
import fi.jasoft.dragdroplayouts.client.ui.interfaces.VHasDragMode;
import fi.jasoft.dragdroplayouts.client.ui.util.IframeCoverUtility;

/**
 * Client side implementation for {@link DDCssLayout}
 * 
 * @author John Ahlroos / www.jasoft.fi
 *
 */
public class VDDCssLayout extends VCssLayout implements VHasDragMode,
VHasDropHandler, DragStartListener{

	 private LayoutDragMode dragMode = LayoutDragMode.NONE;
	 
	 private VDragFilter dragFilter = new VDragFilter();
	 
	 private VAbstractDropHandler dropHandler;
	 
	 private VLayoutDragDropMouseHandler ddHandler = new VLayoutDragDropMouseHandler(
	            this, dragMode);
	 
	 protected ApplicationConnection client;
	 
	 protected boolean iframeCoversEnabled = false;
	 
	 private IframeCoverUtility iframeCoverUtility = new IframeCoverUtility();
	 
	 private HandlerRegistration reg;
	 
	 /**
	  * Default constructor
	  */
	 public VDDCssLayout() {
		 super();
	     ddHandler.addDragStartListener(this);
	}
	
	/**
     * Can be used to listen to drag start events, must return true for the drag
     * to commence. Return false to interrupt the drag:
     */
	public boolean dragStart(Widget widget, LayoutDragMode mode) {
		return dragMode != LayoutDragMode.NONE && dragFilter.isDraggable(widget);
	}

	 /**
     * Returns the drop handler which handles the drop events
     */
	public VDropHandler getDropHandler() {
		return dropHandler;
	}

	/**
     * Returns the drag mode
     * 
     * @return
     */
	public LayoutDragMode getDragMode() {
		 return dragMode;
	}
	
	 /**
     * Updates the drop handler. Creates a drop handler if it does not exist.
     * 
     * @param childUidl
     *            The child UIDL containing the rules
     */
    protected void updateDropHandler(UIDL childUidl) {
    	  if (dropHandler == null) {
              dropHandler = new VAbstractDropHandler() {
            	  
        	  /*
        	   * (non-Javadoc)
        	   * @see com.vaadin.terminal.gwt.client.ui.dd.VDropHandler#getApplicationConnection()
        	   */
				public ApplicationConnection getApplicationConnection() {
					return client;
				}

				/*
				 * (non-Javadoc)
				 * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler#dragAccepted(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
				 */
				@Override
				protected void dragAccepted(VDragEvent drag) {
					// Intentionally left empty
				}

				/*
				 * (non-Javadoc)
				 * @see com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler#getPaintable()
				 */
				@Override
				public Paintable getPaintable() {
					return VDDCssLayout.this;
				}
				
				 /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #drop(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public boolean drop(VDragEvent drag) {
                    if (super.drop(drag)) {
                        updateDragDetails(drag);
                        return postDropHook(drag);
                    }
                    return false;
                };
                
                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragEnter(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public void dragEnter(VDragEvent drag) {
                    super.dragEnter(drag);
                    postEnterHook(drag);
                };
                
                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragLeave(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public void dragLeave(VDragEvent drag) {
                    super.dragLeave(drag);
                    postLeaveHook(drag);
                };
                
                /*
                 * (non-Javadoc)
                 * 
                 * @see
                 * com.vaadin.terminal.gwt.client.ui.dd.VAbstractDropHandler
                 * #dragOver(com.vaadin.terminal.gwt.client.ui.dd.VDragEvent)
                 */
                @Override
                public void dragOver(VDragEvent drag) {
                 
                    // Update drop details with the location so we can
                    // validate it
                    updateDragDetails(drag);
                    postOverHook(drag);
                };
            	  
              };
    	  }
    	  dropHandler.updateAcceptRules(childUidl);
    }
    
    /*
     * (non-Javadoc)
     * @see com.vaadin.terminal.gwt.client.ui.VCssLayout#updateFromUIDL(com.vaadin.terminal.gwt.client.UIDL, com.vaadin.terminal.gwt.client.ApplicationConnection)
     */
    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
    	
    	// Drag mode
    	handleDragModeUpdate(uidl);

    	// Drop handlers
        UIDL c = null;
        for (final Iterator<Object> it = uidl.getChildIterator(); it.hasNext();) {
            c = (UIDL) it.next();
            if (c.getTag().equals("-ac")) {
                updateDropHandler(c);
                break;
            }
        }
    	
        UIDL modifiedUIDL = VDragDropUtil.removeDragDropCriteraFromUIDL(uidl);
        super.updateFromUIDL(modifiedUIDL, client);
        
        /*
         * Always check for iframe covers so new added/removed components get
         * covered
         */
        iframeCoverUtility.setIframeCoversEnabled(iframeCoversEnabled, getElement());
       
    	// Drag filters
    	dragFilter.update(modifiedUIDL, client);
    }
    
    /**
     * Handle updates to the dragmode
     * 
     * @param uidl
     * 		The recieved UIDL
     */
    private void handleDragModeUpdate(UIDL uidl) {
        if (uidl.hasAttribute(VHasDragMode.DRAGMODE_ATTRIBUTE)) {
            LayoutDragMode[] modes = LayoutDragMode.values();
            dragMode = modes[uidl.getIntAttribute(VHasDragMode.DRAGMODE_ATTRIBUTE)];
            ddHandler.updateDragMode(dragMode);
            if (reg == null && dragMode != LayoutDragMode.NONE) {

                // Cover iframes if necessery
                iframeCoversEnabled = uidl.getBooleanAttribute(IframeCoverUtility.SHIM_ATTRIBUTE);

                // Listen to mouse down events
                reg = addDomHandler(ddHandler, MouseDownEvent.getType());

            } else if (dragMode == LayoutDragMode.NONE && reg != null) {

                // Remove iframe covers
                iframeCoversEnabled = false;

                // Remove mouse down handler
                reg.removeHandler();
                reg = null;
            }
        }
    }
    
    /*
     * (non-Javadoc)
     * @see com.google.gwt.user.client.ui.Widget#onUnload()
     */
    @Override
    protected void onUnload() {
        super.onUnload();
        if (reg != null) {
            reg.removeHandler();
            reg = null;
        }
        iframeCoverUtility.setIframeCoversEnabled(false, getElement());
    }
    
    /**
     * A hook for extended components to post process the the drop before it is
     * sent to the server. Useful if you don't want to override the whole drop
     * handler.
     */
    protected boolean postDropHook(VDragEvent drag) {
        // Extended classes can add content here...
        return true;
    }

    /**
     * A hook for extended components to post process the the enter event.
     * Useful if you don't want to override the whole drophandler.
     */
    protected void postEnterHook(VDragEvent drag) {
        // Extended classes can add content here...
    }

    /**
     * A hook for extended components to post process the the leave event.
     * Useful if you don't want to override the whole drophandler.
     */
    protected void postLeaveHook(VDragEvent drag) {
        // Extended classes can add content here...
    }

    /**
     * A hook for extended components to post process the the over event. Useful
     * if you don't want to override the whole drophandler.
     */
    protected void postOverHook(VDragEvent drag) {
        // Extended classes can add content here...
    }
    
    /**
     * Updates the drag details while a component is dragged
     * 
     * @param drag
     *            The drag event to update the details from
     */
    protected void updateDragDetails(VDragEvent drag) {
        
    	//TODO
    	
        // Add mouse event details
        MouseEventDetails details = new MouseEventDetails(
                drag.getCurrentGwtEvent(), getElement());
        drag.getDropDetails().put(Constants.DROP_DETAIL_MOUSE_EVENT, details.serialize());
    }
}
