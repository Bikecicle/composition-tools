package filegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import audio.Grain;



public class MandelgrainGen {

	private static String configFile = "config/defaultGrain.txt";
	private static String tableFile = "data/tables/testF.tbl";
	private static String outputFile = "data/grains/test.sco";

	private static int frameRate = 60;

	public static void main(String[] args) {

		if (args.length > 0)
			outputFile = "data/grains/" + args[0];
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
		double zoomSpeed = conf.nextDouble();
		conf.next();
		int maxIteration = conf.nextInt();
		conf.next();
		conf.next();
		int minFrequency = conf.nextInt();
		conf.next();
		int maxFrequency = conf.nextInt();
		conf.next();
		float duration = conf.nextFloat();
		conf.next();
		float attack = conf.nextFloat();
		conf.next();
		float decay = conf.nextFloat();
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
		int step = 1;
		double frequencyStep = 1.0 * (maxFrequency - minFrequency) / resolution;
		// Scale zoom speed to target frame rate
		PrintWriter out = null;
		try {
			double angleStep = 2.0 * Math.PI / resolution;
			int totalSteps = (int) (Math.log(Math.pow(10, maxZoom)) / Math.log(zoomSpeed)) / step;
			int grainCount = 0;

			out = new PrintWriter(outputFile);
			out.println("f1 0 4096 10 1");

			for (int l = 0; l < totalSteps; l++) {
				for (int i = 0; i < resolution; i++) {
					if (table[l * step][i * step] == 1) {
						int frequency = (int) (minFrequency + (frequencyStep * i));
						Grain g = new Grain(1, (1.0f * l * step / 60 * 1000), duration, 0.01f, frequency,
								attack, decay);
						out.println(g.statement());
						grainCount++;
					}
				}
				scale *= zoomSpeed;
				double progress = Math.round(10000.0 * l / totalSteps) / 100.0;
				System.out.println("Progress: " + progress + "%");
			}
			out.println("e");
			out.close();
			System.out.println("Generating Complete!");
			System.out.println("Amplitude Resolution: " + resolution + "  Steps: " + totalSteps + "  Grains: "
					+ grainCount + "  Duration: " + (totalSteps * step / frameRate) + "s");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
