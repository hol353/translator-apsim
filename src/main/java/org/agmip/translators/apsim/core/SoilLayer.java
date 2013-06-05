package org.agmip.translators.apsim.core;

import org.agmip.translators.apsim.util.Util;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Dean Holzworth, CSIRO
 * @author Ioannis N. Athanasiadis, DUTh
 * @since Jul 13, 2012
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SoilLayer {
    // bottomDepth
    @JsonProperty("sllb")
    private double bottomDepth = Util.missingValue;
	public void setBottomDepth(double bottomDepth) {
		this.bottomDepth = bottomDepth;
	}
	
    // thickness
    @JsonIgnore
    private double thickness = Util.missingValue;
    public double getThickness() { return thickness; }
	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

    // bulkDensity
    @JsonProperty("slbdm")
    private double bulkDensity = Util.missingValue;
    public double getBulkDensity() { return bulkDensity; }
	public void setBulkDensity(double bulkDensity) {
		this.bulkDensity = bulkDensity;
	}
	
    @JsonProperty("apsim_airdry")
    private double airDry = Util.missingValue;
    public double getAirDry() { return airDry; }
    
    // lowerLimit
    @JsonProperty("slll")
    private double lowerLimit = Util.missingValue;
    public double getLowerLimit() { return lowerLimit; }
	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	
    // drainedUpperLimit
    @JsonProperty("sldul")
    private double drainedUpperLimit = Util.missingValue;
    public double getDrainedUpperLimit() { return drainedUpperLimit; }
	public void setDrainedUpperLimit(double drainedUpperLimit) {
		this.drainedUpperLimit = drainedUpperLimit;
	}
    // saturation
    @JsonProperty("slsat")
    private double saturation = Util.missingValue; 
    public double getSaturation() { return saturation; }
	public void setSaturation(double saturation) {
		this.saturation = saturation;
	}
	
    // organicCarbon
    @JsonProperty("sloc")
    private double organicCarbon = Util.missingValue;
    public double getOrganicCarbon() { return organicCarbon; }
	public void setOrganicCarbon(double organicCarbon) {
		this.organicCarbon = organicCarbon;
	}
	
    // ph
    @JsonProperty("slphw")
    private double ph = Util.missingValue;
    public double getPh() { return ph; }
	public void setPh(double ph) {
		this.ph = ph;
	}
	
    // swcon
    @JsonProperty("SLDR")
    private double swcon = Util.missingValue;
    public double getSwcon() { return swcon; }
	public void setSwcon(double swcon) {
		this.swcon = swcon;
	}
	
    //kl
    @JsonProperty("apsim_kl")
    private double kl = Util.missingValue;
    public double getKl() { return kl; }
    
    // fbiom
    @JsonProperty("apsim_fbiom")
    private double fbiom;
    public double getFbiom() { return fbiom; }
    
    // finert
    @JsonProperty("apsim_finert")
    private double finert;
    public double getFinert() { return finert; }
    
    @JsonIgnore
    private String log;
    public String getLog() { return log; }    
    
    

    // Constructor - Needed for Jackson
    public SoilLayer() {}
    

    
    // Initialisation routine
    public double initialise(double cumThickness, int layerNumber, int numLayers) throws Exception {
        log = "";
        
        if (bottomDepth == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing thickness.\r\n";

        if (bulkDensity == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing bulk density.\r\n";

        if (airDry == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing airdry.\r\n";
        
        if (lowerLimit == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing lowerLimit.\r\n";
        
        if (drainedUpperLimit == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing drained upper limit.\r\n";

        if (saturation == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing saturation.\r\n";

        if (organicCarbon == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing organ carbon.\r\n";
        
        if (ph == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing PH.\r\n";
        
        if (swcon == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing SWCON.\r\n";

        if (kl == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing KL.\r\n";
        
        if (fbiom == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing FBIOM.\r\n";
        
        if (finert == Util.missingValue)
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing FINERT.\r\n";
        
        thickness = bottomDepth * 10 - cumThickness;
        
        return bottomDepth * 10;
    }
    
}