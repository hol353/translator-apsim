package org.agmip.translators.apsim.events;

import org.agmip.translators.apsim.core.Management;
import org.agmip.translators.apsim.util.Util;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Meng Zhang
 * @since Aug 13, 2014
 */
public class AutoIrrigation extends Event {
    
    @JsonProperty("iamt")
    private double amount = Util.missingValue;
    public double getAmount() { return amount; }
    
    @JsonProperty("irmdp")
    private double irmdp = Util.missingValue;
    public double getIrmdp() {
        return irmdp;
    }
    
    @JsonProperty("irthr")
    private double irthr = Util.missingValue;
    public double getIrthr() { 
        return irthr / 100; // percentage -> fraction
    }

    @Override
    String getApsimAction() {
        return null;
    }

    @Override
    public void initialise(Management management) {
        boolean noErrFlg = true;
        if (irmdp == Util.missingValue) {
            log += "  * Irrigation management, reference soil depth for automatic application (irmdp).\r\n";
            noErrFlg = false;
        }

        if (irthr == Util.missingValue) {
            log += "  * Operation  ERROR: Irrigation threshold water content for automatic application missing (irthr).\r\n";
            noErrFlg = false;
        }
        
        management.setAutoIrrigated(noErrFlg);
    }
    
}
