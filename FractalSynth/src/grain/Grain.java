package grain;

import java.io.Serializable;

public class Grain implements Serializable {

	private static final long serialVersionUID = -7681866418492754276L;

	public static final int DEFAULT_IID = 1;
	public static final float DEFAULT_DUR = 0.01f;
	public static final float DEFAULT_AMP = 0.01f;
	public static final int DEFAULT_FREQ = 60;
	public static final float DEFAULT_ATT = 0.001f;
	public static final float DEFAULT_DEC = 0.001f;

	public int iID;
	public float strt;
	public float dur;
	public float amp;
	public int freq;
	public float att;
	public float dec;

	public Grain(int iID, float strt, float dur, float amp, int freq, float att, float dec) {
		this.iID = iID;
		this.strt = strt;
		this.dur = dur;
		this.amp = amp;
		this.freq = freq;
		this.att = att;
		this.dec = dec;
	}

	public String statement() {
		return "i" + iID + " " + strt + " " + dur + " " + amp + " " + freq + " " + att + " " + dec;
	}
}
