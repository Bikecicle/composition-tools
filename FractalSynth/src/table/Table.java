package table;

public class Table {
	
	public int fRes;
	public int tRes;
	public int[][] data;
	
	public Table() {
		// TODO Auto-generated constructor stub
	}

	public int get(int t, int f, int tRes, int fRes) {
		// Scale time and frequency frames from given resolution to table's resolution
		return data[(int) (1.0 * t / tRes * this.tRes)][(int) (1.0 * f / fRes * this.fRes)];
	}
	
	public int getMaxDensity() {
		// Technically get max total amplitude
		int maxDen = 0;
		for( int[] col : data) {
			int den = 0;
			for (int val : col)
				den += val;
			if (den > maxDen)
				maxDen = den;
		}
		return maxDen;
	}

}
