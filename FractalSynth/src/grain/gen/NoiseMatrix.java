package grain.gen;

import java.util.ArrayList;
import java.util.List;

import grain.Grain;
import grain.Layer;
import grain.OscGrain;
import grain.SineFTable;
import table.Table;

public class NoiseMatrix implements Generator {

	int fMin;
	int fMax;
	float density;
	float duration;
	Table table;

	public NoiseMatrix(int fMin, int fMax, float density, float duration, Table table) {
		this.fMin = fMin;
		this.fMax = fMax;
		this.density = density;
		this.duration = duration;
		this.table = table;
	}

	@Override
	public int gen(Layer layer) {
		int total = (int) (density * duration);
		List<Grain> matrix = new ArrayList<>();
		for (int i = 0; i < total; i++) {
			float strt = (float) (Math.random() * duration);
			float xNorm = strt / duration;
			int sum = 0;
			for (int j = 0; j < table.height(); j++) {
				sum += table.get(xNorm, j / table.height());
			}
			int r = (int) (Math.random() * sum);
			int p = 0;
			int f = -1;
			while (p < r && f < table.height()) {
				f++;
				p += table.get(xNorm, f / table.height());
			}
			float yNorm = f / table.height();
			int freq = (int) ((fMax - fMin) * yNorm + fMin);
			matrix.add(new OscGrain(strt, 1f, 1 / density, freq, Grain.DEFAULT_ATT,
					Grain.DEFAULT_DEC, xNorm, yNorm));
		}
		layer.addGrains(matrix);
		layer.duration = duration;
		layer.addFTable(new SineFTable());
		return matrix.size();
	}

}
