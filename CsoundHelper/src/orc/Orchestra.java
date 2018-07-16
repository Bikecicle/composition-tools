package orc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public List<HashMap<Integer, String>> mapParams() {
		List<HashMap<Integer, String>> paramMap = new ArrayList<>();
		for (int i = 0; i < instruments.size(); i++) {
			HashMap<Integer, String> instMap = instruments.get(i).mapInputs();
			instMap.put(1, INSTRUMENT);
			instMap.put(2, START);
			instMap.put(3, DURATION);
			paramMap.add(instMap);
		}
		return paramMap;
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
