package table;

import java.io.Serializable;
import java.util.List;

public class Table implements Serializable {

	static final long serialVersionUID = -3338690167252241696L;

	public String name;
	public int tRes;
	public int fRes;
	public int kMax;
	public int[][] data;
	public List<String> filters;

	public Table(String name, int tRes, int fRes, int kMax) {
		this.name = name;
		this.tRes = tRes;
		this.fRes = fRes;
		this.kMax = kMax;
	}

	public int get(double xScaled, double yScaled) {
		return data[(int) (xScaled * data.length)][(int) (yScaled * data[0].length)];
	}

	public int getMaxDensity() {
		// Technically get max total amplitude
		int maxDen = 0;
		for (int[] col : data) {
			int den = 0;
			for (int val : col)
				den += val;
			if (den > maxDen)
				maxDen = den;
		}
		return maxDen;
	}

	public int getMaxIteration() {
		int max = 0;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < fRes; j++) {
				if (data[i][j] > max)
					max = data[i][j];
			}
		}
		return max;
	}

	public int length() {
		return data.length;
	}
}
