package grain;

public class SampleGrain extends Grain {

	private static final long serialVersionUID = 9101116127464104179L;

	public float fMod;
	public float sStrt;
	public float sEnd;
	public int xFreq;
	public int xBand;
	public int fID;
	
	public SampleGrain(float strt, float dur, float amp, float att, float dec, float fMod, float sStrt, float sEnd, int xFreq, int xBand,
			int fID, double totalDur) {
		super(Instrument.sample.id, strt, dur, amp, att, dec);
		this.fMod = fMod;
		this.sStrt = sStrt;
		this.sEnd = sEnd;
		this.xFreq = xFreq;
		this.xBand = xBand;
		xNorm = 1.0 * strt / totalDur;
	}

	public String statement() {
		return "i" + iID + " " + strt + " " + dur + " " + amp + " " + att + " " + dec + " " + fMod + " " + sStrt + " "
				+ sEnd + " " + xFreq + " " + xBand + " " + fID;
	}
}
