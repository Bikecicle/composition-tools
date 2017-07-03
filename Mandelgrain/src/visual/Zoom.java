package visual;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import ddf.minim.*;
import processing.core.PApplet;

public class Zoom extends PApplet {

	// Default viewing parameters
	public static int viewWidth = 1360;
	public static int viewHeight = 768;
	public static float fps = 60;
	
	public Palette palette;
	public int dim = 256;

	public static String filename = "data/zooms/test.mz";
	public static String soundFilename = "data/sounds/test.wav";

	public static ObjectInputStream in = null;
	public static Minim minim;
	public static AudioPlayer sound;
	
	public static void main(String[] args) {
		if ( args.length > 0 ) {
			filename = "output/zooms/" + args[0];
		}
		try {
			in = new ObjectInputStream(new FileInputStream(filename));
			viewWidth = in.readInt();
			viewHeight = in.readInt();
			fps = in.readInt();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PApplet.main("visual.Zoom");
	}

	public void settings() {
		size(viewWidth, viewHeight);
	}

	public void setup() {
		palette = new Palette(dim);
		palette.add(new ColorTag(0, 0, 0, 0));
		palette.add(new ColorTag(255, 255, 255, dim / 2));
		frameRate(fps);
		minim = new Minim(this);
		sound = minim.loadFile(soundFilename);
		sound.loop();
	}

	public void draw() {
		
		loadPixels();
		try {
			for (int i = 0; i < viewWidth; i++) {
				for (int j = 0; j < viewHeight; j++) {
					pixels[i + j * viewWidth] = colorArray(palette.get(in.readInt()));
				}
			}
		} catch (IOException e) {
			try {
				in = new ObjectInputStream(new FileInputStream(filename));
				in.readInt();
				in.readInt();
				in.readInt();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		updatePixels();
	}
	
	private int colorArray(int[] a) {
		return color(a[0], a[1], a[2]);
	}

}
