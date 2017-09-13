package grain;

public class Grain {

	public static final int DEFAULT_IID = 1;
	public static final float DEFAULT_DUR = 0.01f;
	public static final float DEFAULT_ATT = 0.003f;
	public static final float DEFAULT_DEC = 0.003f;
	
	public int iID;
	public float strt;
	public float dur;
	public float amp;
	public int freq;
	public float att;
	public float dec;

	public Grain(int iID, float strt, float dur, float amp, int freq, float att,
			float dec) {
		this.iID = iID;
		this.strt = strt;
		this.dur = dur;
		this.amp = amp;
		this.freq = freq;
		this.att = att;
		this.dec = dec;
	}

	public Grain(float strt, int freq, float amp) {
		this.strt = strt;
		this.freq = freq;
		this.amp = amp;
	}

	public String statement() {
		return "i" + iID + " " + strt + " " + dur + " " + amp + " "
				+ freq + " " + att + " " + dec;
	}
}
