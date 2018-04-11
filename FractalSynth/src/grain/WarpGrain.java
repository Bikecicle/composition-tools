package grain;

public class WarpGrain extends Grain {

	private static final long serialVersionUID = 3055216104560554640L;

	public static final int TIE_DUR = -1;

	public int iwsize; // window size
	public int irandw;
	public int ioverlap; // window overlap
	public int ifreq;
	public int iband;
	public int ifn1;
	public int ifn2;
	public float[] segments;
	public float slen;
	public int id;

	public WarpGrain(float strt, float amp, int iwsize, int irandw, int ioverlap, int ifreq, int iband, int ifn1,
			int ifn2, float[] segments, float slen, int id) {
		super(Instrument.warp, strt, TIE_DUR, amp, 0, 0, 0, 0);
		this.iwsize = iwsize;
		this.irandw = irandw;
		this.ioverlap = ioverlap;
		this.ifn1 = ifn1;
		this.ifn2 = ifn2;
		this.ifreq = ifreq;
		this.iband = iband;
		this.segments = segments;
		this.slen = slen;
		this.id = id;
	}

	@Override
	public String statement() {
		String stmt = "";
		stmt += "i " + gType.id + "." + id + " " + strt + " " + dur + " " + segments[0] + " " + segments[1] + " " + slen
				+ " " + iwsize + " " + irandw + " " + ioverlap + " " + ifreq + " " + iband + " " + ifn1 + " " + ifn2;
		for (int i = 1; i < segments.length - 1; i++) {
			float ts = segments[i];
			float te = segments[i + 1];
			float sstrt = strt + slen * i;
			stmt += "\ni " + gType.id + "." + id + " " + sstrt + " " + dur + " " + ts + " " + te + " " + slen;
		}
		return stmt;
	}

}
