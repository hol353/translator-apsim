package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonProperty;


public class Tillage extends Event {
	
    @JsonProperty("tidep")
	String depth;
	@JsonProperty("ti_name")
	String implementName;
	@Override
	public String getApsimAction() {
		// TODO Auto-generated method stub
		return "";
	}

}
