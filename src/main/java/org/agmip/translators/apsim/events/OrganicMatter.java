package org.agmip.translators.apsim.events;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

public class OrganicMatter extends Event {

    @JsonProperty("omamt")
    private String amount = "?";
    
    @JsonProperty("omdep")
    private String depth = "?";
    
    @JsonProperty("omc%")
    private String carbon = "?";

    @JsonProperty("omn%")
    private String nitrogen = "?";

    @JsonProperty("omp%")
    private String phosphorus = "?";
    
    @Override
    public String getApsimAction() {
        String cnr = "?";
        String cpr = "?";
        
        String Action = "SurfaceOrganicMatter add_surfaceom type=manure, name=manure, " +
                        "mass=" + amount + "(kg/ha), " +
                        "depth = " + depth + " (mm)";
               
        if (!amount.equals("?")) {
            if (!carbon.equals("?")) {
                if (!nitrogen.equals("?")) {
                    double amountCarbon = Double.valueOf(carbon) / 100.0 * Double.valueOf(amount);
                    double amountNitrogen = Double.valueOf(nitrogen) / 100.0 * Double.valueOf(amount);
                    if (amountNitrogen == 0.0)
                        cnr = "0";
                    else
                        cnr = String.valueOf(amountCarbon / amountNitrogen * 100.0);
                }
                Action += ", cnr = " + cnr;
                if (!phosphorus.equals("?")) {
                    double amountCarbon = Double.valueOf(carbon) / 100.0 * Double.valueOf(amount);
                    double amountPhosphorus = Double.valueOf(phosphorus) / 100.0 * Double.valueOf(amount);
                    if (amountPhosphorus == 0.0)
                        cpr = "0";
                    else
                        cpr = String.valueOf(amountCarbon / amountPhosphorus * 100.0);
                    Action += ", cpr = " + cpr;
                }
            }
        }
        return Action;
    }

    @Override
    public void initialise() {
        if ("?".equals(getDate()))
            log += "  * Operation fertiliser ERROR: Date missing. '?' has been inserted\r\n";
        
        if ("?".equals(amount))
            log += "  * Operation " + getDate() + " ERROR: Organic matter application missing amount\r\n";
        
        if ("?".equals(depth))
            log += "  * Operation " + getDate() + " ERROR: Organic matter application missing depth\r\n";
        if ("?".equals(carbon))
            log += "  * Operation " + getDate() + " ERROR: Organic matter application missing carbon percent. CNR=?\r\n";
        if ("?".equals(nitrogen))
            log += "  * Operation " + getDate() + " ERROR: Organic matter application missing nitrogen percent. CNR=?\r\n";
    }

}
