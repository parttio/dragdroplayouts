package fi.jasoft.dragdroplayouts;

import com.vaadin.server.Resource;

public class DragCaption {
    protected String caption;
    protected Resource icon;

    public DragCaption(String caption, Resource icon) {
        this.caption = caption;
        this.icon = icon;
    }

    public Resource getIcon() {
        return icon;
    }

    public String getCaption() {
        return caption;
    }

    public void setIcon(Resource icon) {
        this.icon = icon;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
