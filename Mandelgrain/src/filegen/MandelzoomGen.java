package filegen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Scanner;


public class MandelzoomGen {

	private static int frameShort;

	private static String configFile = "config/defaultZoom.txt";
	private static String outputFile = "data/zooms/test.mz";
	private static int frameWidth = 800;
	private static int frameHeight = 600;
	private static int frameRate;
	private static double positionX;
	private static double positionY;
	private static double maxZoom;
	private static double zoomSpeed;
	private static int maxIteration;
	private static double scale;

	private static double x0;
	private static double y0;
	private static int k;
	private static double x;
	private static double y;
	private static double xtemp;

	public static void main(String[] args) {

		if (args.length > 0)
			outputFile = "data/zooms/" + args[0];
		if (args.length > 1)
			configFile = "config/" + args[1];

		Scanner in = null;
		try {
			in = new Scanner(new File(configFile));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		in.next();
		frameWidth = in.nextInt();
		in.next();
		frameHeight = in.nextInt();
		in.next();
		frameRate = in.nextInt();
		in.next();
		positionX = in.nextDouble();
		in.next();
		positionY = in.nextDouble();
		in.next();
		maxZoom = in.nextDouble();
		in.next();
		zoomSpeed = in.nextDouble();
		in.next();
		maxIteration = in.nextInt();
		in.close();

		frameShort = frameWidth > frameHeight ? frameHeight : frameWidth;
		scale = 1.0;

		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(outputFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			out.writeInt(frameWidth);
			out.writeInt(frameHeight);
			out.writeInt(frameRate);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int totalFrames = (int) (Math.log(Math.pow(10, maxZoom)) / Math.log(zoomSpeed));
		long timeGuess = 0;
		for (int l = 0; l < totalFrames; l++) {
			long start = Calendar.getInstance().getTimeInMillis();
			for (int i = 0; i < frameWidth; i++) {
				x0 = (4.0 * (i + 1) - 2.0 * frameWidth) / frameShort / scale + positionX;
				for (int j = 0; j < frameHeight; j++) {
					y0 = (4.0 * (j + 1) - 2.0 * frameHeight) / frameShort / scale + positionY;
					k = 0;
					x = 0.0;
					y = 0.0;
					while ((x * x + y * y) < 4 && k < maxIteration) {
						xtemp = x * x - y * y + x0;
						y = 2 * x * y + y0;
						x = xtemp;
						k++;
					}
					try {
						out.writeInt(k);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			scale *= zoomSpeed;

			double progress = Math.round(10000.0 * l / totalFrames) / 100.0;
			System.out.println("Progress: " + progress + "%");
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Rendering Complete!");
	}
}
