package visual;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import processing.core.PApplet;
import table.Table;

public class ViewTable extends PApplet {

	public static int viewWidth = 1600;
	public static int viewHeight = 800;
	public static int res;
	public static int steps;
	public static int maxIteration;

	public static Palette palette;
	public static double xScale;
	public static double yScale;

	public static String filename = "C:/Users/Griffin/git/sound/FractalSynth/tables/t1/t1.table";
	public static String imagename = "C:/Users/Griffin/git/sound/FractalSynth/tables/t1/t1.jpg";
	public static Table table;

	public static void main(String[] args) {
		if (args.length > 0) {
			filename = args[0];
			imagename = args[1];
		}
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
			table = (Table) in.readObject();
			in.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PApplet.main("visual.ViewTable");
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		steps = table.data.length;
		res = table.data[0].length;
		maxIteration = findMaxIteration();
		xScale = 1.0 * steps / viewWidth;
		yScale = 1.0 * res / viewHeight;
		palette = new Palette(maxIteration);
		palette.add(new ColorTag(0, 0, 0, 0));
		palette.add(new ColorTag(255, 255, 255, maxIteration - 1));
		noLoop();
	}

	public void draw() {
		background(255);
		loadPixels();
		for (int i = 0; i < viewWidth; i++) {
			for (int j = 0; j < viewHeight; j++) {
				if (maxIteration == 1) {
					pixels[i + (viewHeight - j - 1) * viewWidth] = color(
							table.data[(int) (i * xScale)][(int) (j * yScale)] * 255);
				} else {
					pixels[i + (viewHeight - j - 1) * viewWidth] = colorArray(
							palette.get(table.data[(int) (i * xScale)][(int) (j * yScale)]));
				}
			}
		}
		updatePixels();
		save(imagename);
	}

	private int colorArray(int[] a) {
		return color(a[0], a[1], a[2]);
	}

	private int findMaxIteration() {
		int max = 0;
		for (int i = 0; i < steps; i++) {
			for (int j = 0; j < res; j++) {
				if (table.data[i][j] > max)
					max = table.data[i][j];
			}
		}
		return max;
	}
}
