package org.agmip.translators.apsim.readers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.agmip.translators.apsim.core.Soil;
import org.agmip.translators.apsim.core.SoilLayer;

import com.ximpleware.AutoPilot;

/**
 * Helper class to parse an APSIM XML file and extract all unique soils.
 * 
 * @author Ioannis N. Athanasiadis
 */
public class SoilReader extends VTDReader{
	public SoilReader(String file) {
		super(file,"(//soil[not(@shortcut)])");
	}
	
	public SoilReader(String file, String path){
		super(file, path);
	}


	public List<Soil> read() throws Exception{
		AutoPilot a = xpath("");
		int count = 1;
		List<Soil> soils = new ArrayList<Soil>();
		Set<String> names = new HashSet<String>();

		while (a.evalXPath() != -1) {
			Soil s = read("["+count+"]");
			String name = s.getName();
			name =  names.contains(name)? name+count : name;
			names.add(name);
			s.setName(name);			
			soils.add(s);
		    count++;
		}
		return soils;
	}
	
	
	public Soil read(String path) throws Exception{
		Soil soil = new Soil();
	    
		soil.setCn2bare(xPathText(path+"/SoilWat/Cn2Bare"));
		soil.setDiffusConst(xPathText(path+"/SoilWat/DiffusConst"));
		soil.setDiffusSlope(xPathText(path+"/SoilWat/DiffusSlope"));
		soil.setId(xPathText(path+"/@name"));
		soil.setLatitude(xPathText(path+"/Latitude"));
		soil.setLongitude(xPathText(path+"/Longitude"));
		soil.setName(xPathText(path+"/@name"));
		soil.setSalb(xPathText(path+"/SoilWat/Salb")); 
		soil.setSite(xPathText(path+"/Site"));
		soil.setSource(xPathText(path+"/DataSource"));
		soil.setU(xPathText(path+"/SoilWat/SummerU"));
		
//		TODO: Deal with soil classification.
//		String[] soilClass = get(prefix+"/Comment[1]").split(":");			
//		soil.setClassification(soilClass.length==2?soilClass[1].trim():soilClass[0].trim());

		int soilLayers = count(path+"/Water/Layer");
		 

		SoilLayer[] layers = new SoilLayer[soilLayers]; 
		soil.setLayers(layers);
		for(int l=1; l<=soilLayers; l++) {
			SoilLayer layer = new SoilLayer();
			layer.setBulkDensity(xPathText(path+"/Water/Layer["+l+"]/BD"));
			layer.setDrainedUpperLimit(xPathText(path+"/Water/Layer["+l+"]/DUL"));
			layer.setLowerLimit(xPathText(path+"/Water/Layer["+l+"]/LL15"));
			layer.setOrganicCarbon(xPathText(path+"/SoilOrganicMatter/Layer["+l+"]/OC"));
			layer.setPh(xPathText(path+"/Analysis/Layer["+l+"]/PH"));
			layer.setSaturation(xPathText(path+"/Water/Layer["+l+"]/SAT"));
			layer.setSwcon(xPathText(path+"/SoilWat/Layer["+l+"]/SWCON"));
			layer.setThickness(xPathText(path+"/Water/Layer["+l+"]/Thickness"));
			
			layers[l-1]=layer;
		}


		Double sum = 0.0;
		for(int k=0;k<layers.length;k++){
			sum = sum + Double.parseDouble(layers[k].getThickness())/10;
			layers[k].setBottomDepth(sum.toString());
		}
		return soil;
	}

}
