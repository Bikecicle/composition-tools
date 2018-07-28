package orc;

import java.util.ArrayList;
import java.util.List;

import sco.ParamMap;

public class Orchestra {

	public static final String INSTRUMENT = "inst";
	public static final String START = "strt";
	public static final String DURATION = "dur";

	public static final Value SR = new Constant<String>("sr");
	public static final Value KSMPS = new Constant<String>("ksmps");
	public static final Value NCHNLS = new Constant<String>("nchnls");
	public static final Value DBFS = new Constant<String>("0dbfs");

	int sr;
	int ksmps;
	int nchnls;
	float dbfs;
	List<Instrument> instruments;

	public Orchestra(int sr, int ksmps, int nchnls, float dbfs) {
		this.sr = sr;
		this.ksmps = ksmps;
		this.nchnls = nchnls;
		this.dbfs = dbfs;
		instruments = new ArrayList<>();
	}

	public void add(Instrument instrument) {
		instruments.add(instrument);
	}

	public ParamMap mapParams() {
		ParamMap map = new ParamMap();
		for (Instrument inst : instruments) {
			for (Input input : inst.inputs) {
				input.mapInput(inst.id, map);
			}
			map.put(inst.id, 1, INSTRUMENT);
			map.put(inst.id, 2, START);
			map.put(inst.id, 3, DURATION);
		}
		System.out.println(map);
		return map;
	}

	public String toString() {
		String s = "sr = " + sr + "\n" + "ksmps = " + ksmps + "\n" + "nchnls = " + nchnls + "\n" + "0dbfs = " + dbfs
				+ "\n";
		for (int i = 0; i < instruments.size(); i++) {
			s += "\n" + "instr " + (i + 1) + "\n" + instruments.get(i) + "\n" + "endin\n";
		}
		return s;
	}
}
