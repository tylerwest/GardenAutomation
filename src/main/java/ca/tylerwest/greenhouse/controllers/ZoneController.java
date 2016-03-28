package ca.tylerwest.greenhouse.controllers;

import java.util.List;

public class ZoneController {
    private final List<Zone> zones;

    public ZoneController(List<Zone> zones) {
        this.zones = zones;
    }

    public List<Zone> getZones() {
        return zones;
    }
    
    public Zone getZoneByID(String ID) {
        for (Zone zone : zones)
            if (zone.getID().equals(ID))
                return zone;
        return null;
    }
}
