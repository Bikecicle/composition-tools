package visual;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import processing.core.PApplet;

public class ViewStream extends PApplet{

	private static int viewWidth = 1000;
	private static int viewHeight = 1000;
	
	private static double fMin = 20;
	private static double fMax = 1000;
	
	private static double duration = 11.6;
	
	public static void main(String[] args) {
		PApplet.main("visual.ViewStream");
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		background(255);
		loadPixels();
		try {
			Scanner in = new Scanner(new File("data/streams/test.sco"));
			in.nextLine();
			String line = in.nextLine();
			while (line.startsWith("i")) {
				String[] grain = line.split(" ");
				int x = (int) (Double.valueOf(grain[1]) / duration * viewWidth);
				int y = (int) ((Double.valueOf(grain[4]) - fMin) / (fMax - fMin) * viewHeight);
				System.out.println(x + " " + y + " " + pixels.length + " " + (x + (y * viewWidth)));
				int i = x + (y * viewWidth);
				pixels[i] = color(0);
				line = in.nextLine();
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updatePixels();
	}

	public void draw() {

	}
}
