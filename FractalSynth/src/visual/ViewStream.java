package visual;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import processing.core.PApplet;

public class ViewStream extends PApplet{

	private static int viewWidth = 1000;
	private static int viewHeight = 1000;
	
	private static double fMin = 1.0/40;
	private static double fMax = 1.0/1;
	
	private static double duration = 50;
	
	private static String scoreFile = "C:/Users/Griffin/git/sound/FractalSynth/projects/498/layers/default.sco";
	private static String imageFile = "C:/Users/Griffin/git/sound/FractalSynth/projects/498/images/default.jpg";
	
	public static void main(String[] args) {
		if (args.length > 0) {
			scoreFile = args[0];
			imageFile = args[1];
		}
		PApplet.main("visual.ViewStream");
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		background(255);
		loadPixels();
		Scanner score = null;
		try {
			score = new Scanner(new File(scoreFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		score.nextLine();
		String line = score.nextLine();
		while (line.startsWith("i")) {
			String[] grain = line.split(" ");
			int x = (int) (Double.valueOf(grain[1]) / duration * viewWidth);
			int y = viewHeight - (int) ((Double.valueOf(grain[2]) - fMin) / (fMax - fMin) * viewHeight);
			int i = x + (y * viewWidth);
			pixels[i] = color(0);
			line = score.nextLine();
		}
		score.close();
		updatePixels();
		save(imageFile);
		noLoop();
	}

	public void draw() {
		
	}
}
