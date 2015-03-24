package ca.tylerwest.greenhouse.web;

import ca.tylerwest.greenhouse.Greenhouse;
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
import java.util.Map.Entry;

/**
 *
 */
@Theme("mytheme")
@Widgetset("ca.tylerwest.greenhouse.MyAppWidgetset")
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
