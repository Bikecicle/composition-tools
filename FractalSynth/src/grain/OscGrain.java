package grain;

import sco.Note;
import sco.Param;
import sco.ParamMap;

public class OscGrain extends Grain {
	
	private static final long serialVersionUID = 6203508575435333528L;
	
	public float att;
	public float dec;
	public int freq;

	public OscGrain(float strt, float dur, float amp, int freq, float att, float dec, double xNorm, double yNorm) {
		super(GrainType.osc, strt, dur, amp, xNorm, yNorm);
		this.att = att;
		this.dec = dec;
		this.freq = freq;                                    
	}

	public String statement() {
		return super.statement() + " " + freq + " " + att + " " + dec;
	}

	@Override
	public Note getNote(ParamMap paramMap) {
		Note note = super.getNote(paramMap);
		note.add(new Param<Float>("rise", att));
		note.add(new Param<Float>("dec", dec));
		note.add(new Param<Integer>("freq", freq));
		return note;
	}
}
