package visual;

import main.FractalSynth;
import table.Table;

public class Coat {

	public String tableName;
	public int[][][] val;
	
	public Coat(String tableName) {
		this.tableName = tableName;
		val = null;
	}
	
	public void generate(String projectName, int width, int height, Palette colors) {
		val = new int[width][height][3];
		FractalSynth fractalSynth = new FractalSynth(projectName);
		Table table = fractalSynth.getTable(tableName);
		double xScale = 1.0 * table.fRes / width;
		double yScale = 1.0 * table.length() / height;
		int iMax = table.getMaxIteration();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double m = 1.0 * table.data[(int) (y * yScale)][(int) (x * xScale)] / iMax;
				int[] color = colors.get(y);
				val[x][y][0] = (int) (m * color[0]);
				val[x][y][1] = (int) (m * color[1]);
				val[x][y][2] = (int) (m * color[2]);
			}
		}
	}
}
