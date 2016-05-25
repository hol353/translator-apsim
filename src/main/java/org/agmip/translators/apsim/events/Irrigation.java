package org.agmip.translators.apsim.events;

import org.agmip.translators.apsim.core.Management;
import org.agmip.translators.apsim.util.Util;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Irrigation extends Event {

    @JsonProperty("irop")
    private String method = "?";
    public String getMethod() { return method; }

    @JsonProperty("irval")
    private double amount = Util.missingValue;
    public double getAmount() { return amount; }

    @JsonProperty("abund")
    private double bundHeight = Util.missingValue;

    @JsonIgnore
    private boolean usePaddy = false;
    public boolean isPaddy() { return usePaddy; }

    @Override
    public String getApsimAction() {
        if (("IR008").equals(method)
            || ("IR009").equals(method)
            || ("IR010").equals(method)
            || ("IR011").equals(method)) {
            usePaddy = true;
            return null;
        } else {
            return "irrigation apply amount = " +amount+ " (mm) " ;
        }
    }

    @Override
    public void initialise(Management management) {
        if ("?".equals(getDate()))
            log += "  * Operation irrigation ERROR: Date missing (date).\r\n";

        if (amount == Util.missingValue)
            log += "  * Operation " + getDate() + " ERROR: Irrigation amount missing (irval).\r\n";

        if (("IR008").equals(method)) {
            // KS
        } else if (("IR010").equals(method)) {
            // puddling, plowpan depth
            bundHeight = amount;
            if (bundHeight != Util.missingValue)
                management.setBundHeight(bundHeight);
        } else if (("IR009").equals(method)) {
            // Max flood height
            if (management.getMaxFlood() == Util.missingValue) {
                management.setMaxFlood(amount);
            }
        } else if (("IR011").equals(method)) {
            // Min flood height
            if (management.getMinFlood() == Util.missingValue) {
                management.setMinFlood(amount);
            }
        }


    }

}
