package fi.jasoft.dragdroplayouts.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;

@Theme("dragdrop")
@Widgetset("fi.jasoft.dragdroplayouts.demo.DemoWidgetSet")
public class DragDropUI extends UI {

    private TabSheet tabs;

    private Label code;

    @Override
    protected void init(VaadinRequest request) {

        VerticalSplitPanel content = new VerticalSplitPanel();
        content.setSizeFull();

        content.addComponent(new DragdropAbsoluteLayoutDemo());

        setContent(content);

        if (true)
            return;

        tabs = new TabSheet();
        tabs.setSizeFull();
        tabs.setImmediate(true);

        tabs.addComponent(new DragdropAbsoluteLayoutDemo());
        tabs.addComponent(new DragdropVerticalLayoutDemo());
        tabs.addComponent(new DragdropHorizontalLayoutDemo());
        tabs.addComponent(new DragdropGridLayoutDemo());
        tabs.addComponent(new DragdropCssLayoutDemo());
        tabs.addComponent(new DragdropFormLayoutDemo());

        tabs.addComponent(new DragdropLayoutDraggingDemo());
        tabs.addComponent(new DragdropHorizontalSplitPanelDemo());
        tabs.addComponent(new DragdropVerticalSplitPanelDemo());
        tabs.addComponent(new DragdropTabsheetDemo());
        tabs.addComponent(new DragdropAccordionDemo());

        tabs.addComponent(new DragdropDragFilterDemo());
        tabs.addComponent(new DragdropCaptionModeDemo());

        tabs.addListener(new TabSheet.SelectedTabChangeListener() {
            public void selectedTabChange(SelectedTabChangeEvent event) {
                try {
                    tabChanged(event.getTabSheet().getSelectedTab());
                } catch (IOException e) {
                    code.setValue("No source code available.");
                    e.printStackTrace();
                }
            }
        });

        content.addComponent(tabs);

        code = new Label("");
        try {
            tabChanged(tabs.getSelectedTab());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Panel codePanel = new Panel();
        codePanel.setSizeFull();
        codePanel.setContent(code);
        content.addComponent(codePanel);

        setContent(content);
    }

    private void tabChanged(Component tab) throws IOException {
        String path = tab.getClass().getCanonicalName().replaceAll("\\.", "/")
                + ".java";

        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            code.setValue("No source code available.");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        StringBuilder codelines = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            if (line.startsWith("import")) {
                // Remove imports
            } else if (line.startsWith("package")) {
                // Remove package declaration
            } else {
                codelines.append(line);
                codelines.append("\n");
            }

            line = reader.readLine();
        }

        reader.close();

        String code = codelines.toString();

        code = Pattern
                .compile("public String getCodePath.*?}",
                        Pattern.MULTILINE | Pattern.DOTALL).matcher(code)
                .replaceAll("");

        this.code.setValue(code.trim());
    }
}
