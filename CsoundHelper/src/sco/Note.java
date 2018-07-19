package sco;

import orc.Orchestra;

public class Note {
	
	public ParamList params;
	
	public Note() {
		params = new ParamList();
	}
	
	public void add(Param<?> param) {
		params.add(param);
	}
	
	public String read(ParamMap map) {
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
