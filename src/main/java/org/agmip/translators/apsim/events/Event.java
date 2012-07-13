package org.agmip.translators.apsim.events;

import org.agmip.translators.apsim.util.DateDeserializer;
import org.agmip.translators.apsim.util.DateSerializer;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 */

    @JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    property = "event")  
	@JsonSubTypes({  
	    @Type(value = Irrigation.class, name = "irrigation"),
	    @Type(value = Planting.class, name = "planting"), 
	    @Type(value = Fertilizer.class, name = "fertilizer") ,
	    @Type(value = OrganicMatter.class, name = "organic_matter"), 
	    @Type(value = Tillage.class, name = "tillage"), 
	    @Type(value = Chemical.class, name = "chemical"), 
	    @Type(value = Harvest.class, name = "harvest"),  
	    })  
    @JsonIgnoreProperties(ignoreUnknown = true)

public abstract class Event {
	
	@JsonSerialize(using=DateSerializer.class)
	@JsonDeserialize(using=DateDeserializer.class)
	String date;
	

	
	abstract String getApsimAction();

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	

}
