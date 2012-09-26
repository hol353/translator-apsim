package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

public class Planting extends Event{

	/**
	 * Cultivar name
	 * Unit or Type: text
	 */
	
	@JsonProperty("crid")
	String cropID;
	
	@JsonProperty("cul_name")
	String cultivar;

	/**
	 * Planting depth
	 * Unit or Type: mm (AgMIP unit: cm)
     * TODO: Implement conversion
	 */
	@JsonProperty("pldp")
	Double depth;
	/**
	 * Row spacing
	 * Unit or Type: cm
	 */
	@JsonProperty("plrs")
	Double rowSpacing;
	/**
	 * Plant population at planting
	 * Unit or Type: number/m2
	 */
	@JsonProperty("plpop")
	Double population;

	/*
	 * (non-Javadoc)
	 * @see org.agmip.translators.apsim.events.Event#getApsimAction()
	 * TODO: Add a translator from code to name.
	 */
	@Override
	public String getApsimAction() {
            depth = depth*10.0;
            if (cropID.equals("MZ"))
                cropID = "Maize";
            cultivar = "sc401";
            return cropID +" sow plants = " + population + ", sowing_depth = " + depth + ", cultivar = " + cultivar + ", row_spacing = " + rowSpacing + ", crop_class = plantsow ";  
	}
        
}
