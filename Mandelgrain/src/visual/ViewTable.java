package visual;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import processing.core.PApplet;

public class ViewTable extends PApplet {

	public static int viewWidth = 1600;
	public static int viewHeight = 800;
	public static int res;
	public static int steps;
	public static int maxIteration;

	public static Palette palette;
	public static double xScale;
	public static double yScale;

	public static String filename = "data/tables/testF.tbl";
	public static String imagename = "data/images/test5.jpg";
	public static int[][] table;

	public static void main(String[] args) {
		if (args.length > 0) {
			filename = "data/tables/" + args[0];
		}
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
			table = (int[][]) in.readObject();
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
		steps = table.length;
		res = table[0].length;
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
				//pixels[i + (viewHeight - j - 1) * viewWidth] = colorArray(palette.get(table[(int) (i * xScale)][(int) (j * yScale)]));
				pixels[i + (viewHeight - j - 1) * viewWidth] = color(table[(int) (i * xScale)][(int) (j * yScale)] * 255);
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
		for (int i = 0; i < steps; i ++)  {
			for (int j = 0; j < res; j ++) {
				if (table[i][j] > max)
					max = table[i][j];
			}
		}
		return max;
	}
}
