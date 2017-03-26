package be.beme.schn.vaadin.dd;


import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.GridLayout;

/**
 * A GridLayout that handles the drag and drop by the way of
 * swapping to child, but does not handle empty cells. For
 * this purpose use DDDGridLayout
 *
 * @version 1.0
 * @author Dorian Messina.
 */
public class DDSwapGridLayout extends CustomComponent {
    private final GridLayout layout;
    private final SwapGridLayoutDropHandler dropHandler;

    public DDSwapGridLayout() {
        layout = new GridLayout();
        dropHandler = new SwapGridLayoutDropHandler(layout);

        final DragAndDropWrapper pane = new DragAndDropWrapper(layout);
        setCompositionRoot(pane);
    }

    /**
     * @param component an item to add to the DDSwapGridLayout
     * **/
    public void addComponent(final Component component) {
        final SwapDragAndDropWrapper wrapper = new SwapDragAndDropWrapper(component,
                dropHandler);

        layout.addComponent(wrapper);
    }


    public void addDropListener(Listener listener)
    {

       dropHandler.addDropListener(listener);

    }

    /**
     * @return find a abscissa by index. Useful when you work somewhere with
     * a List
     * **/
    public int findX(int index)
    {
        return index%layout.getColumns();
    }
    /**
     * @return find a ordinate by index. Useful when you work somewhere with
     * a List
     * **/
    public int findY(int index, int x)
    {
        return (index-x)/layout.getRows();
    }

    /**
     * @return the GriLayout from the drag and drop system. Be careful don't use
     * getGridLayout().getComponent(x,y), but instead {@link #getUnwrappedComponent(int x, int y)}.
     */
    public GridLayout getGridLayout()
    {
        return this.layout;
    }

    /**
     * @return an item put by you in the GridLayout cleaned from drag and drop stuff
     * **/
    public Component getUnwrappedComponent(int x, int y){
        SwapDragAndDropWrapper wrappedDrag=(SwapDragAndDropWrapper) this.layout.getComponent(x,y);
        return wrappedDrag.getCompositionRoot();
    }

    //------------------------------------------------------------------\\

    /**
     * The wrapper to use with DDSzapGridLayout must be this one
     * because it's used in {@link #getUnwrappedComponent(int x, int y)}
     * **/
    public class SwapDragAndDropWrapper extends DragAndDropWrapper {

        private final DropHandler dropHandler;

        public SwapDragAndDropWrapper(final Component content,
                                      final DropHandler dropHandler) {
            super(content);
            this.dropHandler = dropHandler;
            setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);

        }

        /**
         * The purpose of this method is to make public the one from supper class
         * DragAndDropWrapper. That way, we can call it in DDSwapGridLayout
         * **/
        public Component getCompositionRoot()
        {
            return super.getCompositionRoot();
        }
    }

}