package org.agmip.translators.apsim.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.agmip.translators.apsim.events.Event;
import org.agmip.translators.apsim.events.Planting;
import org.agmip.translators.apsim.events.Irrigation;
import org.agmip.translators.apsim.util.Util;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;
import org.joda.time.Days;

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

    @JsonIgnore
    private List<BundEntry> bundEntries = new ArrayList<BundEntry>();
    public List<BundEntry> getBundEntries() { return bundEntries; }
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

        // initialise all events.
        DateTime pdate = null;
        for (int i = 0; i < events.size(); i++) {
            events.get(i).initialise(this);
            log += events.get(i).getLog();
            if (events.get(i).getClass().getName().endsWith(".Planting")) {
                pdate = new DateTime(((Planting) events.get(i)).getEventDate());
            }
        }

        // sort the events into date order.
        Collections.sort(events, eventComparator);

        // initialize the wonderful bund stuff
        DateTime idate = null;
        BundEntry be = null;
        boolean runWithBund = true;
        for(int i=0; i < events.size(); i++) {
            if (events.get(i).getClass().getName().endsWith(".Irrigation")) {
                if (runWithBund) {
                    Irrigation irr = (Irrigation) events.get(i);
                    if (! irr.isPaddy()) {
                        runWithBund = false;
                        break;
                    }
                    DateTime currDate = new DateTime(irr.getEventDate());
                    int dap = Days.daysBetween(pdate, currDate).getDays();
                    if (idate == null) {
                        idate = currDate;
                        be = new BundEntry(dap).merge(irr);
                    } else if (idate != currDate) {
                        bundEntries.add(be);
                        idate = currDate;
                        be = new BundEntry(dap).merge(irr);
                    } else {
                        be.merge(irr);
                    }
                }
            }
        }
        if (runWithBund) {
            bundEntries.add(be);
        }
        this.isPaddyApplied = runWithBund;
    }

    public class BundEntry {
        private final int dap;
        private double bundHeight;
        private double minFlood;
        private double maxFlood;

        public BundEntry(int dap) {
            this.dap = dap;
        }

        public BundEntry merge(Irrigation irr) {
            if("IR009" == irr.getMethod()) {
                this.maxFlood = irr.getAmount();
            }

            if("IR010" == irr.getMethod()) {
                this.bundHeight = irr.getAmount();
            }
            if("IR011" == irr.getMethod()) {
                this.minFlood = irr.getAmount();
            }
            return this;
        }
        public int getDap() { return dap; }
        public double getBundHeight() { return bundHeight; }
        public double getMinFlood() { return minFlood; }
        public double getMaxFlood() { return maxFlood; }
    }
}
