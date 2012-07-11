/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agmip.translators.apsim;

import java.util.Map;
import org.agmip.util.MapUtil;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Dean Holzworth
 */
class InitialConditionLayer {
    @JsonProperty("ich20")
    private double soilWater;
    
    @JsonProperty("icno3")
    private double no3;
    
    @JsonProperty("icnh4")
    private double nh4;

    
    void readFrom(Map layer) {
        soilWater = Double.parseDouble(MapUtil.getValueOr(layer, "ich20", "-99"));
        no3 = Double.parseDouble(MapUtil.getValueOr(layer, "icno3", "-99"));
        nh4 = Double.parseDouble(MapUtil.getValueOr(layer, "icnh4", "-99"));
    }
    
    
    

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

    
}
