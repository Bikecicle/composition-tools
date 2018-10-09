package grain.mod;

import grain.Grain;
import grain.Layer;
import table.Table;

public class Inflate implements Modifier {

	private double dMin;
	private double dMax;
	private Table table;

	public Inflate(double dMin, double dMax, Table table) {
		this.dMin = dMin;
		this.dMax = dMax;
		this.table = table;
	}

	public Inflate(double dur) {
		this.dMax = dur;
		this.table = null;
	}

	@Override
	public int applyTo(Layer layer) {
		for (Grain g : layer.sequence) {
			if (table != null) {
				double m = table.get(g.xNorm, g.yNorm) / table.kMax;
				g.dur = (float) ((dMax - dMin) * m + dMin);
			} else {
				g.dur = (float) dMax;
			}
		}
		return layer.sequence.size();
	}

}
