package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

public class Fertilizer extends Event {
	
    // method
    //@JsonProperty("feacd")
    //private String method;

    // material
    //@JsonProperty("fecd")
    //private String material;
    
    //@JsonProperty("feamp")
    //private String phosphorus;

    //@JsonProperty("feamk")
    //private String potasium;

    @JsonProperty("fedep")
    private String depth = "?";

    @JsonProperty("feamn")
    private String nitrogen = "?";

    @Override
    public String getApsimAction() {
        return "fertiliser apply amount = " + nitrogen + " (kg/ha), type = no3_n (), depth = " + depth + " (mm)";
    }

    
    
    
    
    
    @Override
    public void initialise() {
        log = "";
        if ("?".equals(getDate()))
            log += "  * Operation fertiliser ERROR: Date missing. '?' has been inserted\r\n";
        
        if ("?".equals(depth)) {
            depth = "50";
            log += "  * Operation " + getDate() + " ASSUMPTION: Fertiliser depth missing. A value of 50 mm has been assumed\r\n";
        }
        
        if ("?".equals(nitrogen)) {
            log += "  * Operation " + getDate() + " ERROR: Fertiliser nitrogen amount missing. A '?' has been inserted\r\n";
        }        
        
        
    }
	
	
}
