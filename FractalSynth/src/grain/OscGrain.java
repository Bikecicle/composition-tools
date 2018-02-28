package grain;

public class OscGrain extends Grain {
	
	private static final long serialVersionUID = 6203508575435333528L;
	
	public int freq;

	public OscGrain(float strt, float dur, float amp, int freq, float att, float dec, double xNorm, double yNorm) {
		super(Instrument.osc, strt, dur, amp, att, dec, xNorm, yNorm);
		this.freq = freq;                                    
	}

	public String statement() {
		return super.statement() + " " + freq + " " + att + " " + dec;
	}
}
