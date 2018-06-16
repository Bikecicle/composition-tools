package grain;

import java.util.ArrayList;
import java.util.List;

import table.Table;

public class PulsarMatrix implements Generator {

	private int fMinP;
	private int fMaxP;
	private int fResP;
	private int fMinD;
	private int fMaxD;
	private int minResD;
	private int maxResD;
	private double zoomVel;
	private int zoomMax;
	private Table tableP;
	private Table tableD;

	public PulsarMatrix(int fMinP, int fMaxP, int fResP, int fMinD, int fMaxD, int minResD, int maxResD, double zoomVel,
			int zoomMax, Table tableP, Table tableD) {
		this.fMinP = fMinP;
		this.fMaxP = fMaxP;
		this.fResP = fResP;
		this.fMinD = fMinD;
		this.fMaxD = fMaxD;
		this.minResD = minResD;
		this.maxResD = maxResD;
		this.zoomVel = zoomVel;
		this.zoomMax = zoomMax;
		this.tableP = tableP;
		this.tableD = tableD;
	}

	@Override
	public int gen(Layer layer) {
		List<Grain> matrix = new ArrayList<Grain>();
		int tRes = fMaxP;
		double fStepP = 1.0 * (fMaxP - fMinP) / fResP;
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel + 1);
		int tSteps = (int) (dur * tRes);

		for (int t = 0; t < tSteps; t++) {
			for (int fp = 0; fp < fResP; fp++) {
				int pFreq = (int) (fMinP + (fStepP * fp));
				// Add a grain if this frequency releases a pulse at this time
				// step, and the corresponding value in tableP is 1
				if ((int) (t % (1.0 * fMaxP / pFreq)) == 0 && tableP.get(1.0 * t / tSteps, 1.0 * fp / fResP) == 1) {
					double scl = 1.0 * (pFreq - fMinP) / (fMaxP - fMinP);
					int fResD = (int) ((maxResD - minResD) * scl) + minResD;
					double fStepD = 1.0 * (fMaxD - fMinD) / fResD;
					for (int f = 0; f < fResD; f++) {
						if (tableD.get(1.0 * t / tSteps, 1.0 * fp / fResP) == 1) {
							float gTime = 1.0f * t / tRes;
							int gFreq = (int) (fMinD + (fStepD * f));
							double xNorm = gTime / dur;
							double yNorm = 1.0 * (gFreq - fMinD) / (fMaxD - fMinD);
							matrix.add(new OscGrain(gTime, Grain.DEFAULT_DUR, Grain.DEFAULT_AMP, gFreq,
									Grain.DEFAULT_ATT, Grain.DEFAULT_DEC, xNorm, yNorm));
						}
					}
				}
			}
		}
		layer.addGrains(matrix);
		if (layer.duration < dur)
			layer.duration = dur;
		layer.addFTable(new SineFTable());
		return matrix.size();
	}

}
