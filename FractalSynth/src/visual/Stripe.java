package visual;

import java.util.ArrayList;
import java.util.List;

public class Stripe {

	public static final int RGB_MAX = 255;

	public static final int COLOR_COUNT = 5;
	public static final int COLOR_MAG = 255;

	public String name;
	public List<Coat> coats;
	public Palette envelope; // B&W gradient
	public int[][][] val;

	public Stripe(String name) {
		this.name = name;
		coats = new ArrayList<Coat>();
	}

	public void generate(String projectName, int width, int height) {
		val = new int[width][height][3];
		for (Coat coat : coats) {
			Palette colors = randomGrad(height);
			System.out.println("- " + coat.tableName + ": " + colors);
			coat.generate(projectName, width, height, colors);
		}

		// Layer
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				for (Coat coat : coats) {
					val[x][y][0] = (int) Math
							.sqrt(((val[x][y][0] * val[x][y][0] + coat.val[x][y][0] * coat.val[x][y][0])) / 2);
					val[x][y][1] = (int) Math
							.sqrt(((val[x][y][1] * val[x][y][1] + coat.val[x][y][1] * coat.val[x][y][1])) / 2);
					val[x][y][2] = (int) Math
							.sqrt(((val[x][y][2] * val[x][y][2] + coat.val[x][y][2] * coat.val[x][y][2])) / 2);
				}
			}
		}

		// Envelope
		if (envelope != null && envelope.gradient.length > 0) {
			double gScale = 1.0 / height * envelope.dim;
			for (int y = 0; y < height; y++) {
				double gain = envelope.gradient[(int) (y * gScale)][0] / 255.0;
				for (int x = 0; x < width; x++) {
					val[x][y][0] *= gain;
					val[x][y][1] *= gain;
					val[x][y][2] *= gain;
				}
			}
		}

		// Filtering
		contrast(2);

		// Clip
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (val[x][y][0] > RGB_MAX)
					val[x][y][0] = RGB_MAX;
				if (val[x][y][1] > RGB_MAX)
					val[x][y][1] = RGB_MAX;
				if (val[x][y][2] > RGB_MAX)
					val[x][y][2] = RGB_MAX;
			}
		}
	}

	private void contrast(double gain) {
		for (int y = 0; y < val[0].length; y++) {
			for (int x = 0; x < val.length; x++) {
				val[x][y][0] *= gain;
				val[x][y][1] *= gain;
				val[x][y][2] *= gain;
			}
		}
	}

	@SuppressWarnings("unused")
	private void glow() {
		int width = val.length;
		int height = val[0].length;
		int[][][] fVal = new int[width][height][3];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x < width - 1) {
					fVal[x + 1][y][0] = val[x][y][0] / 2;
					fVal[x + 1][y][1] = val[x][y][1] / 2;
					fVal[x + 1][y][2] = val[x][y][2] / 2;
				}
				if (x > 0) {
					fVal[x - 1][y][0] = val[x][y][0] / 2;
					fVal[x - 1][y][1] = val[x][y][1] / 2;
					fVal[x - 1][y][2] = val[x][y][2] / 2;
				}
				if (y < height - 1) {
					fVal[x][y + 1][0] = val[x][y][0] / 2;
					fVal[x][y + 1][1] = val[x][y][1] / 2;
					fVal[x][y + 1][2] = val[x][y][2] / 2;
				}
				if (y > 0) {
					fVal[x][y - 1][0] = val[x][y][0] / 2;
					fVal[x][y - 1][1] = val[x][y][1] / 2;
					fVal[x][y - 1][2] = val[x][y][2] / 2;
				}
			}
		}
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				val[x][y][0] += fVal[x][y][0];
				val[x][y][1] += fVal[x][y][1];
				val[x][y][2] += fVal[x][y][2];
				if (val[x][y][0] > 255)
					val[x][y][0] = 255;
				if (val[x][y][1] > 255)
					val[x][y][1] = 255;
				if (val[x][y][2] > 255)
					val[x][y][2] = 255;
			}
		}
	}
	
	private Palette randomGrad(int height) {
		Palette colors = new Palette(height);
		for (int i = 0; i < COLOR_COUNT; i++) {
			int[] color = new int[3];
			int pos = 0;
			if (i > 0) {
				pos = (int) (1.0 * height / (COLOR_COUNT - 1) * i) - 1;
			}
			int a = (int) (Math.random() * (COLOR_MAG + 1));
			int b = (int) (Math.random() * (COLOR_MAG + 1));
			if (a > b) {
				color[0] = b;
				color[1] = a - b;
				color[2] = COLOR_MAG - a;
			} else {
				color[0] = a;
				color[1] = b - a;
				color[2] = COLOR_MAG - b;
			}
			colors.add(new ColorTag(color[0], color[1], color[2], pos));
		}
		return colors;
	}
}
