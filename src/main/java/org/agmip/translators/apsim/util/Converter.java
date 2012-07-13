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
 * 
 * @author Ioannis N. Athanasiadis 
 */
public class Converter {

	public static final SimpleDateFormat agmip = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat apsim = new SimpleDateFormat("dd/MM/yyyy");

	@Deprecated
	public static void generateWeatherFiles(File path, Weather s)
			throws Exception {
		if (s.shortName == null)
			s.shortName = "default"; // throw new
		// Exception("Cant create file. Station short name missing");

		BufferedWriter br = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(path, s.shortName + ".met"))));
		br.write("!title: ");
		br.write(s.longName);
		br.newLine();
		br.write("tav = ");
		br.write(s.averageTemperature);
		br.newLine();
		br.write("amp = ");
		br.newLine();
		br.write("year day radn maxt mint rain wind dewp vers rh \n");
		br.write("()    ()   (MJ/m2) (oC)  (oC)  (mm)  (km)  (oC)   ()   (%)\n");

		for (DailyWeather r : s.records) {
			br.write(GetYear(r.date) + " ");
			br.write(GetDay(r.date) + " ");
			br.write(r.solarRadiation + " ");
			br.write(r.maxTemperature + " ");
			br.write(r.minTemperature + " ");
			br.write(r.rainfall + " ");
			br.write(r.windSpeed + " ");
			br.write(r.dewPoint + " ");
			br.write(r.vaporPressure + " ");
			br.write(r.relativeHumidity + " ");
			br.newLine();
		}

		br.close();
	}

	public static void generateAPSIMFile(File path, SimulationRun sim)
			throws Exception {
		// Initialise velocity
		Velocity.init();
		VelocityContext context = new VelocityContext();
		context.put("simulation", sim);

		Template template = Velocity
				.getTemplate("src\\main\\resources\\AgMIPTemplate.apsim");
		FileWriter F;
		try {

			F = new FileWriter(new File(path, sim.experimentName + ".apsim"));
			template.merge(context, F);
			F.close();

		} catch (IOException ex) {
			Logger.getLogger(ApsimOutput.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	public static void generateMetFile(File path, SimulationRun sim) throws Exception {
		// Initialise velocity
		Velocity.init();
		VelocityContext context = new VelocityContext();
		context.put("weather", sim.weather);

		Template template = Velocity
				.getTemplate("src/main/resources/template.met");
		FileWriter writer;
		writer = new FileWriter(new File(path, sim.experimentName + ".met"));
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



}
