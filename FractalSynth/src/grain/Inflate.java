package grain;

import table.Table;

public class Inflate implements Modifier {

	private double dMin;
	private double dMax;
	private double zoomVel;
	private Table table;

	public Inflate(double dMin, double dMax, double zoomVel, Table table) {
		this.dMin = dMin;
		this.dMax = dMax;
		this.zoomVel = zoomVel;
		this.table = table;
	}

	@Override
	public int applyTo(Layer layer) {
		for (Grain g : layer.sequence) {
			double m = table.get(g.xNorm, g.yNorm) / table.kMax;
			g.dur = (float) ((dMax - dMin) * m + dMin);
			g.att = g.dur / 2;
			g.dec = g.dur / 2;
		}
		return layer.sequence.size();
	}

}
