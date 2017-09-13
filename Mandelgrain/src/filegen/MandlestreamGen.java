package filegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import audio.Grain;

public class MandlestreamGen {

	private static String configFile = "config/defaultStream.txt";
	private static String tableFile = "data/tables/testF.tbl";
	private static String outputFile = "data/streams/test.sco";

	private static int frameRate = 60;

	public static void main(String[] args) {

		if (args.length > 0)
			outputFile = "data/streams/" + args[0];
		if (args.length > 1)
			configFile = "config/" + args[1];

		Scanner conf = null;
		try {
			conf = new Scanner(new File(configFile));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		conf.next();
		conf.next();
		int resolution = conf.nextInt();
		conf.next();
		double positionX = conf.nextDouble();
		conf.next();
		double positionY = conf.nextDouble();
		conf.next();
		double maxZoom = conf.nextDouble();
		conf.next();
		double zoomSpeed = conf.nextDouble(); // zoom/frame
		conf.next();
		int maxIteration = conf.nextInt();
		conf.next();
		conf.next();
		int totalGrains = conf.nextInt();
		conf.next();
		int minFrequency = conf.nextInt();
		conf.next();
		int maxFrequency = conf.nextInt();
		conf.next();
		int grainDuration = conf.nextInt();
		conf.next();
		int attack = conf.nextInt();
		conf.next();
		int decay = conf.nextInt();
		conf.close();
		
		ObjectInputStream in;
		int[][] table = null;
		try {
			 in = new ObjectInputStream(new FileInputStream(tableFile));
			 table = (int[][]) in.readObject();
			 in.close();
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		double scale = 1.0;
		double frequencyStep = (maxFrequency - minFrequency) * 1.0 / resolution;
		PrintWriter out = null;
		try {
			double step = 2.0 * Math.PI / resolution;
			int frameCount = (int) (Math.log(Math.pow(10, maxZoom)) / Math.log(zoomSpeed));
			float amplitude = (float) (1.0 / (totalGrains / frameCount * 2));

			out = new PrintWriter(outputFile);
			out.println("f1 0 4096 10 1");

			for (int g = 0; g < totalGrains; g++) {
				float grainFrame = (float) (Math.random() * frameCount);
				scale = Math.pow(zoomSpeed, (grainFrame));
				int[] p = new int[resolution];
				int total = 0;
				for (int i = 0; i < resolution; i++) {
					int k = table[(int) grainFrame][i];
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
				Grain grain = new Grain(1, grainFrame * 1000 / 60, grainDuration, amplitude, frequency, attack, decay);
				out.println(grain.statement(resolution));
				double progress = Math.round(g * 10000.0 / totalGrains) / 100.0;
				System.out.println("Progress: " + progress + "%");
			}
			out.println("e");
			out.close();
			System.out.println("Generating Complete!");
			System.out.println("Frequency Resolution: " + resolution + "  Grains: "
					+ totalGrains + "  Duration: " + (1.0 * frameCount / frameRate) + "s @ " + frameRate + "fps");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
