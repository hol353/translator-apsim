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
    @JsonProperty("apsim_ftn")
    private double ftn = Util.missingValue;
    
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
        return LookupCodes.lookupCode("crid", cropID, "apsim");
    }
    
    @Override
    public String getApsimAction() {
    	String actionLine = getSowLine(getCropName());
    	actionLine = actionLine.replace("$cropName", getCropName());
    	actionLine = actionLine.replace("$population", String.valueOf(population)); 
    	actionLine = actionLine.replace("$depth", String.valueOf(depth));
    	actionLine = actionLine.replace("$cultivar",cultivar);
    	actionLine = actionLine.replace("$row_spacing", String.valueOf(rowSpacingAsMM()));
    	actionLine = actionLine.replace("$ftn", String.valueOf(ftn));
    	return actionLine;
    }

    @Override
    public void initialise() {
        if ("?".equals(getDate()))
            log += "  * Operation planting ERROR: Date missing. '?' has been inserted.\r\n";
        
        if ("?".equals(cropID))
            log += "  * Operation " + getDate() + " ERROR: Planting crop missing (crid).\r\n";
        
        if (depth == Util.missingValue)
            log += "  * Operation " + getDate() + " ERROR: Planting depth missing (pldp).\r\n";
      
        if ("?".equals(cultivar))
        	log += "  * Operation " + getDate() + " ERROR: Missing planting cultivar (apsim_cul_id).\r\n";

        if (rowSpacing == Util.missingValue)
            log += "  * Operation " + getDate() + " ERROR: Planting row spacing missing (plrs).\r\n";

        if (getCropName() == "sorghum" && ftn == Util.missingValue)
        	log += "  * Operation " + getDate() + " ERROR: Planting fertile tiller number not specified for sorghum (apsim_ftn): r\n";
    }
    
    // Return a specific crop sow line.    
    private static String getSowLine(String cropName) {
    	if (cropName.equals("maize"))
    		return "$cropName sow plants = $population, sowing_depth = $depth (mm), cultivar = $cultivar, row_spacing = $row_spacing (mm)";
    	else if (cropName.equals("sorghum"))
    		return "$cropName sow plants = $population, sowing_depth = $depth (mm), cultivar = $cultivar, row_spacing = $row_spacing (mm), skip = solid, tiller_no_fertile = $ftn";
    	else if (cropName.equals("sugar"))
    		return "$cropName sow plants = $population, cultivar = $cultivar, sowing_depth = $depth";
    	else
    		// Plant based crops
    		return "$cropName sow plants = $population, sowing_depth = $depth (mm), cultivar = $cultivar, row_spacing = $row_spacing (mm), crop_class = plant";
    }
    
  
        
}
