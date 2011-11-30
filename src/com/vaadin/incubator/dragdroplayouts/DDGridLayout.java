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
package com.vaadin.incubator.dragdroplayouts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.TargetDetailsImpl;
import com.vaadin.incubator.dragdroplayouts.client.ui.LayoutDragMode;
import com.vaadin.incubator.dragdroplayouts.client.ui.VDDGridLayout;
import com.vaadin.incubator.dragdroplayouts.events.LayoutBoundTransferable;
import com.vaadin.incubator.dragdroplayouts.interfaces.DragFilter;
import com.vaadin.incubator.dragdroplayouts.interfaces.LayoutDragSource;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.terminal.gwt.client.MouseEventDetails;
import com.vaadin.terminal.gwt.client.ui.dd.HorizontalDropLocation;
import com.vaadin.terminal.gwt.client.ui.dd.VerticalDropLocation;
import com.vaadin.ui.ClientWidget;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

@ClientWidget(VDDGridLayout.class)
public class DDGridLayout extends GridLayout implements LayoutDragSource,
        DropTarget {

    private static final long serialVersionUID = -4586419114115623331L;

    public class GridLayoutTargetDetails extends TargetDetailsImpl {
       
        private static final long serialVersionUID = 967712285492743651L;

        private Component over;

        private int row = -1;

        private int column = -1;

        protected GridLayoutTargetDetails(Map<String, Object> rawDropData) {
            super(rawDropData, DDGridLayout.this);

            if (rawDropData.get("row") != null) {
                row = Integer.valueOf(rawDropData.get("row").toString());
            } else {
            	row = -1;
            }

            if (rawDropData.get("column") != null) {
                column = Integer.valueOf(rawDropData.get("column").toString());
            } else {
            	column = -1;
            }

            if (row != -1 && column != -1) {
                over = getComponent(column, row);
            }

            if (over == null) {
                over = DDGridLayout.this;
            }
        }

        /**
         * Returns the component over which the dragged component was dropped.
         * Returns NULL if no component was under the dragged component
         * 
         * @return
         */
        public Component getOverComponent() {
            return over;
        }

        /**
         * Over which row was the component dropped
         * 
         * @return The index of the row over which the component was dropped
         */
        public int getOverRow() {
            return row;
        }

        /**
         * Over which column was the component dropped
         * 
         * @return The index of the column over which the component was dropped
         */
        public int getOverColumn() {
            return column;
        }

        /**
         * Returns the horizontal location within the cell the component was
         * dropped
         * 
         * @return
         */
        public HorizontalDropLocation getHorizontalDropLocation() {
            return HorizontalDropLocation
                    .valueOf(getData("hdetail").toString());
        }

        /**
         * Returns the vertical location within the cell the component was
         * dropped
         * 
         * @return
         */
        public VerticalDropLocation getVerticalDropLocation() {
            return VerticalDropLocation.valueOf(getData("vdetail").toString());
        }

        /**
         * Was the dropped component dropped in an empty cell
         * 
         * @return
         */
        public boolean overEmptyCell() {
            return Boolean.valueOf(getData("overEmpty").toString());
        }

        /**
         * Some details about the mouse event
         * 
         * @return details about the actual event that caused the event details.
         *         Practically mouse move or mouse up.
         */
        public MouseEventDetails getMouseEvent() {
            return MouseEventDetails
                    .deSerialize((String) getData("mouseEvent"));
        }
    }

    /**
     * Contains the transferable details when dragging from a GridLayout.
     */
    public class GridLayoutTransferable extends LayoutBoundTransferable {

        private static final long serialVersionUID = 580459196290377421L;

        /**
         * Constructor
         * 
         * @param sourceComponent
         *            The source layout from where the component was dragged
         * @param rawVariables
         *            The drag details
         */
        public GridLayoutTransferable(Component sourceComponent,
                Map<String, Object> rawVariables) {
            super(sourceComponent, rawVariables);
        }

        /**
         * The row from where the component was dragged
         * 
         * @return The row index
         */
        public int getSourceRow() {
            return Integer.valueOf(getData("row").toString());
        }

        /**
         * The column from where the component was dragged
         * 
         * @return The column index
         */
        public int getSourceColumn() {
            return Integer.valueOf(getData("column").toString());
        }
    }

    private DropHandler dropHandler;

    private LayoutDragMode dragMode = LayoutDragMode.NONE;

    private float horizontalDropRatio = VDDGridLayout.DEFAULT_HORIZONTAL_RATIO;

    private float verticalDropRatio = VDDGridLayout.DEFAULT_VERTICAL_RATIO;

    // Are the iframes shimmed
    private boolean iframeShims = true;
    
    /**
     * A filter for dragging components.
     */
    private DragFilter dragFilter = DragFilter.ALL;

    /**
     * Constructor for grid of given size (number of cells). Note that grid's
     * final size depends on the items that are added into the grid. Grid grows
     * if you add components outside the grid's area.
     * 
     * @param columns
     *            Number of columns in the grid.
     * @param rows
     *            Number of rows in the grid.
     */
    public DDGridLayout(int columns, int rows) {
        super(columns, rows);
    }

    /**
     * Constructs an empty grid layout that is extended as needed.
     */
    public DDGridLayout() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        if (dropHandler != null) {
            dropHandler.getAcceptCriterion().paint(target);
        }

        // Drop ratios
        target.addAttribute("hDropRatio", horizontalDropRatio);
        target.addAttribute("vDropRatio", verticalDropRatio);

        // Drag mode
        target.addAttribute("dragMode", dragMode.ordinal());

        // Shims
        target.addAttribute("shims", iframeShims);
        
        if(getDragFilter() != null){
        	// Get components with dragging disabled
        	Map<Component, Boolean> dragmap = new HashMap<Component, Boolean>();
        	Iterator<Component> iter = getComponentIterator();
        	while(iter.hasNext()){
        		Component c = iter.next();
        		boolean draggable = getDragFilter().isDraggable(c);
        		dragmap.put(c, draggable);
        	}
        	target.addAttribute("dragmap", dragmap);
        }
    }

    /**
     * {@inheritDoc}
     */
    public DropHandler getDropHandler() {
        return dropHandler;
    }

    /**
     * {@inheritDoc}
     */
    public void setDropHandler(DropHandler dropHandler) {
        this.dropHandler = dropHandler;
        requestRepaint();
    }

    /**
     * {@inheritDoc}
     */
    public LayoutDragMode getDragMode() {
        return dragMode;
    }

    /**
     * {@inheritDoc}
     */
    public void setDragMode(LayoutDragMode mode) {
        dragMode = mode;
        requestRepaint();
    }

    /**
     * {@inheritDoc}
     */
    public TargetDetails translateDropTargetDetails(
            Map<String, Object> clientVariables) {
        return new GridLayoutTargetDetails(clientVariables);
    }

    /**
     * {@inheritDoc}
     */
    public Transferable getTransferable(Map<String, Object> rawVariables) {
        return new GridLayoutTransferable(this, rawVariables);
    }

    /**
     * Sets the ratio which determines how a cell is divided into drop zones.
     * The ratio is measured from the left and right borders. For example,
     * setting the ratio to 0.3 will divide the drop zone in three equal parts
     * (left,middle,right). Setting the ratio to 0.5 will disable dropping in
     * the middle and setting it to 0 will disable dropping at the sides.
     * 
     * @param ratio
     *            A ratio between 0 and 0.5. Default is 0.2
     */
    public void setComponentHorizontalDropRatio(float ratio) {
        if (ratio >= 0 && ratio <= 0.5) {
            horizontalDropRatio = ratio;
            requestRepaint();
        } else {
            throw new IllegalArgumentException(
                    "Ratio must be between 0 and 0.5");
        }
    }

    /**
     * Sets the ratio which determines how a cell is divided into drop zones.
     * The ratio is measured from the top and bottom borders. For example,
     * setting the ratio to 0.3 will divide the drop zone in three equal parts
     * (top,center,bottom). Setting the ratio to 0.5 will disable dropping in
     * the center and setting it to 0 will disable dropping at the sides.
     * 
     * @param ratio
     *            A ratio between 0 and 0.5. Default is 0.2
     */
    public void setComponentVerticalDropRatio(float ratio) {
        if (ratio >= 0 && ratio <= 0.5) {
            verticalDropRatio = ratio;
            requestRepaint();
        } else {
            throw new IllegalArgumentException(
                    "Ratio must be between 0 and 0.5");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setShim(boolean shim) {
        iframeShims = shim;
        requestRepaint();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isShimmed() {
        return iframeShims;
    }
    
    /**
     * {@inheritDoc}
     */
	public DragFilter getDragFilter() {
		return dragFilter;
	}

	/**
     * {@inheritDoc}
     */
	public void setDragFilter(DragFilter dragFilter) {
		if(this.dragFilter != dragFilter){
			this.dragFilter = dragFilter;
			requestRepaint();
		}
	}
}
