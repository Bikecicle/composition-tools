package sco;

public class SineFTable extends FTable {
	
	private static final long serialVersionUID = 6055167497295545172L;
	
	public float str1;

	public SineFTable() {
		super(IFN_DEFAULT, 10);
		this.isize = 16384;
		this.str1 = 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		SineFTable fTable = null;
		try {
			fTable = (SineFTable) obj;
		} catch (ClassCastException e) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + " " + str1;
	}
}
