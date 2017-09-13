package filegen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class TableGen {

	private static String configFile = "config/defaultTable.txt";
	private static String outputFile = "data/tables/loc1.tbl";
	
	private static int frameRate = 60;

	public static void main(String[] args) {

		if (args.length > 0)
			outputFile = "data/tables/" + args[0];
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
		double scale = 1.0;
		// Scale zoom speed to target frame rate
		try {
			double step = 2.0 * Math.PI / resolution;
			int totalSteps = (int) (Math.log(Math.pow(10, maxZoom)) / Math.log(zoomSpeed));
			int grainCount = 0;
			int[][] table = new int[totalSteps][resolution];
			
			for (int i = 0; i < totalSteps; i++) {
				for (int j = 0; j < resolution; j++) {
					double angle = step * j;
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
					table[i][j] = k;
				}
				scale *= zoomSpeed;
				double progress = Math.round(10000.0 * i / totalSteps) / 100.0;
				System.out.println("Progress: " + progress + "%");
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile));
			out.writeObject(table);
			out.close();
			System.out.println("Generating Complete!");
			System.out.println("Resolution: " + resolution + "  Frames: " + totalSteps + " Duration: " + (1.0 * totalSteps / frameRate) + " seconds at " + frameRate + " fps");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
