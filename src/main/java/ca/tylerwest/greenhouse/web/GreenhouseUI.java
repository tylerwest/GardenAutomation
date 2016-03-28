package ca.tylerwest.greenhouse.web;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import ca.tylerwest.greenhouse.web.auth.AccessControl;
import ca.tylerwest.greenhouse.web.auth.GreenhouseAccessControl;
import ca.tylerwest.greenhouse.web.views.MainScreen;

@SuppressWarnings("serial")
@Theme("greenhouse")
@Widgetset("ca.tylerwest.greenhouse.GreenhouseWidgetset")
public class GreenhouseUI extends UI {
	
	private AccessControl accessControl = new GreenhouseAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("Greenhouse");
        setContent(new MainScreen());
        System.out.println("----------------------UI INIT");
    }
    
    public static GreenhouseUI get() {
        return (GreenhouseUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "GreenhouseUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = GreenhouseUI.class, productionMode = false)
    public static class GreenhouseUIServlet extends VaadinServlet {
    }
}
