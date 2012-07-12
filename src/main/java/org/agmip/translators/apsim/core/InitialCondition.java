/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agmip.translators.apsim.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.agmip.util.MapUtil;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author hol353
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialCondition {
    
    @JsonProperty("icdat")
    public String date;
    
    @JsonProperty("icrag")
    public double residueWeight;
    
    @JsonProperty("icrn")
    public double residueNConc;
    
    public InitialConditionLayer[] soilLayer;
    
    public void readFrom(Map input) throws ParseException {
/*        MapUtil.BucketEntry management = MapUtil.getBucket(input, "treatment").get(0);
        MapUtil.BucketEntry initialConditions = management.getSubBucketEntry("initial_condition");
        
        DateFormat dateFormatter = new SimpleDateFormat("yyyymmdd");
        date = dateFormatter.parse(MapUtil.getValueOr(initialConditions.getValues(), "icdat", "?"));
        residueWeight = Double.parseDouble(MapUtil.getValueOr(management.getValues(), "icrag", "?"));
        residueNConc = Double.parseDouble(MapUtil.getValueOr(management.getValues(), "icrn", "?"));
       
        ArrayList data = management.getDataList();
        setLayers(new InitialConditionLayer[data.size()]);
        for (int i = 0; i < data.size(); i++) {
            layers[i] = new InitialConditionLayer();
            layers[i].readFrom((Map) data.get(i));
        } 
*/    }

 
}
