/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.tylerwest.greenhouse.controllers;

import java.util.List;

/**
 *
 * @author Tyler
 */
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
