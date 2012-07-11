package org.agmip.translators.apsim.weather;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * An APSIM Weather info compartment consists of several station information.
 * 
 * 
 * @author Ioannis N. Athanasiadis, http://eco.logismi.co
 * @since July 10, 2012
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
	List<Station> data = new ArrayList<Station>();

	public List<Station> getData() {
		return data;
	}

	public void setData(List<Station> data) {
		this.data = data;
	}
	


}
