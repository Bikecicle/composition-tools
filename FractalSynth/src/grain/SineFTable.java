package grain;

public class SineFTable extends FTable {
	
	private static final long serialVersionUID = 6055167497295545172L;
	
	public float str1;

	public SineFTable() {
		super(IFN_DEFAULT, Instrument.osc.genRoutine);
		this.isize = 16384;
		this.str1 = 1;
	}

	@Override
	public String statement() {
		return super.statement() + " " + str1;
	}
}
