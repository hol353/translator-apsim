/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agmip.translators.apsim;

import java.util.Map;

import org.agmip.translators.apsim.weather.Weather;

/**
 *
 * @author Dean Holzworth
 */
public class Experiment {

	
    Weather weather = new Weather();
    Soil soil = new Soil();
    Management treatment = new Management();
    
    
    
    public void readFrom(Map input) {
        soil.readFrom(input);
    }


    public Soil getSoil() {
        return soil;
    }

    public void setSoil(Soil soil) {
        this.soil = soil;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
