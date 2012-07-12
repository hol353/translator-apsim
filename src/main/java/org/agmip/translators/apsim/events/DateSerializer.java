package org.agmip.translators.apsim.events;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

// http://loianegroner.com/2010/09/how-to-serialize-java-util-date-with-jackson-json-processor-spring-3-0/

public class DateSerializer extends JsonSerializer<Date>{
		 
	    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	 
	    @Override
	    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
	            throws IOException, JsonProcessingException {
	 
	        String formattedDate = dateFormat.format(date);
	 
	        gen.writeString(formattedDate);
	    }
	       

}
