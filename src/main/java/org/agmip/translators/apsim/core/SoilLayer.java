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
public class SoilLayer {
    @JsonProperty("sllb")
    private double bottomDepth;
    
    @JsonIgnore
    private double thickness;
    
    @JsonProperty("slbdm")
    private double bulkDensity;
    
    @JsonIgnore
    private double airDry;
    
    @JsonProperty("slll")
    private double lowerLimit;
    
    @JsonProperty("sldul")
    private double drainedUpperLimit;
    
    @JsonProperty("slsat")
    private double saturation; 
    
    @JsonProperty("sloc")
    private double organicCarbon;

    @JsonProperty("slphw")
    private double ph;

    public double calcThickness(double cumThickness) {
        setThickness(getBottomDepth() * 10 - cumThickness);
        return getBottomDepth() * 10;
    }

    /**
     * @return the bottomDepth
     */
    public double getBottomDepth() {
        return bottomDepth;
    }

    /**
     * @param bottomDepth the bottomDepth to set
     */
    public void setBottomDepth(double bottomDepth) {
        this.bottomDepth = bottomDepth;
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

    /**
     * @return the bulkDensity
     */
    public double getBulkDensity() {
        return bulkDensity;
    }

    /**
     * @param bulkDensity the bulkDensity to set
     */
    public void setBulkDensity(double bulkDensity) {
        this.bulkDensity = bulkDensity;
    }

    /**
     * @return the airDry
     */
    public double getAirDry() {
        return lowerLimit - (lowerLimit * 0.05);
    }

    /**
     * @param airDry the airDry to set
     */
    public void setAirDry(double airDry) {
        this.airDry = airDry;
    }

    /**
     * @return the lowerLimit
     */
    public double getLowerLimit() {
        return lowerLimit;
    }

    /**
     * @param lowerLimit the lowerLimit to set
     */
    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    /**
     * @return the drainedUpperLimit
     */
    public double getDrainedUpperLimit() {
        return drainedUpperLimit;
    }

    /**
     * @param drainedUpperLimit the drainedUpperLimit to set
     */
    public void setDrainedUpperLimit(double drainedUpperLimit) {
        this.drainedUpperLimit = drainedUpperLimit;
    }

    /**
     * @return the saturation
     */
    public double getSaturation() {
        return saturation;
    }

    /**
     * @param saturation the saturation to set
     */
    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    /**
     * @return the organicCarbon
     */
    public double getOrganicCarbon() {
        return organicCarbon;
    }

    /**
     * @param organicCarbon the organicCarbon to set
     */
    public void setOrganicCarbon(double organicCarbon) {
        this.organicCarbon = organicCarbon;
    }

    /**
     * @return the ph
     */
    public double getPh() {
        return ph;
    }

    /**
     * @param ph the ph to set
     */
    public void setPh(double ph) {
        this.ph = ph;
    }
}
