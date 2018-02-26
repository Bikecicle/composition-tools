package grain;

public class SampleGrain extends Grain {

	private static final long serialVersionUID = 9101116127464104179L;

	public float fMod;
	public float sStrt;
	public int freq;
	public int band;
	public int fID;
	
	public SampleGrain(float strt, float dur, float amp, float att, float dec, float fMod, float sStrt, int freq, int band,
			int fID, int fMin, int fMax, double totalDur) {
		super(Instrument.sample, strt, dur, amp, att, dec);
		this.fMod = fMod;
		this.sStrt = sStrt;
		this.freq = freq;
		this.band = band;
		this.fID = fID;
		xNorm = 1.0 * strt / totalDur;
		yNorm = 1.0 * (freq - fMin) / (fMax - fMin);  
	}

	public String statement() {
		return super.statement() + " " + att + " " + dec + " " + fMod + " " + freq + " " + band + " " + fID + " " + sStrt;
	}
}
