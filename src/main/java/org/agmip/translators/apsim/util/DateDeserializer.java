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
 */
public class DateDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        try {
            String toParse = jp.getText();
            Date date = Util.agmip.parse(toParse);
            return Util.apsim.format(date);
        } catch (ParseException e) {
            throw new IOException(e);
        }


    }
}
