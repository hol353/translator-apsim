package org.agmip.translators.apsim.events;

import org.agmip.ace.LookupCodes;
import org.agmip.translators.apsim.util.Util;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

public class Planting extends Event{

    // cropID
    @JsonProperty("crid")
    private String cropID = "?";

    // cultivar
    @JsonProperty("apsim_cul_id")
    private String cultivar = "?";

    // cultivar
    //@JsonProperty("cul_id")
    //private String culId = "?";
    
    // planting depth. units=mm
    @JsonProperty("pldp")
    private double depth = Util.missingValue;
    public double depth() { return depth; }

    // row spacing. units=cm
    @JsonProperty("plrs")
    double rowSpacing;
    public double rowSpacingAsMM() {
        if (rowSpacing == Util.missingValue)
            return Util.missingValue;
        else
            return rowSpacing * 10.0;
    }
    
    // Plant population at planting. units=number/m2
    @JsonProperty("plpop")
    private double population = Util.missingValue;

    // Crop Name
    public String getCropName() {
        return LookupCodes.lookupCode("crid", cropID, "common");
        //return Util.cropCodeToName(cropID);
    }
    
    @Override
    public String getApsimAction() {
        return getCropName()+" sow plants = " + population + ", sowing_depth = " + depth + ", cultivar = " + cultivar + ", row_spacing = " + rowSpacingAsMM() + ", crop_class = plant";
    }

    @Override
    public void initialise() {
        if ("?".equals(getDate()))
            log += "  * Operation planting ERROR: Date missing. '?' has been inserted.\r\n";
        
        if ("?".equals(cropID))
            log += "  * Operation " + getDate() + " ERROR: Planting crop missing.\r\n";
        
        if (depth == Util.missingValue)
            log += "  * Operation " + getDate() + " ERROR: Planting depth missing.\r\n";
      
        if ("?".equals(cultivar))
        	log += "  * Operation " + getDate() + " ERROR: Missing planting cultivar.\r\n";

        if (rowSpacing == Util.missingValue)
            log += "  * Operation " + getDate() + " ERROR: Planting row spacing missing.\r\n";

    }
        
}
