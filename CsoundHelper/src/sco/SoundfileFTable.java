package sco;

public class SoundfileFTable extends FTable {

	private static final long serialVersionUID = -2596051195054624160L;

	public String Sfilnam;
	public float iskip;
	public int iformat;
	public int ichn;

	public SoundfileFTable(String filename, int ifn) {
		super(ifn, 1);
		this.Sfilnam = "samples/" + filename;
		this.iskip = 0; // Start at beginning
		this.iformat = 0; // Defer
		this.ichn = 0; // Defer
	}

	public SoundfileFTable(String filename) {
		this(filename, IFN_DEFAULT);
	}

	@Override
	public boolean equals(Object obj) {
		SoundfileFTable other = null;
		try {
			other = (SoundfileFTable) obj;
		} catch (ClassCastException e) {
			return false;
		}
		if (this.Sfilnam.equals(other.Sfilnam))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return super.toString() + " " + "\"" + Sfilnam + "\" " + iskip + " " + iformat + " " + ichn;
	}
}
