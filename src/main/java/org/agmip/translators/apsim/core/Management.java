package org.agmip.translators.apsim.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.agmip.translators.apsim.events.Event;
import org.agmip.translators.apsim.events.Planting;
import org.agmip.translators.apsim.util.Util;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Dean Holzworth, CSIRO
 * @author Ioannis N. Athanasiadis, DUTh
 * @since Jul 13, 2012
 */
 
@JsonIgnoreProperties(ignoreUnknown = true)
public class Management {

    // log
    private String log = "";
    public String getLog() { return log; }
    
    // events
    private List<Event> events;
    public List<Event> getEvents() { return events; }
        
    // return the crop being planted
    public String plantingCropName() {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getClass().getName().endsWith(".Planting")) {
                Planting planting = (Planting) events.get(i);
                return planting.getCropName();
            }
        }
        return null;
    }
    
    // bund height - I think this property should be in management not in
    // individual irrigation applications. Units: mm
    @JsonIgnore
    private double bundHeight = Util.missingValue;
    public double getBundHeight() { return bundHeight;}
    public void setBundHeight(double height) { bundHeight = height; setPaddyApplied(true); }

    @JsonIgnore
    private double minFlood = Util.missingValue;
    public double getMinFlood() { return minFlood;}
    public void setMinFlood(double height) { minFlood = height; setPaddyApplied(true); }

    @JsonIgnore
    private double maxFlood = Util.missingValue;
    public double getMaxFlood() { return maxFlood;}
    public void setMaxFlood(double height) { maxFlood = height; setPaddyApplied(true); }
    
    @JsonIgnore
    private boolean isPaddyApplied = false;
    public boolean isPaddyApplied() { return isPaddyApplied;}
    public void setPaddyApplied(boolean isPaddyApplied) { this.isPaddyApplied = isPaddyApplied; }
    
    
    // default constructor - needed for Jackson
    public Management() {}
    
    
    Comparator<Event> eventComparator = new Comparator<Event>() {
        @Override
        public int compare(Event eventA, Event eventB)  {
            if (eventA.getEventDate() == null)
                return -1;
            if (eventB.getEventDate() == null)
                return 1;
            return eventA.getEventDate().compareTo(eventB.getEventDate());
        }
    };    
    
    // initialise this instance
    public void initialise() {
    	
        // Special handling for irrigation with operation code of IR008, IR009 and IR010
//        int size = events.size();
//        for (int i = 0; i < size; i++) {
//            if (events.get(i) instanceof Irrigation) {
//                Irrigation ir = (Irrigation) events.get(i);
//                if (("IR008").equals(ir.getMethod())) {
//                    // Might add another SetVariableEvent for percolation rate (KS)
//                } else if (("IR009").equals(ir.getMethod())) {
//                    events.add(new SetVariableEvent(ir.getDate(),
//                            "Soil Water",
//                            "max_pond",
//                            String.valueOf(ir.getAmount())));
//                } else if (("IR010").equals(ir.getMethod())) {
//                    // Might add another SetVariableEvent for plow-pan depth
//                }
//            }
//        }

        // skip meaningless events
//        for (int i = events.size() - 1; i > -1; i--) {
//            if (events.get(i) instanceof Irrigation) {
//                Irrigation ir = (Irrigation) events.get(i);
//                if (("IR008").equals(ir.getMethod())
//                        || ("IR009").equals(ir.getMethod())
//                        || ("IR010").equals(ir.getMethod())) {
//                    events.remove(i);
//                }
//            }
//        }

        // initialise all events.
        for (int i = 0; i < events.size(); i++) {
            events.get(i).initialise(this);
            log += events.get(i).getLog();
        }
        
        // Look for bund height. If found then add it as an event.
//        if (events.size() >= 1 && bundHeight != Util.missingValue) {
//        	Collections.sort(events, eventComparator);
//        	events.add(new SetVariableEvent(events.get(0).getDate(), 
//        			                        "Soil Water",
//        			                        "max_pond",
//        			                        String.valueOf(bundHeight)));
//        }
        
        

        
        // sort the events into date order.
        Collections.sort(events, eventComparator);
    }
}
