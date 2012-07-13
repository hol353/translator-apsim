package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

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
