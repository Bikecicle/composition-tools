package table;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageToTable {
	
	public static final int kMax = 765;
	
	public Table[] fetch(String name, String imgPath) throws IOException {
		BufferedImage img = ImageIO.read(new File(imgPath));
		int[][] red = new int[img.getWidth()][img.getHeight()];
		int[][] green = new int[img.getWidth()][img.getHeight()];
		int[][] blue = new int[img.getWidth()][img.getHeight()];
		int[][] sum = new int[img.getWidth()][img.getHeight()];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getWidth(); y++) {
				int rgb = img.getRGB(x, y);
				red[x][y] = (rgb >> 16) & 0xFF;
				green[x][y] = (rgb >> 8) & 0xFF;
				blue[x][y] = rgb & 0xFF;
				sum[x][y] = red[x][y] + green[x][y] + blue[x][y];
			}
		}
		Table[] tables = new Table[4];
		tables[0] = new Table(name + "_r", red, kMax);
		tables[1] = new Table(name + "_g", green, kMax);
		tables[2] = new Table(name + "_b", blue, kMax);
		tables[3] = new Table(name, sum, kMax);
		return tables;
	}

}
