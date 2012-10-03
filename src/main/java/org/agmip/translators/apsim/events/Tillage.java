package org.agmip.translators.apsim.events;

import org.agmip.translators.apsim.util.Converter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

public class Tillage extends Event {

    // depth
    @JsonProperty("tidep")
    String depth = "?";
    
    // implementCode
    @JsonProperty("tiimp")
    String implementCode = "?";
    
    // implementName
    private String getImplementName() {
        if ("?".equals(implementCode))
            return "?";
        else
            return Converter.tillageCodeToName(implementCode); 
    }
    
    // apsimAction
    @Override
    public String getApsimAction() {
        return "SurfaceOrganicMatter tillage type=" + getImplementName() + ", f_incorp=0, tillage_depth=" + depth;
    }
    
    
    

    // Initialise this instance.
    @Override
    public void initialise() {
        if ("?".equals(getDate()))
            log += "  * Operation tillage ERROR: Date missing. '?' has been inserted\r\n";
        
        if ("?".equals(depth)) {
            depth = "0";
            log += "  * Operation " + getDate() + " ASSUMPTION: Tillage depth missing. Zero mm has been assumed\r\n";
        }
        if ("?".equals(implementCode))
            log += "  * Operation " + getDate() + " ERROR: Tillage implement missing. '?' has been inserted\r\n";
        else
            log += "  * Operation " + getDate() + " ASSUMPTION: Tillage type: " + getImplementName() + " may not match an APSIM tillage type\r\n";
    }
    
}
