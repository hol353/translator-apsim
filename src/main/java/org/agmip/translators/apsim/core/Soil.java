package org.agmip.translators.apsim.core;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Dean Holzworth, CSIRO
 * @author Ioannis N. Athanasiadis, DUTh
 * @since Jul 13, 2012
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Soil {
    
    // classification
    @JsonProperty("classification")
    private String classification = "?" ;
    public String getClassification() { return classification; }
    
    // site
    @JsonProperty("soil_site")
    private String site = "?";
    public String getSite() { return site; }
    
    // name
    @JsonProperty("soil_name")
    private String name = "?";
    public String getName() {
        if ("?".equals(name))
            return id;
        else
            return name;
    }
    
    // id
    @JsonProperty("soil_id")
    private String id = "?";    
    
    
    // source
    @JsonProperty("sl_source")
    private String source = "?";
    public String getSource() { return source; }
    
    // latitude
    @JsonProperty("soil_lat")
    private String latitude = "?";
    public String getLatitude() { return latitude; }
    
    // longitude
    @JsonProperty("soil_long")
    private String longitude = "?";
    public String getLongitude() { return longitude; }

    // u
    @JsonProperty("SLU1")
    private String u = "?";
    public String getU() { return u; }
    
    // salb
    @JsonProperty("SALB")
    private String salb = "?";
    public String getSalb() { return salb; }

    // cn2bare
    @JsonProperty("SLRO")
    private String cn2bare = "?";
    public String getCn2bare() { return cn2bare; }
    
    // diffusConst
    @JsonIgnore
    public String diffusConst;
    public String getDiffusConst() { return diffusConst; }

    // diffusSlope
    @JsonIgnore
    public String diffusSlope;
    public String getDiffusSlope() { return diffusSlope; }
    
    // layers
    @JsonProperty("soilLayer")
    private SoilLayer[] layers;
    public SoilLayer[] getLayers() { return layers; }
    
    // log
    private String log;
    public String getLog() { return log; }
    
    
    
    // Needed for Jackson
    public Soil() {}
    
    
    
    // Initialise the instance.
    public void initialise() throws Exception {
        log = "";
        
        if (layers.length == 0)
           log += "  * Soil ERROR: No soil layers found\r\n";
        else {
            // calculate a thickness for each layer.
            double cumThickness = 0.0;
            for (int i = 0; i < layers.length; i++) {
                cumThickness = layers[i].initialise(cumThickness, i+1, layers.length);
            } 
            
            // Look for an organic carbon in the top layer but missing values in subsequent
            // deeper layers.
            if (!layers[0].getOrganicCarbon().equals("?")) {
                double TopOC = Double.valueOf(layers[0].getOrganicCarbon());
                boolean missingValuesFound = false;
                for (int i = 1; i < layers.length; i++) {
                    if (layers[i].getOrganicCarbon().equals("?"))
                        missingValuesFound = true;
                    layers[i].calculateOrganicCarbon(TopOC);
                }
                
                if (missingValuesFound)
                    log += "  * Soil ASSUMPTION: Missing organic carbon values have been estimated from the measured value in the top layer.";
            }
                
            
            log += "  * Soil ASSUMPTION: AirDry values are set to 0.5 of LL15 for all layers\r\n";
            
            for (int i = 0; i < layers.length; i++) {
                log += layers[i].getLog();
            }   
            log += "  * Soil ASSUMPTION: Crop LL values for all layers are set to LL15.\r\n";
            log += "  * Soil ASSUMPTION: Crop KL values decrease from 0.08 at the surface to 0.02 in the bottom layer.\r\n";
            log += "  * Soil ASSUMPTION: Crop XF values for all layers are set to 1.0.\r\n";
        }
        
        if ("?".equals(u)) {
            u = "6.0";
            log += "  * Soil ASSUMPTION: Missing U. Assuming a value of 6.0\r\n";
        }

        if ("?".equals(salb)) {
            salb = "0.13";
            log += "  * Soil ASSUMPTION: Missing SALB. Assuming a value of 0.13\r\n";
        }

        if ("?".equals(cn2bare)) {
            cn2bare = "73.0";
            log += "  * Soil ASSUMPTION: Missing CN2Bare. Assuming a value of 73.0\r\n";
        }
        
        if (getClassification().toLowerCase().contains("sand") ||
            getName().toLowerCase().contains("sand")) {
            log += "  * Soil ASSUMPTION: Sand soil type found. Assuming DiffusConst of 250 and DiffusSlope of 22\r\n";
            diffusConst = "250";
            diffusSlope = "22";
        }
        else if (getClassification().toLowerCase().contains("loam") ||
                 getName().toLowerCase().contains("loam")) {
            log += "  * Soil ASSUMPTION: Loam soil type found. Assuming DiffusConst of 88 and DiffusSlope of 35\r\n";
            diffusConst = "88";
            diffusSlope = "35";
        }
        else if (getClassification().toLowerCase().contains("clay") ||
                 getName().toLowerCase().contains("clay")) {
            log += "  * Soil ASSUMPTION: Clay soil type found. Assuming DiffusConst of 40 and DiffusSlope of 16\r\n";
            diffusConst = "40";
            diffusSlope = "16";
        }
        else {
            log += "  * Soil ASSUMPTION: No soil type info found. Assuming DiffusConst of 40 and DiffusSlope of 16\r\n";
            diffusConst = "40";
            diffusSlope = "16";
        }

        
        log += "  * Soil ASSUMPTION: RootCN set to a value of 45.0\r\n";
        log += "  * Soil ASSUMPTION: RootWt set to a value of 500.0\r\n";
        log += "  * Soil ASSUMPTION: FBiom values decrease from 0.04 at the surface to 0.01 in the bottom layer.\r\n";
        log += "  * Soil ASSUMPTION: FInert values increase from 0.4 at the surface to 0.9 in the bottom layer.\r\n";
    } 
      
      
}
