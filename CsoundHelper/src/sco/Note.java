package sco;

import java.util.HashMap;
import java.util.List;

import orc.Orchestra;

public class Note {
	
	public ParameterList params;
	
	public Note() {
		params = new ParameterList();
	}
	
	public void add(Param<?> param) {
		params.add(param);
	}
	
	public String read(List<HashMap<Integer, String>> paramMap) {
		String inst = params.find(Orchestra.INSTRUMENT).readValue();
		String s = "i " + inst;
		HashMap<Integer, String> map = paramMap.get(Integer.parseInt(inst));
		int p = 2;
		String name = map.get(p);
		while (name != null) { 
			s += " " + params.find(name).readValue();
			p++;
			name = map.get(p);
		}
		return s;
	}
}
