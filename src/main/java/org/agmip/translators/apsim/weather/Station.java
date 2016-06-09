package org.agmip.translators.apsim.weather;

import java.util.HashSet;
import java.util.Set;


public class Station {
	String wsta_site;
	String wsta_lat;
	String wsta_long;
	String wsta_insi;
	String tav;
	Set<Record> records = new HashSet<Record>();
	public String getWsta_site() {
		return wsta_site;
	}
	public void setWsta_site(String wsta_site) {
		this.wsta_site = wsta_site;
	}
	public String getWsta_lat() {
		return wsta_lat;
	}
	public void setWsta_lat(String wsta_lat) {
		this.wsta_lat = wsta_lat;
	}
	public String getWsta_long() {
		return wsta_long;
	}
	public void setWsta_long(String wsta_long) {
		this.wsta_long = wsta_long;
	}
	public String getWsta_insi() {
		return wsta_insi;
	}
	public void setWsta_insi(String wsta_insi) {
		this.wsta_insi = wsta_insi;
	}
	public String getTav() {
		return tav;
	}
	public void setTav(String tav) {
		this.tav = tav;
	}
	public Set<Record> getRecords() {
		return records;
	}
	public void setRecords(Set<Record> records) {
		this.records = records;
	}
	
	
	
	
}

