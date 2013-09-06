package fi.jasoft.dragdroplayouts.demo;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import com.vaadin.server.VaadinServlet;

@WebServlet(
    urlPatterns={"/*","/VAADIN/*"},
    initParams={
        @WebInitParam(name="ui", value="fi.jasoft.dragdroplayouts.demo.DemoUI"),
		@WebInitParam(name="widgetset", value="fi.jasoft.dragdroplayouts.demo.DemoWidgetSet")
    })
public class DemoServlet extends VaadinServlet { }
