/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agmip.translators.apsim;

import java.util.ArrayList;
import java.util.Map;
import org.agmip.util.MapUtil;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author Dean Holzworth
 */
public class Soil {
    @JsonProperty("classification")
    private String classification;

    @JsonProperty("soil_site")
    private String site;
    
    @JsonProperty("soil_name")
    private String name;
    
    @JsonProperty("sl_source")
    private String source;

    @JsonProperty("soil_lat")
    private double latitude;

    @JsonProperty("soil_long")
    private double longitude;
    
    private SoilLayer[] layers;
    
    public void readFrom(Map input) {
        MapUtil.BucketEntry soil = MapUtil.getBucket(input, "soil").get(0);
        
        setClassification(MapUtil.getValueOr(soil.getValues(), "classification", "?"));
        setSite(MapUtil.getValueOr(soil.getValues(), "soil_site", "?"));
        setName(MapUtil.getValueOr(soil.getValues(), "soil_name", "?"));
        setSource(MapUtil.getValueOr(soil.getValues(), "sl_source", "?"));
        setLatitude(Double.parseDouble(MapUtil.getValueOr(soil.getValues(), "soil_lat", "?")));
        setLongitude(Double.parseDouble(MapUtil.getValueOr(soil.getValues(), "soil_long", "?")));
        
        ArrayList data = soil.getDataList();
        setLayers(new SoilLayer[data.size()]);
        double cumThickness = 0.0;
        for (int i = 0; i < data.size(); i++) {
            getLayers()[i] = new SoilLayer();
            getLayers()[i].readFrom((Map) data.get(i));
            cumThickness = getLayers()[i].calcThickness(cumThickness);
        } 
    }


    public String getClassification() {
        return classification;
    }


    public void setClassification(String classification) {
        this.classification = classification;
    }


    public String getSite() {
        return site;
    }


    public void setSite(String site) {
        this.site = site;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

 
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public SoilLayer[] getLayers() {
        return layers;
    }

     public void setLayers(SoilLayer[] layers) {
        this.layers = layers;
    }
        
      
}
