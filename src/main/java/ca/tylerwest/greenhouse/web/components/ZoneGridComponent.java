package ca.tylerwest.greenhouse.web.components;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;

import ca.tylerwest.greenhouse.Greenhouse;
import ca.tylerwest.greenhouse.controllers.Zone;

@SuppressWarnings("serial")
public class ZoneGridComponent extends CustomComponent {
	
	public ZoneGridComponent() {
		createComponent();
	}
	
	private void createComponent()
	{
		Panel panel = new Panel("Greenhouse Zones");
		
		GridLayout zoneGrid = new GridLayout(3, 2);
    	zoneGrid.setMargin(true);
    	panel.setContent(zoneGrid);
    	
    	for (Zone zone : Greenhouse.getInstance().getZoneController().getZones())
    	{
    		zoneGrid.addComponent(new ZoneComponent(zone));
    	}
		
		setCompositionRoot(panel);
	}
}
