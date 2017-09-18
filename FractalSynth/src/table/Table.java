package table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable {

	private static final long serialVersionUID = -8279801280032154773L;

	public String name;
	public int fRes;
	public int tRes;
	public int[][] data;
	public double posX;
	public double posY;
	public int zoomMax;
	public double zoomVel;
	public int kMax;
	public List<String> filters;

	public Table(String name, int tRes, int fRes, double zoomVel, int zoomMax, int iterMax, double posX, double posY) {
		this.name = name;
		this.tRes = tRes;
		this.fRes = fRes;
		this.zoomVel = zoomVel;
		this.zoomMax = zoomMax;
		this.kMax = iterMax;
		this.posX = posX;
		this.posY = posY;
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel + 1);
		int len = (int) (dur * tRes);
		data = new int[len][fRes];
		filters = new ArrayList<String>();
	}

	public int get(int t, int f, int tRes, int fRes) {
		// Scale time and frequency frames from given resolution to table's
		// resolution
		return data[(int) (1.0 * t / tRes * this.tRes)][(int) (1.0 * f / fRes * this.fRes)];
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

	@Override
	public String toString() {
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel + 1);
		String str = "Name: " + name + "\n" + "Duration: " + dur + " sec at x" + (zoomVel + 1) + "/sec\n" + "Resolution: "
				+ tRes + " steps/sec | " + fRes + " steps/2pi\n" + "Max iteration: " + kMax + "\n" + "Position: " + posX
				+ " | " + posY + "\n" + "Applied filters: \n";
		if (filters.size() > 0) {
			for (int i = 0; i < filters.size(); i++) {
				str += (i + 1) + ") " + filters.get(i) + "\n";
			}
		} else {
			str += "none";
		}
		return str;
	}
}
