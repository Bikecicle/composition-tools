package table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {
	
	private static final long serialVersionUID = -8011552819568163620L;
	
	public String name;
	public int kMax;
	public int[][] data;
	public List<String> filters;
	
	public Table(String name, int[][] data, int kMax) {
		this.name = name;
		this.data = data;
		this.kMax = kMax;
		filters = new ArrayList<>();
	}
	
	public Table(String name, int kMax) {
		this(name, null, kMax);
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
		for (int i = 0; i < length(); i++) {
			for (int j = 0; j < height(); j++) {
				if (data[i][j] > max)
					max = data[i][j];
			}
		}
		return max;
	}

	public int length() {
		return data.length;
	}
	
	public int height() {
		return data[0].length;
	}
}
