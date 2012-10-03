package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

public class OrganicMatter extends Event {

    @JsonProperty("omamt")
    private String amount;
    
    @JsonProperty("omdep")
    private String depth;
    
    @JsonProperty("omn%")
    private String nitrogen;
    
    @Override
    public String getApsimAction() {
        return null;
    }

    @Override
    public void initialise() {
        
    }

}
