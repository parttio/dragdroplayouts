package be.beme.schn.vaadin.dd;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.event.dd.acceptcriteria.SourceIsTarget;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

import java.util.ArrayList;

/**
 * @author  Dorian Messina.
 */
public class DefaultSwapGridLayoutDropHandler implements DropHandler {

    private final GridLayout layout;
    private int indexdest=0;
    private int xdest;
    private int ydest;
    private int indexsrc=0;
    private int xsrc;
    private int ysrc;
    private ArrayList<Component.Listener> listeners;

    public DefaultSwapGridLayoutDropHandler(final GridLayout layout) {
        this.layout = layout;
        listeners=new ArrayList<>();
    }

    @Override
    public AcceptCriterion getAcceptCriterion() {
        return new Not(SourceIsTarget.get());
    }

    @Override
    public void drop(final DragAndDropEvent dropEvent) {
        final Transferable transferable = dropEvent.getTransferable();
        final Component sourceComponent = transferable.getSourceComponent();

        if (sourceComponent instanceof SwapDragAndDropWrapper)
        {
            final TargetDetails dropTargetData = dropEvent.getTargetDetails();
            final DropTarget target = dropTargetData.getTarget();                                                       //DropTarget extends Component
            int[] results=findComponent(target);
            indexdest=results[0];
            xdest=results[1];
            ydest=results[2];

            results=findComponent(sourceComponent);
            indexsrc=results[0];
            xsrc=results[1];
            ysrc=results[2];

            layout.replaceComponent(layout.getComponent(xdest,ydest),sourceComponent);
        }

        fireDropEvent();
    }

    private int[] findComponent(Component target)
    {
        int index=0;
        for(int y=0;y<layout.getRows();y++)
        {
            for(int x=0;x<layout.getColumns();x++)
            {

                if(layout.getComponent(x,y)==target) //should I use .equals()?
                {
                    return new int[]{index,x,y};
                }
                index++;
            }
        }
        return null;
    }

    public void addDropListener(Component.Listener listener)
    {
        listeners.add(listener);
    }

    public void fireDropEvent()
    {
        for(Component.Listener l: listeners)
        {
            l.componentEvent(new SwapGridLayoutDropEvent(this.layout,new int[]{xdest,ydest,indexdest,xsrc,ysrc,indexsrc}));
        }
    }
}