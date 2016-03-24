package ca.tylerwest.greenhouse.web;

import java.util.Map.Entry;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import ca.tylerwest.greenhouse.Greenhouse;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("greenhouse")
@Widgetset("ca.tylerwest.greenhouse.GreenhouseWidgetset")
public class GreenhouseUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                for (Entry<Object, Object> entry : Greenhouse.getInstance().getProperties().entrySet()) {
                    layout.addComponent(new Label(entry.getKey() + " : " + entry.getValue()));
                }
            }
        });
        layout.addComponent(button);

        System.out.println("----------------------UI INIT");
    }

    @WebServlet(urlPatterns = "/*", name = "GreenhouseUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = GreenhouseUI.class, productionMode = false)
    public static class GreenhouseUIServlet extends VaadinServlet {
    }
}
