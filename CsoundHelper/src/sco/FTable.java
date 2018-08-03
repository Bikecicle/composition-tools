package sco;

import java.io.Serializable;

public class FTable implements Serializable {

	private static final long serialVersionUID = 2581117148676222588L;

	public static final int IFN_DEFAULT = 0; // placeholder - set by engine if remains at 0

	int ifn;
	int itime;
	int isize;
	GenRoutine igen;

	public FTable(int ifn, GenRoutine igen) {
		this.ifn = ifn;
		itime = 0;
		isize = 0;
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
