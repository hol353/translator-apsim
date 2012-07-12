
package org.agmip.translators.apsim.core;

import java.util.List;


import org.agmip.translators.apsim.events.Event;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by json2pojo
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

}
