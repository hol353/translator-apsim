package org.agmip.translators.apsim.util;

import java.io.IOException;
import java.text.ParseException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

// http://loianegroner.com/2010/09/how-to-serialize-java-util-date-with-jackson-json-processor-spring-3-0/

public class DateSerializer extends JsonSerializer<String>{
		 
	 
	    @Override
	    public void serialize(String date, JsonGenerator gen, SerializerProvider provider)
	            throws IOException, JsonProcessingException {
	 
	        String formattedDate="";
			try {
				formattedDate = Converter.agmip.format(Converter.apsim.parse(date));
			} catch (ParseException e) {
				throw new IOException(e);
			}
	 	        		
	        gen.writeString(formattedDate);
	    }
	       

}
