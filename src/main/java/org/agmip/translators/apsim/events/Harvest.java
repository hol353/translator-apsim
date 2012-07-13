package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Harvest extends Event {

	@Override
	public String getApsimAction() {
		return "";
	}


}
