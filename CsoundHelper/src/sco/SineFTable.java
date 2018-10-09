package sco;

public class SineFTable extends FTable {
	
	private static final long serialVersionUID = 6055167497295545172L;
	
	float str1;

	public SineFTable() {
		super(IFN_DEFAULT, 0, 16384, GenRoutine.sine);
		this.str1 = 1;
	}

	@Override
	public String toString() {
		return super.toString() + " " + str1;
	}
}
