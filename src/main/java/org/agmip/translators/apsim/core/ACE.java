package org.agmip.translators.apsim.core;

import java.util.Collection;
import java.util.Vector;

import org.codehaus.jackson.annotate.JsonProperty;

public class ACE {
	// soils
	@JsonProperty("soils")
	Collection<Soil> soils = new Vector<Soil>();
	public Collection<Soil> getSoils() { return soils; }
	public void setSoils(Collection<Soil> soils) {
		this.soils = soils;
	}
	
	// weathers
	@JsonProperty("weathers")
	Collection<Weather> weathers = new Vector<Weather>();
	public Collection<Weather> getWeathers() { return weathers; }
	public void setWeathers(Collection<Weather> weathers) {
		this.weathers = weathers;
	}

	// experiments
	@JsonProperty("experiments")
	Collection<Simulation> experiments = new Vector<Simulation>();
	public Collection<Simulation> getExperiments() { return experiments; }
	public void setExperiments(Collection<Simulation> experiments) {
		this.experiments = experiments;
	}

	// initialise this object
	public void initialise() throws Exception{

		// Initialise each soil.
		for(Soil soil:soils)
			soil.initialise();
		
		// For each experiment, give it the corresponding soil and climate.
		if (experiments != null) {
			for(Simulation sim:experiments){
				String mySoil = sim.getSoilID();
				for(Soil soil:soils){
					if(mySoil.equals(soil.getId())){
						sim.setSoil(soil);
						break;
					}
				}


				String myWeather = sim.getWeatherID();
				for (Weather w : weathers){
					if(myWeather.equals(w.getId())){
						sim.setWeather(w); break;
					}
				}
	
				sim.initialise();
			}
		}
	}


}
