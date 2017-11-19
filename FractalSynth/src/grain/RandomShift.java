package grain;

import table.Table;

public class RandomShift implements Modulator {
	
	private double rMax;
	private double zoomVel;
	private Table table;
	
	public RandomShift(double rMax, double zoomVel, Table table) {
		this.rMax = rMax;
		this.zoomVel = zoomVel;
		this.table = table;
	}

	@Override
	public int applyTo(Layer layer) {
		int fMin = layer.getFMin();
		int fMax = layer.getFMax();
		for (Grain g : layer.sequence) {
			double r = rMax * table.get(g.strt, g.freq, fMin, fMax, zoomVel) / table.kMax;
			double d = Math.random() * 2 * r - r;
			g.strt += d;
		}
		return layer.sequence.size();
	}

}
