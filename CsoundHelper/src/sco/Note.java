package sco;

import orc.Orchestra;

public class Note {
	
	ParamList params;
	ParamMap map;
	
	public Note(ParamMap map) {
		params = new ParamList();
		this.map = map;
	}
	
	public Note(int inst, float strt, float dur, ParamMap map) {
		this(map);
		add(new Param<Integer>(Orchestra.INSTRUMENT, inst));
		add(new Param<Float>(Orchestra.START, strt));
		add(new Param<Float>(Orchestra.DURATION, dur));
	}
	
	public void add(Param<?> param) {
		params.add(param);
	}
	
	public String toString() {
		int i = 1;
		try {
			i = Integer.parseInt(params.find(Orchestra.INSTRUMENT).read());
		} catch (NumberFormatException | ParameterNotFoundException e1) {
			e1.printStackTrace();
		}
		String s = "i " + i;
		int p = 2;
		String name = map.get(i, p);
		while (name != null) { 
			try {
				s += " " + params.find(name).read();
			} catch (ParameterNotFoundException e) {
				e.printStackTrace();
			}
			p++;
			name = map.get(i, p);
		}
		return s;
	}
}
