package orc;

import java.util.ArrayList;
import java.util.List;

import sco.ParamMap;

public class Orchestra {
	
	public static final String INSTRUMENT = "inst";
	public static final String START = "strt";
	public static final String DURATION = "dur";

	public int sr;
	public int ksmps;
	public int nchnls;
	public float dbfs0;
	public List<Instrument> instruments;

	int pIndex;
	
	public Orchestra(int sr, int ksmps, int nchnls, float dbfs0) {
		this.sr = sr;
		this.ksmps = ksmps;
		this.nchnls = nchnls;
		this.dbfs0 = dbfs0;
		instruments = new ArrayList<>();
		pIndex = 4;
	}
	
	public void add(Instrument instrument) {
		instruments.add(instrument);
	}
	
	public ParamMap mapParams() {
		ParamMap map = new ParamMap();
		for (int i = 0; i < instruments.size(); i++) {
			instruments.get(i).mapInputs(map);
			map.put(i + 1, 1, INSTRUMENT);
			map.put(i + 1, 2, START);
			map.put(i + 1, 3, DURATION);
		}
		return map;
	}
	
	public String read() {
		String s = "sr = " + sr + "\n" + 
				"ksmps = " + ksmps + "\n" +
				"nchnls = " + nchnls + "\n" +
				"0dbfs = " + dbfs0 + "\n";
		for (int i = 0; i < instruments.size(); i++) {
			s += "\n" +
					"instr " + (i + 1) + "\n" +
					instruments.get(i).read() + "\n" +
					"endin\n";
		}
		return s;
	}
}
