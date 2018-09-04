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
			int x = (int) (table.length() * strt / duration);
			for (int y = 0; y < table.height(); y++) {
				sum += table.data[x][y];
			}
			if (sum > 0) {
				int r = (int) (Math.random() * sum);
				int p = 0;
				int y = 0;
				while (y < table.height()) {
					p += table.data[x][y];
					if (p >= r) {
						break;
					}
					y++;
				}
				float yNorm = 1.0f * y / table.height();
				int freq = (int) ((fMax - fMin) * yNorm + fMin);
				matrix.add(new OscGrain(strt, Grain.DEFAULT_DUR, 1 / density, freq, Grain.DEFAULT_ATT,
						Grain.DEFAULT_DEC, xNorm, yNorm));
			}
		}
		layer.addGrains(matrix);
		layer.duration = duration;
		layer.addFTable(new SineFTable());
		return matrix.size();
	}

}
