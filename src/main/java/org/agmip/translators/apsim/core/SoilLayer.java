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
    private String bottomDepth = "?";
    
    // thickness
    @JsonIgnore
    private String thickness = "?";
    public String getThickness() { return thickness; }
    
    // bulkDensity
    @JsonProperty("slbdm")
    private String bulkDensity = "?";
    public String getBulkDensity() { return bulkDensity; }
    
    @JsonIgnore
    public String getAirDry() {
        double ll = Double.valueOf(lowerLimit);
        return String.valueOf(ll * 0.5);
    }
    
    // lowerLimit
    @JsonProperty("slll")
    private String lowerLimit = "?";
    public String getLowerLimit() { return lowerLimit; }
    
    // drainedUpperLimit
    @JsonProperty("sldul")
    private String drainedUpperLimit = "?";
    public String getDrainedUpperLimit() { return drainedUpperLimit; }
    
    // saturation
    @JsonProperty("slsat")
    private String saturation = "?"; 
    public String getSaturation() { return saturation; }
    
    // organicCarbon
    @JsonProperty("sloc")
    private String organicCarbon ="?";
    public String getOrganicCarbon() { return organicCarbon; }
    
    // ph
    @JsonProperty("slphw")
    private String ph = "?";
    public String getPh() { return ph; }
    
    // swcon
    @JsonProperty("SLDR")
    private String swcon = "?";
    public String getSwcon() { return swcon; }
    
    @JsonIgnore
    private double kl;
    public double getKl() { return kl; }
    
  
    @JsonIgnore
    private double fbiom;
    public double getFbiom() { return fbiom; }
    
    @JsonIgnore
    private double finert;
    public double getFinert() { return finert; }
    
    @JsonIgnore
    private String log;
    public String getLog() { return log; }    
    
    

    // Needed for Jackson
    public SoilLayer() {}
    

    
    // These assumptions are written to the top of the .apsim file in a <Memo>
    public double initialise(double cumThickness, int layerNumber, int numLayers) throws Exception {
        log = "";
        
        if ("?".equals(bottomDepth))
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing thickness.\r\n";

        if ("?".equals(bulkDensity))
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing bulk density.\r\n";
        
        if ("?".equals(lowerLimit))
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing lowerLimit.\r\n";
        
        if ("?".equals(drainedUpperLimit))
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing drained upper limit.\r\n";

        if ("?".equals(saturation))
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing saturation.\r\n";

        if ("?".equals(organicCarbon))
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ERROR: Missing organ carbon.\r\n";
        
        if ("?".equals(ph)) {
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ASSUMPTION: Missing PH. Assuming a value of 7.0\r\n";
            ph = "7.0";
        }
        if ("?".equals(swcon)) {
            log += "  * Soil layer " + String.valueOf(layerNumber) + " ASSUMPTION: Missing SWCON. Assuming a value of 0.3\r\n";
            swcon = "0.3";
        }
        double bottom = Double.valueOf(bottomDepth);
        thickness = String.valueOf(bottom * 10 - cumThickness);
        
        double[] klX = {  15,   30,   60,   90,  120,  150,  180};
        double[] klY = {0.08, 0.08, 0.08, 0.06, 0.06, 0.04, 0.02};
        kl = Util.linearInterpReal(bottom, klX, klY);
        
        double fbiomPerLayer = 0.03;
        if (numLayers > 1)
            fbiomPerLayer = (0.04 - 0.01) / (numLayers-1);
        fbiom = 0.04 - ( (layerNumber-1) * fbiomPerLayer);

        double[] finertX = {  15,   30,   60,   90};
        double[] finertY = { 0.4,  0.5,  0.7, 0.95};
        finert = Util.linearInterpReal(bottom, finertX, finertY);
        
        return bottom * 10;
    }
    
    // Where only soil surface data has been collected use the following method 
                // for estimating values in the deeper layers:
                // 0-15 cm measured data, 
                // 15-30 cm, 80% of 0-15 layer; 
                // 30-60 cm, 50% of 0-15 layer; 
                // 60-90 cm, 25% of 0-15 layer; 
                // 90-120 cm, 15% of 0-15 layer; 
                // 120-150 cm, 10% of 0-15 layer; 
                // 150-180 cm 10% of 0-15 layer.
    public void calculateOrganicCarbon(double TopLayerOC) throws Exception {
        double[] bottomX = {  30,   60,   90,  120,  150,  180};
        double[] percentY = {  80,   50,   25,   15,   10,   10};
        
        if (organicCarbon.equals("?")) {
            double Percent = Util.linearInterpReal(Double.valueOf(bottomDepth), 
                                                        bottomX, percentY);
            organicCarbon = String.valueOf(TopLayerOC * Percent / 100.0);
        }
        
    }


	public void setBottomDepth(String bottomDepth) {
		this.bottomDepth = bottomDepth;
	}


	public void setThickness(String thickness) {
		this.thickness = thickness;
	}

	public void setBulkDensity(String bulkDensity) {
		this.bulkDensity = bulkDensity;
	}


	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}


	public void setDrainedUpperLimit(String drainedUpperLimit) {
		this.drainedUpperLimit = drainedUpperLimit;
	}


	public void setSaturation(String saturation) {
		this.saturation = saturation;
	}


	public void setOrganicCarbon(String organicCarbon) {
		this.organicCarbon = organicCarbon;
	}


	public void setPh(String ph) {
		this.ph = ph;
	}


	public void setSwcon(String swcon) {
		this.swcon = swcon;
	}
 
    
}