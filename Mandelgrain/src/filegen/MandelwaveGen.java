package filegen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Scanner;

import utility.TimeInterval;

public class MandelwaveGen {

	private static String configFile = "config/default.txt";
	private static String outputFile = "data/waves/test.mw";

	public static void main(String[] args) {

		if (args.length > 0)
			outputFile = "data/waves/" + args[0];
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
		in.next();
		int resolution = in.nextInt();
		double positionX = in.nextDouble();
		double positionY = in.nextDouble();
		double maxZoom = in.nextDouble();
		double zoomSpeed = in.nextDouble();
		int maxIteration = in.nextInt();
		in.close();

		double scale = 1.0;

		ObjectOutputStream out = null;
		try {
			double step = 2.0 * Math.PI / resolution;
			int totalFrames = (int) (Math.log(Math.pow(10, maxZoom)) / Math.log(zoomSpeed));

			out = new ObjectOutputStream(new FileOutputStream(outputFile));
			out.writeInt(resolution);
			out.writeInt(totalFrames);
			out.writeInt(maxIteration);

			long timeGuess = 0;
			for (int l = 0; l < totalFrames; l++) {
				long start = Calendar.getInstance().getTimeInMillis();
				for (int i = 0; i < resolution; i++) {
					double angle = step * i;
					double x0 = Math.cos(angle) * 2.0 / scale + positionX;
					double y0 = Math.sin(angle) * 2.0 / scale + positionY;
					int k = 0;
					double x = 0.0;
					double y = 0.0;
					while ((x * x + y * y) < 4 && k < maxIteration) {
						double xtemp = x * x - y * y + x0;
						y = 2 * x * y + y0;
						x = xtemp;
						k++;
					}
					out.writeInt(k);
				}
				scale *= zoomSpeed;
				timeGuess = (long) (timeGuess / 2.0 + (Calendar.getInstance().getTimeInMillis() - start) / 2.0);
				TimeInterval eta = new TimeInterval(timeGuess * (totalFrames - l));

				double progress = Math.round(10000.0 * l / totalFrames) / 100.0;
				System.out.println("Progress: " + progress + "% ETA: " + eta.toString());
			}
			out.close();
			System.out.println("Rendering Complete!");
			System.out.println("Amplitude Resolution: " + resolution + "  Frames: " + totalFrames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
