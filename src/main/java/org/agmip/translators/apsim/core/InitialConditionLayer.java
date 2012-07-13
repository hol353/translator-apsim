
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
    
    @JsonProperty("icbl")
    private double bottomDepth;
    
    @JsonProperty("ich2o")
    private double soilWater;
    
    @JsonProperty("icno3")
    private double no3;
    
    @JsonProperty("icnh4")
    private double nh4;

    @JsonIgnore
    private double thickness;
        
    public double getSoilWater() {
        return soilWater;
    }

    public void setSoilWater(double soilWater) {
        this.soilWater = soilWater;
    }

    public double getNo3() {
        return no3;
    }

    public void setNo3(double no3) {
        this.no3 = no3;
    }

    public double getNh4() {
        return nh4;
    }

    public void setNh4(double nh4) {
        this.nh4 = nh4;
    }
    
    public double calcThickness(double cumThickness) {
        setThickness(bottomDepth * 10 - cumThickness);  // convert from cm to mm
        return bottomDepth * 10;
    }

    /**
     * @return the thickness
     */
    public double getThickness() {
        return thickness;
    }

    /**
     * @param thickness the thickness to set
     */
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }
    
}
