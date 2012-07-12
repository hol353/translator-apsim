package org.agmip.translators.apsim.core;

import org.codehaus.jackson.annotate.JsonProperty;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by json2pojo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationRun {

	@JsonProperty("initial_condition")
	public InitialCondition initialCondition;
	public Weather weather;
	public Management management;
	public Soil soil;

	@JsonProperty("exname")
	public String experimentName;

	@JsonProperty("fl_long")
	public String longitude;
	@JsonProperty("fl_lat")
	public String latitude;

}
