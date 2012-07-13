package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

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
