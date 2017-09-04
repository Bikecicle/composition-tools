package filegen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import utility.Grain;

public class MandlestreamGen {

	private static String configFile = "config/defaultStream.txt";
	private static String outputFile = "data/streams/test.sco";

	private static int frameRate = 60;

	public static void main(String[] args) {

		if (args.length > 0)
			outputFile = "data/streams/" + args[0];
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
		double zoomSpeed = in.nextDouble(); // zoom/frame
		in.next();
		int maxIteration = in.nextInt();
		in.next();
		in.next();
		int totalGrains = in.nextInt();
		in.next();
		int minFrequency = in.nextInt();
		in.next();
		int maxFrequency = in.nextInt();
		in.next();
		int grainDuration = in.nextInt();
		in.next();
		int attack = in.nextInt();
		in.next();
		int decay = in.nextInt();

		double scale = 1.0;
		double frequencyStep = (maxFrequency - minFrequency) * 1.0 / resolution;
		float amplitude = (float) (1.0 / (resolution + 1));
		PrintWriter out = null;
		try {
			double step = 2.0 * Math.PI / resolution;
			int duration = (int) (Math.log(Math.pow(10, maxZoom)) / Math.log(zoomSpeed) / frameRate * 1000.0);

			out = new PrintWriter(outputFile);
			out.println("f1 0 4096 10 1");

			for (int g = 0; g < totalGrains; g++) {
				float grainTime = (float) (Math.random() * duration);
				scale = Math.pow(zoomSpeed, (grainTime / 1000.0 * frameRate));
				int[] p = new int[resolution];
				int total = 0;
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
					p[i] = k;
					total += k;
				}
				int f = (int) (Math.random() * total);
				int sum = 0;
				for (int i = 0; i < resolution; i++) {
					sum += p[i];
					if (sum > f) {
						f = i;
						break;
					}
				}
				int frequency = (int) (frequencyStep * f + minFrequency);
				Grain grain = new Grain(1, grainTime, grainDuration, amplitude, frequency, attack, decay);
				out.println(grain.statement(resolution));
				double progress = Math.round(g * 10000.0 / totalGrains) / 100.0;
				System.out.println("Progress: " + progress + "%");
			}
			out.println("e");
			out.close();
			System.out.println("Generating Complete!");
			System.out.println("Amplitude Resolution: " + resolution + "  Grains: "
					+ totalGrains + "  Duration: " + (duration / 1000.0) + "s");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
