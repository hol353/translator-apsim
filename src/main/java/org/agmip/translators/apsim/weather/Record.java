package org.agmip.translators.apsim.weather;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Record {
	
	String w_date ="?";
	String srad="?"; //radn
	String tmax="?"; //maxt
	String tmin="?"; //mint
	String rain="?";
	
	String wind="?";
	String dewp="?";
	String vprs="?"; //vers
	String rhum="?"; //rh
	public String getW_date() {
		return w_date;
	}
	public void setW_date(String w_date) {
		this.w_date = w_date;
	}
	public String getSrad() {
		return srad;
	}
	public void setSrad(String srad) {
		this.srad = srad;
	}
	public String getTmax() {
		return tmax;
	}
	public void setTmax(String tmax) {
		this.tmax = tmax;
	}
	public String getTmin() {
		return tmin;
	}
	public void setTmin(String tmin) {
		this.tmin = tmin;
	}
	public String getRain() {
		return rain;
	}
	public void setRain(String rain) {
		this.rain = rain;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getDewp() {
		return dewp;
	}
	public void setDewp(String dewp) {
		this.dewp = dewp;
	}
	public String getVprs() {
		return vprs;
	}
	public void setVprs(String vprs) {
		this.vprs = vprs;
	}
	public String getRhum() {
		return rhum;
	}
	public void setRhum(String rhum) {
		this.rhum = rhum;
	}
	


}
