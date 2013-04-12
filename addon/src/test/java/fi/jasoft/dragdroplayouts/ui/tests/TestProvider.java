package fi.jasoft.dragdroplayouts.ui.tests;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class TestProvider extends UIProvider {

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		
		String testClass = event.getRequest().getParameter("test");
		if(testClass != null){
			try {
				return (Class<? extends UI>) Class.forName(testClass);
			} catch (ClassNotFoundException e) {
				// Ignore
			}
		}
		
		return NoTestFoundUI.class;
	}
	
	public static class NoTestFoundUI extends UI {

		@Override
		protected void init(VaadinRequest request) {
			setContent(new Label("No test with that name could be found."));
		}
		
	}

}
