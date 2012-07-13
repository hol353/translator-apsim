package org.agmip.translators.apsim.core;

import java.util.List;


import org.agmip.translators.apsim.events.Event;
import org.agmip.translators.apsim.events.Planting;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Dean Holzworth, CSIRO
 * @author Ioannis N. Athanasiadis, DUTh
 * @since Jul 13, 2012
 */
 
@JsonIgnoreProperties(ignoreUnknown = true)
public class Management {

  private List<Event> events;

  public List<Event> getEvents() {
    return events;
  }
  
  public void setEvents(List<Event> events) {
    this.events = events;
  }

  public String plantingDate() {
      for (Event event : events) {
          if (event instanceof Planting) {
            return event.getDate();
          }
      }
      return null;
  }
  
}
