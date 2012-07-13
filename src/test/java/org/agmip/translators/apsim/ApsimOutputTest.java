package org.agmip.translators.apsim;

import java.io.File;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Scanner;

import junit.framework.TestCase;

import org.agmip.util.JSONAdapter;
import org.agmip.util.MapUtil;
import org.junit.Test;


public class ApsimOutputTest extends TestCase {

	@Test
	public void testGenerate() throws Exception{
		URL resource = this.getClass().getResource("/simulation.json");
		String json = new Scanner(new File(resource.getPath()), "UTF-8").useDelimiter("\\A").next();
		LinkedHashMap<String, Object> map = MapUtil.decompressAll(JSONAdapter.fromJSON(json));
		ApsimOutput ao = new ApsimOutput();
		ao.writeFile("src/test/resources/gen/",map);
	}

	
}
