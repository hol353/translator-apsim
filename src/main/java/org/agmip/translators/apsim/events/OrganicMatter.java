package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

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
