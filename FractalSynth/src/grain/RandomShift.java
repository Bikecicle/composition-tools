package grain;

import table.FractalTable;

public class RandomShift implements Modifier {

	private double rMax;
	private FractalTable table;

	public RandomShift(double rMax, FractalTable table) {
		this.rMax = rMax;
		this.table = table;
	}

	public RandomShift(double rMax) {
		this.rMax = rMax;
		this.table = null;
	}

	@Override
	public int applyTo(Layer layer) {
		for (Grain g : layer.sequence) {
			double r = 0;
			if (table != null) {
				r = rMax * table.get(g.xNorm, g.yNorm) / table.kMax;
			} else {
				r = rMax;
			}
			double d = Math.random() * 2 * r - r;
			g.strt += d;
		}
		return layer.sequence.size();
	}

}
