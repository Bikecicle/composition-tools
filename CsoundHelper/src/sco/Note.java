package sco;

import java.util.HashMap;
import java.util.List;

import orc.Orchestra;

public class Note {
	
	public List<HashMap<Integer, String>> paramMap;
	public ParameterList params;
	
	public Note(List<HashMap<Integer, String>> paramMap) {
		this.paramMap = paramMap;
		params = new ParameterList();
	}
	
	public void add(Param<?> param) {
		params.add(param);
	}
	
	@Override
	public String toString() {
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
