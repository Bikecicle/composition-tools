package grain.gen;

import java.util.ArrayList;
import java.util.List;

import grain.Grain;
import grain.Layer;
import grain.OscGrain;
import sco.SineFTable;
import table.Table;

public class SimpleMatrix implements Generator {

	int fMinP;
	int fMaxP;
	int fD;
	float dur;
	Table table;

	public SimpleMatrix(int fMinP, int fMaxP, int fD, float dur, Table table) {
		this.fMinP = fMinP;
		this.fMaxP = fMaxP;
		this.fD = fD;
		this.dur = dur;
		this.table = table;
	}

	@Override
	public int gen(Layer layer) {
		List<Grain> matrix = new ArrayList<>();
		int tMax = (int) (dur * fD);
		float tDelta = 1.0f / fD;
		int fResP = fMaxP - fMinP;
		for (int t = 0; t < tMax; t++) {
			for (int f = 0; f < fResP; f++) {
				double xNorm = 1.0 * t / tMax;
				double yNorm = 1.0 * f / fResP;
				float strt = t * tDelta;
				int freq = f * fResP + fMinP;
				float amp = 1f * table.get(xNorm, yNorm) / fResP;
				if (amp > 0)
					matrix.add(new OscGrain(strt, Grain.DEFAULT_DUR, amp, freq, Grain.DEFAULT_ATT, Grain.DEFAULT_DEC,
							xNorm, yNorm));
			}
		}
		layer.addGrains(matrix);
		layer.addFTable(new SineFTable());
		return matrix.size();
	}

}
