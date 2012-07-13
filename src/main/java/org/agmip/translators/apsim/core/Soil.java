/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agmip.translators.apsim.core;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Dean Holzworth
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Soil {
    @JsonProperty("classification")
    private String classification;

    @JsonProperty("soil_site")
    private String site;
    
    @JsonProperty("soil_name")
    private String name;
    
    @JsonProperty("sl_source")
    private String source;

    @JsonProperty("soil_lat")
    private double latitude;

    @JsonProperty("soil_long")
    private double longitude;
    
    @JsonProperty("soilLayer")
    private SoilLayer[] layers;

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public SoilLayer[] getLayers() {
		return layers;
	}

	public void setLayers(SoilLayer[] layers) {
		this.layers = layers;
	}
    
        
    public void calcThickness() {
        double cumThickness = 0.0;
        for (int i = 0; i < layers.length; i++) {
            cumThickness = layers[i].calcThickness(cumThickness);
        } 
    }


      
      
}
