package grain;

public class OscGrain extends Grain {
	
	private static final long serialVersionUID = 6203508575435333528L;
	
	public int freq;
	public int fMin;
	public int fMax;

	public OscGrain(float strt, float dur, float amp, int freq, float att, float dec, int fMin, int fMax, double totalDur) {
		super(Instrument.osc, strt, dur, amp, att, dec);
		this.freq = freq;
		xNorm = 1.0 * strt / totalDur;
		yNorm = 1.0 * (freq - fMin) / (fMax - fMin);                                      
	}

	public String statement() {
		return "i" + gType.id + " " + strt + " " + dur + " " + amp + " " + freq + " " + att + " " + dec;
	}
}
