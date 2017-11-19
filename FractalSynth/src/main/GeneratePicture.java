package main;

import processing.core.PApplet;
import visual.ImageSource;

public class GeneratePicture extends PApplet {

	private static int viewWidth;
	private static int viewHeight;
	private static String imagePath;

	private static ImageSource source;

	public static void main(String[] args) {
		if (args.length == 4) {
			imagePath = "images/" + args[1] + ".jpg";
			viewWidth = Integer.parseInt(args[2]);
			viewHeight = Integer.parseInt(args[3]);
			source = new ImageSource(args[0], viewWidth, viewHeight);
			PApplet.main("main.GeneratePicture");
		} else {
			System.out.println("Arguments: [script name] [width] [height] [image name]");
		}
	}

	@Override
	public void settings() {
		size(viewWidth, viewHeight);
	}

	@Override
	public void setup() {
		background(0);
		loadPixels();
		
		for (int x = 0; x < viewWidth; x++) {
			for (int y = 0; y < viewHeight; y++) {
				pixels[x + y * viewWidth] = colorArray(source.getShade(x, y));
			}
		}
		
		updatePixels();
		save(imagePath);
		noLoop();
	}
	
	private int colorArray(int[] a) {
		return color(a[0], a[1], a[2]);
	}
}
