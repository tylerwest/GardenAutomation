package ca.tylerwest.greenhouse.web.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import ca.tylerwest.greenhouse.controllers.Zone;
import ca.tylerwest.greenhouse.listeners.GPIOTaskListener;
import ca.tylerwest.greenhouse.web.GreenhouseUI;

@SuppressWarnings("serial")
public class ZoneComponent extends CustomComponent {

	private Zone zone;
	private Label valveStateLabel;
	
	public ZoneComponent(Zone zone) {
		this.zone = zone;
		
		createComponent();
	}
	
	private void createComponent()
	{
		Panel panel = new Panel(zone.getID());
		VerticalLayout panelContent = new VerticalLayout();
		panelContent.setMargin(true);
		panel.setContent(panelContent);
		
		Label description = new Label(zone.getDescription());
		panelContent.addComponent(description);
		
		Label minumumMoistureLevel = new Label(String.format("Minimum moisture level: %s", zone.getMinimumMoistureLevel()));
		panelContent.addComponent(minumumMoistureLevel);
		
		Label actualMoistureLevel = new Label(String.format("Actual moisture level: %s", zone.getSoilMoistureSensor().getSoilMoistureLevel()));
		panelContent.addComponent(actualMoistureLevel);
		
		Label maxumumMoistureLevel = new Label(String.format("Maximum moisture level: %s", zone.getMaximumMoistureLevel()));
		panelContent.addComponent(maxumumMoistureLevel);
		
		Label activeTime = new Label(String.format("Valve active time (seconds): %s", zone.getWaterActiveTimeSeconds()));
		panelContent.addComponent(activeTime);
		
		valveStateLabel = new Label();
		panelContent.addComponent(valveStateLabel);
		updateStateLabel();
		
		if (GreenhouseUI.get().getAccessControl().isUserSignedIn())
		{
			Button openValveButton = new Button("Open Valve", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					zone.getSolenoidValve().open(new ValveStateListener());
				}
			});
			
			Button closeValveButton = new Button("Close Valve", new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					zone.getSolenoidValve().close(new ValveStateListener());
				}
			});
			
			HorizontalLayout buttonLayout = new HorizontalLayout();
			buttonLayout.setMargin(true);
			panelContent.addComponent(buttonLayout);
			
			buttonLayout.addComponent(openValveButton);
			buttonLayout.addComponent(closeValveButton);
		}
		
		setCompositionRoot(panel);
	}
	
	private void updateStateLabel()
	{
		valveStateLabel.setValue(String.format("Valve status is: %s", zone.getSolenoidValve().getState().name()));
	}
	
	private class ValveStateListener implements GPIOTaskListener
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
