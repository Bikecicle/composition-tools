package grain;

import table.Table;

public class ShredSample implements Generator {
	
	private int bandCount;
	private int fMin;
	private int fMax;
	private int overlap;
	private Table table;
	private String sample;

	@Override
	public int gen(Layer layer) {
		WarpGrain[] bands = new WarpGrain[bandCount];
		
		return 0;
	}

}
