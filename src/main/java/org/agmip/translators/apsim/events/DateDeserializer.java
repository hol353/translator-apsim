package org.agmip.translators.apsim.events;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

	// http://loianegroner.com/2010/09/how-to-serialize-java-util-date-with-jackson-json-processor-spring-3-0/

	public class DateDeserializer extends JsonDeserializer<Date>{			 
		    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		 
		    @Override
		    public Date deserialize(JsonParser jp, DeserializationContext ctxt)
		            throws IOException, JsonProcessingException {

						try {
							return dateFormat.parse(jp.nextValue().asString());
						} catch (ParseException e) {
							throw new IOException(e);
						}
				
		    	     
		    }
		       

	}

