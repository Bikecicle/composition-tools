package grain;

import java.io.Serializable;

public abstract class Grain implements Serializable {
	
	private static final long serialVersionUID = -1945211813680404267L;
	
	public static final int DEFAULT_IID = 1;
	public static final float DEFAULT_DUR = 0.01f;
	public static final float DEFAULT_AMP = 0.01f;
	public static final float DEFAULT_ATT = 0.001f;
	public static final float DEFAULT_DEC = 0.001f;

	public int iID;
	public float strt;
	public float dur;
	public float amp;
	public float att;
	public float dec;

	public Grain(int iID, float strt, float dur, float amp, float att, float dec) {
		this.iID = iID;
		this.strt = strt;
		this.dur = dur;
		this.amp = amp;
		this.att = att;
		this.dec = dec;
	}

	public abstract String statement();
}
