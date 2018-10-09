package orc;

import sco.FTable;

public class FTGen extends Statement {

	FTable fTable;
	
	public FTGen(FTable fTable) {
		super(Integer.toString(fTable.ifn), false, 1);
		this.fTable = fTable;
	}
	
	@Override
	public String toString() {
		defined = true;
		return "ftgen " + fTable.toString().substring(2);
	}
}
