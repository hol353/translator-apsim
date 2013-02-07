package org.agmip.translators.apsim.readers;

import java.util.List;

import com.ximpleware.AutoPilot;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathParseException;

public abstract class VTDReader {
	protected final VTDNav vn;
	private String prefix;

	
	public VTDReader(String file){
		final VTDGen vg = new VTDGen();
		vg.parseFile(file, false);
		vn = vg.getNav();
		this.prefix="";
	}
	
	public VTDReader(String file, String prefix){
		this(file);
		this.prefix = prefix;
	}
	
	
	public AutoPilot xpath(String xpath) throws Exception{
		AutoPilot ap = new AutoPilot(vn);
		ap.selectXPath(prefix+xpath);
		return ap;
	}
	
	public  String xpath(int count, String path) throws XPathParseException{
		AutoPilot ap = new AutoPilot(vn);
		ap.selectXPath(prefix+"["+count+"]"+path);
		return ap.evalXPathToString();
	}
	
	public int count(String xpath) throws Exception{
		AutoPilot ap = new AutoPilot(vn);
		ap.selectXPath("count("+prefix+xpath+")");
		return (int) ap.evalXPathToNumber(); 
	}
	
	abstract public<T> List<T> read() throws Exception;
	
	
}
