package grain;

import sco.Note;
import sco.Param;
import sco.ParamMap;

public class SampleGrain extends Grain {

	private static final long serialVersionUID = 9101116127464104179L;

	public float att;
	public float dec;
	public float fMod;
	public float sStrt;
	public int freq;
	public int band;
	public int fID;
	
	public SampleGrain(float strt, float dur, float amp, float att, float dec, float fMod, float sStrt, int freq, int band,
			int fID, double xNorm, double yNorm) {
		super(GrainType.sample, strt, dur, amp, xNorm, yNorm);
		this.att = att;
		this.dec = dec;
		this.fMod = fMod;
		this.sStrt = sStrt;
		this.freq = freq;
		this.band = band;
		this.fID = fID;
	}

	public String statement() {
		return super.statement() + " " + att + " " + dec + " " + fMod + " " + freq + " " + band + " " + fID + " " + sStrt;
	}
	
	@Override
	public Note getNote(ParamMap paramMap) {
		Note note = super.getNote(paramMap);
		note.add(new Param<Float>("att", att));
		note.add(new Param<Float>("dec", dec));
		note.add(new Param<Float>("fMod", fMod));
		note.add(new Param<Float>("sStrt", sStrt));
		note.add(new Param<Integer>("freq", freq));
		note.add(new Param<Integer>("band", band));
		note.add(new Param<Integer>("fID", fID));
		return note;
	}
}
