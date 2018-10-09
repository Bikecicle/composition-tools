package sco;

import java.io.Serializable;

public class FTable implements Serializable {

	private static final long serialVersionUID = 2581117148676222588L;

	public static final int IFN_DEFAULT = 0; // placeholder - set by engine if remains at 0

	public int ifn;
	public int itime;
	public int isize;
	public GenRoutine igen;

	public FTable(int ifn, int itime, int isize, GenRoutine igen) {
		this.ifn = ifn;
		this.itime = itime;
		this.isize = isize;
		this.igen = igen;
	}

	@Override
	public String toString() {
		return "f " + ifn + " " + itime + " " + isize + " " + igen.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((FTable) obj).igen == this.igen;
	}
}
