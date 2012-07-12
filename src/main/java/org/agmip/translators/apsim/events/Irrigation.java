package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Irrigation extends Event {
	
	@JsonProperty("irop")
	String method;
	
	@JsonProperty("irval")
	String amount;
	
	@JsonProperty("ireff")
	String efficiency;

	@Override
	String getApsimAction() {
		return "irrigation apply amount = " +amount+ " (mm) " ;
	}

}
