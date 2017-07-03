package filegen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;

import utility.Grain;
import utility.TimeInterval;

public class MandelgrainGen {

	private static String configFile = "config/defaultGrain.txt";
	private static String outputFile = "data/grains/test.sco";

	private static int frameRate = 60;

	public static void main(String[] args) {

		if (args.length > 0)
			outputFile = "data/grains/" + args[0];
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
		in.next();
		double positionX = in.nextDouble();
		in.next();
		double positionY = in.nextDouble();
		in.next();
		double maxZoom = in.nextDouble();
		in.next();
		double zoomSpeed = in.nextDouble();
		in.next();
		int maxIteration = in.nextInt();
		in.next();
		in.next();
		int minFrequency = in.nextInt();
		in.next();
		int maxFrequency = in.nextInt();
		in.next();
		float duration = in.nextFloat();
		in.next();
		float attack = in.nextFloat();
		in.next();
		float decay = in.nextFloat();
		in.next();
		float overlap = in.nextFloat();
		in.close();

		double scale = 1.0;
		float timeStep = duration - overlap;
		int frequencyStep = (maxFrequency - minFrequency) / resolution;
		// Scale zoom speed to target frame rate
		zoomSpeed = ((zoomSpeed - 1) * timeStep * frameRate / 1000) + 1;
		PrintWriter out = null;
		try {
			double step = 2.0 * Math.PI / resolution;
			int totalSteps = (int) (Math.log(Math.pow(10, maxZoom)) / Math.log(zoomSpeed));
			int grainCount = 0;

			out = new PrintWriter(outputFile);
			out.println("f1 0 4096 10 1");

			long timeGuess = 0;
			for (int l = 0; l < totalSteps; l++) {
				long start = Calendar.getInstance().getTimeInMillis();
				for (int i = 0; i < resolution; i++) {
					int frequency = minFrequency + (frequencyStep * i);
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
					Grain g = new Grain(1, timeStep * l, duration, (k * 1f) / maxIteration / resolution, frequency,
							attack, decay);
					if (g.amplitude >= 0.01 / resolution) {
						out.println(g.statement(resolution));
						grainCount++;
					}
				}
				scale *= zoomSpeed;
				timeGuess = (long) (timeGuess / 2.0 + (Calendar.getInstance().getTimeInMillis() - start) / 2.0);
				TimeInterval eta = new TimeInterval(timeGuess * (totalSteps - l));

				double progress = Math.round(10000.0 * l / totalSteps) / 100.0;
				System.out.println("Progress: " + progress + "% ETA: " + eta.toString());
			}
			out.println("e");
			out.close();
			System.out.println("Generating Complete!");
			System.out.println("Amplitude Resolution: " + resolution + "  Steps: " + totalSteps + "  Grains: "
					+ grainCount + "  Duration: " + (timeStep * totalSteps / 1000.0) + "s");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
