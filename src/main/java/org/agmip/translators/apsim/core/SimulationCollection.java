package org.agmip.translators.apsim.core;

import java.util.Vector;

import org.codehaus.jackson.annotate.JsonProperty;

public class SimulationCollection {
	@JsonProperty("soils")
	Vector<Soil> soils = new Vector<Soil>();
	public Vector<Soil> getSoils() { return soils; }	

	@JsonProperty("weathers")
	Vector<Weather> weathers = new Vector<Weather>();
	public Vector<Weather> getWeathers() { return weathers; }	

	@JsonProperty("experiments")
	Vector<SimulationRun> experiments = new Vector<SimulationRun>();
	public Vector<SimulationRun> getExperiments() { return experiments; }

	public void initialise() throws Exception{

		for(SimulationRun sim:experiments){
			String mySoil = sim.getSoilID();
			for(Soil soil:soils){
				if(mySoil.equals(soil.getID())){
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
