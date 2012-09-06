package org.agmip.translators.apsim.util;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ioannis N. Athanasiadis, DUTh
 * @author Dean Holzworth, CSIRO
 * @since Jul 13, 2012
 * @see  http://loianegroner.com/2010/09/how-to-serialize-java-util-date-with-jackson-json-processor-spring-3-0/
 */
public class DateDeserializer extends JsonDeserializer<String> {

    private static final Logger LOG = LoggerFactory.getLogger(DateDeserializer.class);
    private static int lineNumber = 0;

    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        lineNumber++;
        LOG.info("Line number: "+lineNumber);
        try {
            String toParse = jp.getText();
            Date date = Converter.agmip.parse(toParse);
            return Converter.apsim.format(date);
        } catch (ParseException e) {
            throw new IOException(e);
        }


    }
}
