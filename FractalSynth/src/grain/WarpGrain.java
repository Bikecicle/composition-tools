package grain;

import sco.ArrayParam;
import sco.Note;
import sco.Param;
import sco.ParamMap;

public class WarpGrain extends Grain {

	private static final long serialVersionUID = 3055216104560554640L;

	public static final int TIE_DUR = -1;

	public int ifreq;
	public int iband;
	public int ifn;
	public float smin;
	public float smax;
	public Float[] segments;
	public float slen;
	public int id;

	public WarpGrain(float strt, float amp, int ifreq, int iband, int ifn, float smin, float smax, Float[] segments,
			float slen, int id) {
		super(GrainType.warp, strt, TIE_DUR, amp, 0, 0);
		this.ifn = ifn;
		this.ifreq = ifreq;
		this.iband = iband;
		this.smin = smin;
		this.smax = smax;
		this.segments = segments;
		this.slen = slen;
		this.id = id;
	}

	@Override
	public String statement() {
		String stmt = "";
		stmt += "i " + gType.id + "." + id + " " + strt + " " + dur + " " + amp + " "
				+ String.format("%.6f", segments[0]) + " " + String.format("%.6f", segments[1]) + " " + slen + " "
				+ ifreq + " " + iband + " " + ifn;
		for (int i = 1; i < segments.length - 1; i++) {
			float ts = segments[i];
			float te = segments[i + 1];
			float sstrt = strt + slen * i;
			float sdur = 0;
			if (i < segments.length - 2) {
				sdur = dur;
			}
			stmt += "\ni " + gType.id + "." + id + " " + sstrt + " " + sdur + " " + amp + " " + ts + " " + te + " " + slen;
		}
		return stmt;
	}
	
	@Override
	public Note getNote(ParamMap paramMap) {
		Note note = super.getNote(paramMap);
		note.add(new Param<Integer>("ifn", ifn));
		note.add(new Param<Integer>("ifreq", ifreq));
		note.add(new Param<Integer>("iband", iband));
		note.add(new Param<Float>("smin", smin));
		note.add(new Param<Float>("smax", smax));
		note.add(new ArrayParam<Float>("segments", segments));
		note.add(new Param<Float>("slen", slen));
		note.add(new Param<Integer>("id", id));
		return note;
	}

}
