package ca.tylerwest.greenhouse.web.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import ca.tylerwest.greenhouse.Greenhouse;
import ca.tylerwest.greenhouse.components.TemperatureHumiditySensor;
import ca.tylerwest.greenhouse.listeners.GPIOTaskListener;
import ca.tylerwest.greenhouse.web.GreenhouseUI;

@SuppressWarnings("serial")
public class GlobalComponent extends CustomComponent {
	
	private Label windowStateLabel;

	public GlobalComponent() {
		createComponent();
	}
	
	private void createComponent()
	{
		Panel panel = new Panel("Global Configuration");
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		panel.setContent(layout);
		
		layout.addComponent(createTemperatureHumidityPanel());
		layout.addComponent(createWindowMotorPanel());
		
		setCompositionRoot(panel);
	}
	
	private Panel createTemperatureHumidityPanel()
	{
		TemperatureHumiditySensor sensor = Greenhouse.getInstance().getTemperatureHumiditySensor();
		
		Panel result = new Panel(sensor.getID());
		
		VerticalLayout panelContent = new VerticalLayout();
		panelContent.setMargin(true);
		result.setContent(panelContent);
		
		panelContent.addComponent(new Label(String.format("Minimum temperature: %s", sensor.getMinimumTemperature())));
		panelContent.addComponent(new Label(String.format("Actual temperature: %s", sensor.getTemperature())));
		panelContent.addComponent(new Label(String.format("Maximum temperature: %s", sensor.getMaximumTemperature())));
		
		panelContent.addComponent(new Label(String.format("Minimum humidity: %s", sensor.getMinimumHumidity())));
		panelContent.addComponent(new Label(String.format("Actual humidity: %s", sensor.getHumidity())));
		panelContent.addComponent(new Label(String.format("Maximum humidity: %s", sensor.getMaximumHumidity())));
		
		return result;
	}
	
	private Panel createWindowMotorPanel()
	{
		Panel result = new Panel("Windows");
		
		VerticalLayout panelContent = new VerticalLayout();
		panelContent.setMargin(true);
		result.setContent(panelContent);
		
		windowStateLabel = new Label();
		panelContent.addComponent(windowStateLabel);
		updateStateLabel();
		
		if (GreenhouseUI.get().getAccessControl().isUserSignedIn())
		{
			Button raiseWindowsButton = new Button("Raise Windows", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Greenhouse.getInstance().getWindowController().raiseWindows(new WindowStateListener());
				}
			});
			
			Button lowerWindowsButton = new Button("Lower Windows", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Greenhouse.getInstance().getWindowController().lowerWindows(new WindowStateListener());
				}
			});
			
			HorizontalLayout buttonLayout = new HorizontalLayout();
			buttonLayout.setMargin(true);
			panelContent.addComponent(buttonLayout);
			
			buttonLayout.addComponent(raiseWindowsButton);
			buttonLayout.addComponent(lowerWindowsButton);
		}
		
		return result;
	}
	
	private void updateStateLabel()
	{
		windowStateLabel.setValue(String.format("Window state: %s", Greenhouse.getInstance().getWindowController().getState()));
	}
	
	private class WindowStateListener implements GPIOTaskListener
	{

		@Override
		public void onTaskStarted() {
			updateStateLabel();
		}

		@Override
		public void onTaskCompleted() {
			updateStateLabel();
		}
	}
}
