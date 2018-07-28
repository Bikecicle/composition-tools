package sco;

import orc.Orchestra;

public class Note {
	
	ParamList params;
	ParamMap map;
	
	public Note(ParamMap map) {
		params = new ParamList();
	}
	
	public void add(Param<?> param) {
		params.add(param);
	}
	
	public String toString() {
		int i = Integer.parseInt(params.find(Orchestra.INSTRUMENT).read());
		String s = "i " + i;
		int p = 2;
		String name = map.get(i, p);
		while (name != null) { 
			s += " " + params.find(name).read();
			p++;
			name = map.get(i, p);
		}
		return s;
	}
}
