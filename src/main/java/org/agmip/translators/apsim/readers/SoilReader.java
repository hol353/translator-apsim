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


	public List<Soil> read() throws Exception{
		AutoPilot a = xpath("");
		int count = 1;
		List<Soil> soils = new ArrayList<Soil>();
		Set<String> names = new HashSet<String>();

		while (a.evalXPath() != -1) {
			Soil soil = new Soil();
			String name = xpath(count,"/@name");
			name = names.contains(name)? name+count : name;
			names.add(name);
		    
			soil.setCn2bare(xpath(count,"/SoilWat/Cn2Bare"));
			soil.setDiffusConst(xpath(count,"/SoilWat/DiffusConst"));
			soil.setDiffusSlope(xpath(count,"/SoilWat/DiffusSlope"));
			soil.setId(name);
			soil.setLatitude(xpath(count,"/Latitude"));
			soil.setLongitude(xpath(count,"/Longitude"));
			soil.setName(xpath(count,"/@name"));
			soil.setSalb(xpath(count,"/SoilWat/Salb")); 
			soil.setSite(xpath(count,"/Site"));
			soil.setSource(xpath(count,"/DataSource"));
			soil.setU(xpath(count,"/SoilWat/SummerU"));
			
//			TODO: Deal with soil classification.
//			String[] soilClass = get(prefix+"/Comment[1]").split(":");			
//			soil.setClassification(soilClass.length==2?soilClass[1].trim():soilClass[0].trim());

			int soilLayers = count("["+count+"]/Water/Layer");
			 

			SoilLayer[] layers = new SoilLayer[soilLayers]; 
			soil.setLayers(layers);
			for(int l=1; l<=soilLayers; l++) {
				SoilLayer layer = new SoilLayer();
				layer.setBulkDensity(xpath(count,"/Water/Layer["+l+"]/BD"));
				layer.setDrainedUpperLimit(xpath(count,"/Water/Layer["+l+"]/DUL"));
				layer.setLowerLimit(xpath(count,"/Water/Layer["+l+"]/LL15"));
				layer.setOrganicCarbon(xpath(count,"/SoilOrganicMatter/Layer["+l+"]/OC"));
				layer.setPh(xpath(count,"/Analysis/Layer["+l+"]/PH"));
				layer.setSaturation(xpath(count,"/Water/Layer["+l+"]/SAT"));
				layer.setSwcon(xpath(count,"/SoilWat/Layer["+l+"]/SWCON"));
				layer.setThickness(xpath(count,"/Water/Layer["+l+"]/Thickness"));
				
				layers[l-1]=layer;
			}


			Double sum = 0.0;
			for(int k=0;k<layers.length;k++){
				sum = sum + Double.parseDouble(layers[k].getThickness())/10;
				layers[k].setBottomDepth(sum.toString());
			}
		    count++;
			soils.add(soil);			
		}
		return soils;
	}

}
