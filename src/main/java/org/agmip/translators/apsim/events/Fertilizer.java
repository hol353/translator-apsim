package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonProperty;


public class Fertilizer extends Event {
	
	@JsonProperty("feacd")
	String method;
	
	@JsonProperty("fecd")
	String material;

	@JsonProperty("fedep")
	String depth;
	
	@JsonProperty("feamp")
	String phosphorus;
	
	@JsonProperty("feamk")
	String potasium;
	

	@JsonProperty("feamn")
	String nitrogen;


	@Override
	public String getApsimAction() {
		return "fertiliser apply amount = " + nitrogen + "(kg/ha), type = no3_n (), depth = " + depth + " (mm)";
	}
	
	
	
}
