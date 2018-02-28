package grain;

import java.util.ArrayList;
import java.util.List;

import table.Table;

public class Matrix implements Generator {
	private int fRes;
	private int fMin;
	private int fMax;
	private int tRes;
	private int zoomMax;
	private int zoomVel;
	private Table table;

	public Matrix(int fRes, int fMin, int fMax, int tRes, int zoomMax, int zoomVel, Table table) {
		this.fRes = fRes;
		this.fMin = fMin;
		this.fMax = fMax;
		this.tRes = tRes;
		this.zoomMax = zoomMax;
		this.zoomVel = zoomVel;
		this.table = table;
	}

	@Override
	public int gen(Layer layer) {
		List<Grain> matrix = new ArrayList<Grain>();
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel + 1);
		int tSteps = (int) (dur * tRes);
		double fStep = 1.0 * fRes / (fMax - fMin);
		float gAmp = 1.0f / table.getMaxDensity();

		for (int t = 0; t < tSteps; t++) {
			for (int f = 0; f < fRes; f++) {
				if (table.get(t, f, tRes, fRes, zoomVel) == 1) {
					float gTime = 1.0f * t / tRes;
					int gFreq = (int) (fMin + (fStep * f));
					double xNorm = gTime / dur;
					double yNorm = (gFreq - fMin) / (fMax - fMin);
					matrix.add(new OscGrain(gTime, Grain.DEFAULT_DUR, gAmp, gFreq, Grain.DEFAULT_ATT, Grain.DEFAULT_DEC, xNorm, yNorm));
				}
			}
		}
		layer.addGrains(matrix);
		layer.addFTable(new SineFTable());
		return matrix.size();

	}

}
