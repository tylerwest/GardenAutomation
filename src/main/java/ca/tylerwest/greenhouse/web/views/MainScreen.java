package ca.tylerwest.greenhouse.web.views;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import ca.tylerwest.greenhouse.web.GreenhouseUI;
import ca.tylerwest.greenhouse.web.components.GlobalComponent;
import ca.tylerwest.greenhouse.web.components.ZoneGridComponent;

@SuppressWarnings("serial")
public class MainScreen extends HorizontalLayout {

	public MainScreen() {
		buildMenu();
		buildContent();
	}
	
	private void buildMenu()
	{
		VerticalLayout menu = new VerticalLayout();
		menu.setMargin(true);
		addComponent(menu);
		
		Label title = new Label("Greenhouse");
		title.addStyleName("h1");
		menu.addComponent(title);
		
		Label usernameLabel = new Label();
		Button button = new Button();
		if (GreenhouseUI.get().getAccessControl().isUserSignedIn())
		{
			usernameLabel.setCaption("Logged in as " + GreenhouseUI.get().getAccessControl().getPrincipalName());
			button.setCaption("Logout");
			button.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					VaadinSession.getCurrent().getSession().invalidate();
	                Page.getCurrent().reload();
				}
			});
		}
		else
		{
			usernameLabel.setCaption("Viewing as guest");
			button.setCaption("Login");
			button.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Window loginWindow = new LoginScreen(new LoginScreen.LoginListener() {
						
						@Override
						public void loginSuccessful() {
							Page.getCurrent().reload();
						}
					});
					UI.getCurrent().addWindow(loginWindow);
				}
			});
		}
		
		menu.addComponent(usernameLabel);
		menu.addComponent(button);
	}
	
	private void buildContent()
	{
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		addComponent(content);
		
		content.addComponent(new GlobalComponent());
		content.addComponent(new ZoneGridComponent());
		
	}
	
}
