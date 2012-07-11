package org.agmip.translators.apsim.weather;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import java.util.Calendar;


public class APSIM {

	
	public static void generateWeatherFiles(File path, Weather s) throws Exception{
			if(s.shortName==null) throw new Exception("Cant create file. Station short name missing");
			
	        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(path,s.longName+".met"))));
	        br.write("!title: ");
	        br.write(s.shortName);
	        br.newLine();
	        br.write("tav = ");
	        br.write(s.averageTemperature);
	        br.newLine();
	        br.write("amp = ");
	        br.newLine();
	        br.write("year day radn maxt mint rain wind dewp vers rh \n");
	        br.write("()    ()   (MJ/m2) (oC)  (oC)  (mm)  (km)  (oC)   ()   (%)\n");

	        for(Record r : s.records){
        	    br.write(GetYear(r.w_date)+" ");
        	    br.write(GetDay(r.w_date)+" ");
        	    br.write(r.srad +" ");
        	    br.write(r.tmax +" ");
        	    br.write(r.tmin +" ");
        	    br.write(r.rain +" ");
        	    br.write(r.wind +" ");
        	    br.write(r.dewp +" ");
        	    br.write(r.vprs +" ");
        	    br.write(r.rhum +" ");
    	        br.newLine();
	        }
	        
	        br.close();
	    }
	

   public static String GetYear(String w_date) {
		String[] part = w_date.split("-");
		return part[0];
	}
	
   public static String GetDay(String w_date) {
		String[] part = w_date.split("-");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(part[2]));
        c.set(Calendar.MONTH, Integer.parseInt(part[1])-1);
        c.set(Calendar.YEAR, Integer.parseInt(part[0]));
        return c.get(Calendar.DAY_OF_YEAR) +"";
	}
   

}
