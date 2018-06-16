package table;

import java.util.ArrayList;

public class FractalTable extends Table {

	private static final long serialVersionUID = -8279801280032154773L;

	public double posX;
	public double posY;
	public int zoomMax;
	public double zoomVel;
	

	public FractalTable(String name, int tRes, int fRes, double zoomVel, int zoomMax, int iterMax, double posX, double posY) {
		super(name, tRes, fRes, iterMax);
		this.zoomVel = zoomVel;
		this.zoomMax = zoomMax;
		this.posX = posX;
		this.posY = posY;
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel + 1);
		int len = (int) (dur * tRes);
		data = new int[len][fRes];
		filters = new ArrayList<String>();
	}

	public FractalTable(String name, FractalTable other) {
		super(name, other.tRes, other.fRes, other.kMax);
		this.zoomVel = other.zoomVel;
		this.zoomMax = other.zoomMax;
		this.posX = other.posX;
		this.posY = other.posY;
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel + 1);
		int len = (int) (dur * tRes);
		data = new int[len][fRes];
		filters = new ArrayList<String>();
	}

	@Override
	public String toString() {
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel + 1);
		String str = "Name: " + name + "\n" + "Duration: " + dur + " sec at x" + zoomVel + "/sec\n"
				+ "Resolution: " + tRes + " steps/sec | " + fRes + " steps/2pi\n" + "Max iteration: " + kMax + "\n"
				+ "Position: " + posX + " | " + posY + "\n" + "Applied filters: \n";
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
