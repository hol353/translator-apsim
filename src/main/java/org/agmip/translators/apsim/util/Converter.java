package org.agmip.translators.apsim.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.agmip.translators.apsim.ApsimOutput;
import org.agmip.translators.apsim.core.DailyWeather;
import org.agmip.translators.apsim.core.SimulationRun;
import org.agmip.translators.apsim.core.Weather;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

public class Converter {

	public static final SimpleDateFormat agmip = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat apsim = new SimpleDateFormat("dd/MM/yyyy");
	public static void generateAPSIMFile(File path, SimulationRun sim)
			throws Exception {
		path.mkdirs();
		File file = new File(path, sim.experimentName + ".apsim");
		file.createNewFile();
		Velocity.init();
		VelocityContext context = new VelocityContext();
		context.put("simulation", sim);
                sim.initialise();
		Template template = Velocity
				.getTemplate("src\\main\\resources\\AgMIPTemplate.apsim");
		FileWriter writer;
		try {

			writer = new FileWriter(file);
			template.merge(context, writer);
			writer.close();

		} catch (IOException ex) {
			Logger.getLogger(ApsimOutput.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public static void generateMetFile(File path, SimulationRun sim) throws Exception {
		path.mkdirs();
		File file = new File(path, sim.experimentName + ".met");
		file.createNewFile();
		Velocity.init();
		VelocityContext context = new VelocityContext();
		context.put("weather", sim.weather);
			Template template = Velocity.getTemplate("src/main/resources/template.met");
			FileWriter writer;
			writer = new FileWriter(file);
			template.merge(context, writer);
			writer.close();
		

	}

	public static String GetYear(String agmipDate) throws ParseException {
		Date date = apsim.parse(agmipDate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR) + "";
	}

	public static String GetDay(String agmipDate) throws ParseException {
		Date date = apsim.parse(agmipDate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_YEAR) + "";
	}

	public static Calendar toCalendar(String apsimDate) throws ParseException {
                Calendar c = Calendar.getInstance();
		c.setTime(apsim.parse(apsimDate));
		return c;
	}
        
	public static String toApsimDateString(Calendar date) throws ParseException {
		return apsim.format(date.getTime());
	}

}
