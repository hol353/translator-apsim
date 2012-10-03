
package org.agmip.translators.apsim.core;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Dean Holzworth, CSIRO
 * @author Ioannis N. Athanasiadis, DUTh
 * @since Jul 13, 2012
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialConditionLayer {
    
    // bottomDepth;
    @JsonProperty("icbl")
    private double bottomDepth;
    
    // soilWater
    @JsonProperty("ich2o")
    private String soilWater = "-99";
    public String getSoilWater() { return soilWater; }
    
    // no3
    @JsonProperty("icno3")
    private String no3 = "-99";
    public String getNo3() { return no3; }
    
    // nh4
    @JsonProperty("icnh4")
    private String nh4 = "-99";
    public String getNh4() { return nh4; }
    
    // thickness
    @JsonIgnore
    private String thickness = "?";
    public String getThickness() { return thickness; }    

    
    
    
    
    
    
    
    // constructor
    InitialConditionLayer(String thickness) {
        this.thickness = thickness;
    }
    
    // Initialise this instance.
    public double initialise(double cumThickness) {
        thickness = String.valueOf(bottomDepth * 10 - cumThickness);  // convert from cm to mm
        return bottomDepth * 10;
    }
}
