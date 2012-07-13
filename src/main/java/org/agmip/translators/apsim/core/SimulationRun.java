package org.agmip.translators.apsim.core;

import org.codehaus.jackson.annotate.JsonProperty;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	public String experimentName = "default";

	@JsonProperty("fl_long")
	public String longitude;
	@JsonProperty("fl_lat")
	public String latitude;
	public InitialCondition getInitialCondition() {
		return initialCondition;
	}
	public void setInitialCondition(InitialCondition initialCondition) {
		this.initialCondition = initialCondition;
	}
	public Weather getWeather() {
		return weather;
	}
	public void setWeather(Weather weather) {
		this.weather = weather;
	}
	public Management getManagement() {
		return management;
	}
	public void setManagement(Management management) {
		this.management = management;
	}
	public Soil getSoil() {
		return soil;
	}
	public void setSoil(Soil soil) {
		this.soil = soil;
	}
	public String getExperimentName() {
		return experimentName;
	}
	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	
	
	
}
