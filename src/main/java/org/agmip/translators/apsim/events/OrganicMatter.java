package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonProperty;


public class OrganicMatter extends Event {

	@JsonProperty("omamt")
	String amount;
	@JsonProperty("omdep")
	String depth;
	@JsonProperty("omn%")
	String nitrogen;
	@Override
	public String getApsimAction() {
		return "";
	}

}
