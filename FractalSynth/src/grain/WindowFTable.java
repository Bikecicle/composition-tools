package grain;

public class WindowFTable extends FTable {
	
	private static final long serialVersionUID = 706607239683820828L;
	
	private int[] table;

	public WindowFTable(int[] table) {
		super(IFN_DEFAULT, 2);
		this.table = table;
	}
	
	@Override
	public String statement() {
		String stmt = super.statement();
		for (int val : table) {
			stmt += " " + val;
		}
		return stmt;
	}

}
