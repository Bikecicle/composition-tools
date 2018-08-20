package orc;

import java.util.ArrayList;
import java.util.List;

import sco.ParamMap;

public class Orchestra {

	public static final String INSTRUMENT = "inst";
	public static final String START = "strt";
	public static final String DURATION = "dur";

	public static final Statement SR = new Constant<String>("sr");
	public static final Statement KSMPS = new Constant<String>("ksmps");
	public static final Statement NCHNLS = new Constant<String>("nchnls");
	public static final Statement DBFS = new Constant<String>("0dbfs");

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
				map.put(inst.id, input.index, input.paramName);
			}
		}
		return map;
	}

	@Override
	public String toString() {
		String s = "sr = " + sr + "\r\n" + "ksmps = " + ksmps + "\r\n" + "nchnls = " + nchnls + "\r\n" + "0dbfs = " + dbfs
				+ "\r\n";
		for (int i = 0; i < instruments.size(); i++) {
			s += "\r\n" + "instr " + (i + 1) + "\r\n" + instruments.get(i) + "endin\r\n";
		}
		return s;
	}
}
