package org.agmip.translators.apsim.core;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Dean Holzworth, CSIRO
 * @author Ioannis N. Athanasiadis, DUTh
 * @since Jul 13, 2012
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialCondition {
    
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
    @JsonProperty("icdat")
    public String date = "?";
    
    @JsonProperty("icrag")
    public double residueWeight;
    
    @JsonProperty("icrn")
    public double residueNConc;
  
    @JsonProperty("soilLayer")
    public InitialConditionLayer[] soilLayers;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}


        
	public double getResidueWeight() {
		return residueWeight;
	}

	public void setResidueWeight(double residueWeight) {
		this.residueWeight = residueWeight;
	}

	public double getResidueNConc() {
		return residueNConc;
	}

	public void setResidueNConc(double residueNConc) {
		this.residueNConc = residueNConc;
	}

	public InitialConditionLayer[] getSoilLayers() {
		return soilLayers;
	}

	public void setSoilLayers(InitialConditionLayer[] soilLayers) {
		this.soilLayers = soilLayers;
	}
    
    public void calcThickness() {
        double cumThickness = 0.0;
        for (int i = 0; i < soilLayers.length; i++) {
            cumThickness = soilLayers[i].calcThickness(cumThickness);
        } 
    }

 
}
