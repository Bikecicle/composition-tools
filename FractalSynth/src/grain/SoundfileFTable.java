package grain;

public class SoundfileFTable {

	public String varname;
	public int ifn;
	public float itime;
	public float isize;
	public int igen;
	public String Sfilnam;
	public float iskip;
	public int iformat;
	public int ichn;

	public SoundfileFTable(int ifn, String filename) {
		this.varname = "giFt" + ifn;
		this.ifn = ifn;
		this.itime = 0;
		this.isize = 0;
		this.igen = 1;
		this.Sfilnam = filename;
		this.iskip = 0; // Start at beginning
		this.iformat = 0;
		this.ichn = 0;
	}

	public String statement() {
		return varname + " ftgen " + ifn + ", " + itime + ", " + isize + ", " + igen + ", " + "\"" + Sfilnam + "\", "
				+ iskip + ", " + iformat + ", " + ichn;
	}

}
