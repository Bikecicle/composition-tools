package sco;

public class SineFTable extends FTable {
	
	private static final long serialVersionUID = 6055167497295545172L;
	
	float str1;

	public SineFTable() {
		super(IFN_DEFAULT, GenRoutine.sine);
		this.isize = 16384;
		this.str1 = 1;
	}

	@Override
	public String read() {
		return super.read() + " " + str1;
	}
}
