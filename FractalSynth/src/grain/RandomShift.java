package grain;

import table.Table;

public class RandomShift implements Modulator {
	
	private double rMax;
	private int fMin;
	private int fMax;
	private double zoomVel;
	private Table table;
	
	public RandomShift(double rMax, int fMin, int fMax, double zoomVel, Table table) {
		this.rMax = rMax;
		this.fMin = fMin;
		this.fMax = fMax;
		this.zoomVel = zoomVel;
		this.table = table;
	}

	@Override
	public int applyTo(Layer layer) {
		int dCount = 0;
		for (Grain g : layer.sequence) {
			if (table.get(g.strt, g.freq, fMin, fMax, zoomVel) == 1) {
				double d = Math.random() * 2 * rMax - rMax; // Random
															// displacement
															// within rMax
				g.strt += d;
				dCount++;
			}
		}
		return dCount;
	}

}
