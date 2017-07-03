package visual;

import processing.core.PApplet;

public class MandelbrotExplore extends PApplet {
	public static final int colorRange = 255;

	public static final int viewWidth = 1440;
	public static final int viewHeight = 800;
	private int viewShort;
	
	public static final double moveSpeed = 0.1; // Scaled to view
	public static final double zoomSpeed = 2.0; // Multiplier

	private double scale = 1.0;
	private double positionX = 0.0;
	private double positionY = 0.0;
	private int maxIteration = 1024;

	private boolean moved = false;
	private boolean editingPalette = false;

	private Palette palette;
	private int dim = 255;

	public static void main(String[] args) {
		PApplet.main("visual.MandelbrotExplore");
	}

	@SuppressWarnings("unused")
	public void settings() {
		size(viewWidth, viewHeight);
		viewShort = viewWidth > viewHeight ? viewHeight : viewWidth;
	}

	public void setup() {
		palette = new Palette(dim);
		palette.add(new ColorTag(0, 0, 0, 0));
		palette.add(new ColorTag(255, 255, 255, dim / 2));
		ellipseMode(CENTER);
		noStroke();
		fill(255);
	}

	public void draw() {
		if (editingPalette) {
			editPalette();
		} else {
			renderMandlebrot();
		}
	}

	public void keyPressed() {
		if (editingPalette) {
			
		} else {
			mainControls();
		}
	}

	private void editPalette() {
		loadPixels();
		for (int i = 0; i < viewWidth; i++) {
			for (int j = 0; j < viewHeight; j++) {
				int[] c = palette.get((int) (1.0 * i / viewWidth * dim));
				pixels[i + j * viewWidth] = color(c[0], c[1], c[2]);
			}
		}
		updatePixels();
	}

	private void renderMandlebrot() {
		loadPixels();
		for (int i = 0; i < viewWidth; i++) {
			double x0 = scaleX(i + 1);
			for (int j = 0; j < viewHeight; j++) {
				double y0 = scaleY(j + 1);
				int k = 0;
				double x = 0.0;
				double y = 0.0;
				while ((x * x + y * y) < 4 && k < maxIteration) {
					double xtemp = x * x - y * y + x0;
					y = 2 * x * y + y0;
					x = xtemp;
					k++;
				}
				pixels[i + j * viewWidth] = colorArray(palette.get(k));
			}
		}
		updatePixels();
		fill(255, 0, 0);
		ellipse(viewWidth / 2, viewHeight / 2, 5, 5);
		text("Zoom: x" + scale, 10, 20);
		text("Position: " + positionX + "+" + positionY + "i", 10, 40);
		text("Max Iterations: " + maxIteration, 10, 60);
		moved = false;
	}

	// Scales x to mandlebrot x scale (-2, 1)
	private double scaleX(int x) {
		return (4.0 * x - 2.0 * viewWidth) / viewShort / scale + positionX;
	}

	// Scales y to mandlebrot y scale (-1, 1)
	private double scaleY(int y) {
		return (4.0 * y - 2.0 * viewHeight) / viewShort / scale + positionY;
	}

	private void mainControls() {
		if (!moved) {
			if (key == CODED) {
				if (keyCode == UP) {
					positionY -= (moveSpeed / scale);
					moved = true;
				} else if (keyCode == DOWN) {
					positionY += (moveSpeed / scale);
					moved = true;
				} else if (keyCode == LEFT) {
					positionX -= (moveSpeed / scale);
					moved = true;
				} else if (keyCode == RIGHT) {
					positionX += (moveSpeed / scale);
					moved = true;
				}
			} else {
				if (key == 'w') {
					scale *= zoomSpeed;
					moved = true;
				} else if (key == 's') {
					scale /= zoomSpeed;
					moved = true;
				} else if (key == 'a' && maxIteration > 1) {
					maxIteration /= 2.0;
					moved = true;
				} else if (key == 'd') {
					maxIteration *= 2.0;
					moved = true;
				} else if (key == 'p') {
					editingPalette = true;
				}
			}
		}
	}

	private int colorArray(int[] a) {
		return color(a[0], a[1], a[2]);
	}
}
