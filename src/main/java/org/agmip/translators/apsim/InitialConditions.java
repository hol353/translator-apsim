/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agmip.translators.apsim;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import org.agmip.util.MapUtil;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author hol353
 */
public class InitialConditions {
    
    @JsonProperty("icdat")
    private Date date;
    
    @JsonProperty("icrag")
    private double residueWeight;
    
    @JsonProperty("icrn")
    private double residueNConc;
    
    private InitialConditionLayer[] layers;
    
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

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the residueWeight
     */
    public double getResidueWeight() {
        return residueWeight;
    }

    /**
     * @param residueWeight the residueWeight to set
     */
    public void setResidueWeight(double residueWeight) {
        this.residueWeight = residueWeight;
    }

    /**
     * @return the residueNConc
     */
    public double getResidueNConc() {
        return residueNConc;
    }

    /**
     * @param residueNConc the residueNConc to set
     */
    public void setResidueNConc(double residueNConc) {
        this.residueNConc = residueNConc;
    }

    /**
     * @return the layers
     */
    public InitialConditionLayer[] getLayers() {
        return layers;
    }

    /**
     * @param layers the layers to set
     */
    public void setLayers(InitialConditionLayer[] layers) {
        this.layers = layers;
    }
}
