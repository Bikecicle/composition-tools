package grain;

public class SampleGrain extends Grain {

	private static final long serialVersionUID = 9101116127464104179L;

	public float fMod;
	public float sStrt;
	public float sEnd;
	public int freq;
	public int band;
	public int fID;
	
	public SampleGrain(float strt, float dur, float amp, float att, float dec, float fMod, float sStrt, int freq, int band,
			int fID, int fMin, int fMax, double totalDur) {
		super(Instrument.sample, strt, dur, amp, att, dec);
		this.fMod = fMod;
		this.sStrt = sStrt;
		this.sEnd = sStrt + dur;
		this.freq = freq;
		this.band = band;
		xNorm = 1.0 * strt / totalDur;
		yNorm = 1.0 * (freq - fMin) / (fMax - fMin);  
	}

	public String statement() {
		return "i" + gType.id + " " + strt + " " + dur + " " + amp + " " + att + " " + dec + " " + fMod + " " + sStrt + " "
				+ sEnd + " " + freq + " " + band + " " + fID;
	}
}
