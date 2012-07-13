package org.agmip.translators.apsim.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 * @see  http://loianegroner.com/2010/09/how-to-serialize-java-util-date-with-jackson-json-processor-spring-3-0/
 */

	public class DateDeserializer extends JsonDeserializer<String>{			 
		 
		    @Override
		    public String deserialize(JsonParser jp, DeserializationContext ctxt)
		            throws IOException, JsonProcessingException {

						try {
							Date date = Converter.agmip.parse(jp.getText());
							return Converter.apsim.format(date);
						} catch (ParseException e) {
							throw new IOException(e);
						}
				
		    	     
		    }
		       

	}

