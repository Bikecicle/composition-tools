package sco;

public class WindowFTable extends FTable {

	Window type;
	int max;

	public WindowFTable(int itime, int isize, Window type, int max) {
		super(FTable.IFN_DEFAULT, itime, isize, GenRoutine.window);
		this.type = type;
		this.max = max;
	}

	@Override
	public String toString() {
		return super.toString() + " " + type.id + " " + max;
	}
}
