package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Irrigation extends Event {
	
    //@JsonProperty("irop")
    //private String method = "?";

    @JsonProperty("irval")
    private String amount = "?";

    //@JsonProperty("ireff")
    //private String efficiency = "?";

    @Override
    public String getApsimAction() {
        return "irrigation apply amount = " +amount+ " (mm) " ;
    }

    @Override
    public void initialise() {
        if ("?".equals(getDate()))
            log += "  * Operation irrigation ERROR: Date missing. '?' has been inserted\r\n";
        
        if ("?".equals(amount))
            log += "  * Operation " + getDate() + " ERROR: Irrigation amount missing. A '?' has been inserted.\r\n";
    }

}
