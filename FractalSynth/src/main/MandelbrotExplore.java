package main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import processing.core.PApplet;
import visual.ColorTag;
import visual.Palette;

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

	private boolean moved = true;
	private boolean rendered = false;
	private boolean editingPalette = false;

	private int nDivMin = 120;
	private int nDivMax = viewWidth;
	private int nDiv = nDivMin;

	private Palette palette;
	private int dim = 255;

	public static void main(String[] args) {
		PApplet.main("main.MandelbrotExplore");
	}

	@SuppressWarnings("unused")
	public void settings() {
		size(viewWidth, viewHeight);
		viewShort = viewWidth > viewHeight ? viewHeight : viewWidth;
		int viewLong = viewWidth > viewHeight ? viewHeight : viewWidth;
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
		if (moved) {
			nDiv = nDivMin;
			rendered = false;
			/**
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = color(0);
			}
			*/
		} else {
			if (!rendered) {
				int xGap = viewWidth / nDiv + 1;
				int yGap = viewHeight / nDiv + 1;
				for (int xDiv = 0; xDiv < nDiv; xDiv++) {
					if (moved) {
						break;
					}
					for (int yDiv = 0; yDiv < nDiv; yDiv++) {
						if (moved) {
							break;
						}
						if (((xDiv % 2 == 0) && (yDiv % 2 == 1)) || (xDiv % 2 == 1) || (nDiv == nDivMin)) {
							double x0 = scaleX(xDiv * xGap);
							double y0 = scaleY(yDiv * yGap);
							int k = 0;
							double x = 0.0;
							double y = 0.0;
							while ((x * x + y * y) < 4 && k < maxIteration) {
								double xtemp = x * x - y * y + x0;
								y = 2 * x * y + y0;
								x = xtemp;
								k++;
							}
							for (int i = 0; i < xGap; i++) {
								for (int j = 0; j < yGap; j++) {
									int iDiv = i + xDiv * xGap;
									int jDiv = j + yDiv * yGap;
									if (iDiv < viewWidth && jDiv < viewHeight) {
										pixels[iDiv + jDiv * viewWidth] = colorArray(palette.get(k));
									}
								}
							}
						}
					}
				}
				nDiv *= 2;
				rendered = nDiv / 2 >= nDivMax;
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
				} else if (key == 'r') {
					System.out.println("Recording position");
					System.out.println(positionX);
					System.out.println(positionY);
					try {
						PrintWriter out = new PrintWriter("config/position.txt");
						out.println(positionX);
						out.println(positionY);
						out.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private int colorArray(int[] a) {
		return color(a[0], a[1], a[2]);
	}
}
