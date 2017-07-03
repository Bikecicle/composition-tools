package visual;

import processing.core.PApplet;

public class ViewPalette extends PApplet{

	private static int viewWidth = 1000;
	private static int viewHeight = 100;
	
	private static int dim = 800;
	private static Palette palette;
	
	public static void main(String[] args) {
		palette = new Palette(dim);

		PApplet.main("visual.ViewPalette");
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		loadPixels();
		for (int i = 0; i < viewWidth; i++) {
			for (int j = 0; j < viewHeight; j++) {
				int[] c = palette.get((int) (1.0 * i / viewWidth * dim));
				pixels[i + j * viewWidth] = color(c[0], c[1], c[2]);
			}
		}
		updatePixels();
	}

	public void draw() {

	}
}
