package org.agmip.translators.apsim;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.agmip.core.types.TranslatorOutput;
import org.agmip.util.MapUtil;
import org.agmip.util.MapUtil.BucketEntry;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;




public class ApsimOutput implements TranslatorOutput {
    
    
    public static class SoilLayer {
        private double sllb;
        private double thickness;
        private double slbdm;
        private double airDry;
        private double slll;
        private double sldul;
        private double slsat; 
        private double sloc;
        private double slphw;
    
        public double getThickness() {
            return thickness;
        }
        
        public void setThickness(double value) {
            thickness = value;    
        }
        
        public double getSlbdm() {
            return slbdm;
        }
        
        public void setSlbdm(double value) {
            slbdm = value;    
        }
        
        public double getAirDry() {
            return airDry;
        }
        
        public void setAirDry(double value) {
            airDry = value;    
        }     
        
        public double getSlll() {
            return slll;
        }
        
        public void setSlll(double value) {
            slll = value;    
        }          
        
        public double getSldul() {
            return sldul;
        }
        
        public void setSldul(double value) {
            sldul = value;    
        }           
        
        public double getSlsat() {
            return slsat;
        }
        
        public void setSlsat(double value) {
            slsat = value;    
        }  
        public double getSloc() {
            return sloc;
        }
        
        public void setSloc(double value) {
            sloc = value;    
        }  
        public double getSlphw() {
            return slphw;
        }
        
        public void setSlphw(double value) {
            slphw = value;    
        }          
        
        public void ReadFrom(Map layer) {
            sllb = Double.parseDouble(MapUtil.getValueOr(layer, "sllb", "-99"));
            slbdm = Double.parseDouble(MapUtil.getValueOr(layer, "slbdm", "-99"));
            slll = Double.parseDouble(MapUtil.getValueOr(layer, "slll", "-99"));
            sldul = Double.parseDouble(MapUtil.getValueOr(layer, "sldul", "-99"));
            slsat = Double.parseDouble(MapUtil.getValueOr(layer, "slsat", "-99"));
            sloc = Double.parseDouble(MapUtil.getValueOr(layer, "sloc", "-99"));
            slphw = Double.parseDouble(MapUtil.getValueOr(layer, "slphw", "-99"));
        
            airDry = slll - (slll * 0.05);
        }
        
        public double calcThickness(double cumThickness) {
            thickness = sllb * 10 - cumThickness;
            return sllb * 10;
        }
    }
    
    public void writeFile(String filePath, Map input) {
        Velocity.init();
        VelocityContext context = new VelocityContext();
        
        // Weather variable.
        BucketEntry weather = MapUtil.getBucket(input, "weather").get(0);
        context.put( "wst_insi", MapUtil.getValueOr(weather.getValues(), "wst_insi", "?"));
        
        // Soil metadata
        context.put( "classification", MapUtil.getValueOr(weather.getValues(), "classification", "?"));
        context.put( "soil_site", MapUtil.getValueOr(weather.getValues(), "soil_site", "?"));
        context.put( "soil_name", MapUtil.getValueOr(weather.getValues(), "soil_name", "?"));
        context.put( "sl_source", MapUtil.getValueOr(weather.getValues(), "sl_source", "?"));
        context.put( "soil_lat", MapUtil.getValueOr(weather.getValues(), "soil_lat", "?"));
        context.put( "soil_long", MapUtil.getValueOr(weather.getValues(), "soil_long", "?"));
             
        // Setup the soil layers.
        SoilLayer[] layers;
        BucketEntry soil = MapUtil.getBucket(input, "soil").get(0);
        ArrayList data = soil.getDataList();
        layers = new SoilLayer[data.size()];
        double cumThickness = 0.0;
        for (int i = 0; i < data.size(); i++) {
            layers[i] = new SoilLayer();
            layers[i].ReadFrom((Map) data.get(i));
            cumThickness = layers[i].calcThickness(cumThickness);
        }            
        context.put( "layers", layers);

        
        
        // Write template.
        Template template = Velocity.getTemplate("src\\main\\resources\\AgMIPTemplate.apsim");
        FileWriter F;
        try {
            
            F = new FileWriter("Test.apsim");
            template.merge( context, F );
            F.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ApsimOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        
    }
}
