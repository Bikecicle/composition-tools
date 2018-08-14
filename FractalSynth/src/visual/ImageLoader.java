package visual;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageLoader extends PApplet {

	public String imgPath;
	public int[][] red;
	public int[][] green;
	public int[][] blue;
	public int[][] alpha;
	
	public ImageLoader(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public void load() {
		PApplet.main("visual.ImageLoader");
	}

	public void setup() {
		noLoop();
	}

	public void draw() {
		red = new int[width][height];
		green = new int[width][height];
		blue = new int[width][height];
		alpha = new int[width][height];
		
		PImage img = loadImage(imgPath);
		image(img, 0, 0);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				red[x][y] = (int) red(get(x, height-y));
				green[x][y] = (int) green(get(x, height-y));
				blue[x][y] = (int) blue(get(x, height-y));
				alpha[x][y] = (int) alpha(get(x, height-y));
			}
		}
	}
}
