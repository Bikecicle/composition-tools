package main;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import sound.Performer;

public class UI {

	public static final String TEST_OUT = "test.wav";

	public static final String INT = "\\d+";
	private static final String FLOAT = "\\d+|(\\d*\\.\\d+)";

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int voiceCount = Integer.parseInt(input("Voice count:", INT, in));
		int length = Integer.parseInt(input("Length (measures):", INT, in));
		int quant = Integer.parseInt(input("Quantization (measure division):", INT, in));
		float tempo = Float.parseFloat(input("Tempo (bpm):", FLOAT, in));
		String[] samples = new String[voiceCount];
		for (int v = 0; v < voiceCount; v++) {
			samples[v] = inputFile("Voice #" + (v + 1) + " sample:", "samples", in);
		}
		Session session = new Session(voiceCount, length, quant, tempo, samples);
		int b = 1;
		boolean stopped = false;
		while (!stopped) {
			System.out.println("Starting batch " + b);
			List<Rhythm> batch = session.createBatch();
			for (int i = 0; i < batch.size(); i++) {
				String r = "r";
				while (r.equals("r")) {
					Performer.play(batch.get(i));
					System.out.println();
					System.out.println("[Batch " + b + ", " + (i + 1) + "/" + batch.size() + "]");
					r = input("Rate (0-5), (r)epeat, (s)ave:", "[0-5rs]", in);
				}
				if (r.equals("s")) {
					
				}
				batch.get(i).rate(Integer.parseInt(r));
			}
			String action = input("(c)ontinue, (r)epeat, (q)uit", "[crq]", in);
			if (action.equals("c")) {
				session.advance();
				b++;
			} else if (action.equals("r")) {
				// Go over the same population again
			} else if (action.equals("q")) {
				stopped = true;
			}
		}
		in.close();
	}

	public static String input(String prompt, String pattern, Scanner in) {
		String value = null;
		while (true) {
			System.out.println(prompt);
			Scanner filter = new Scanner(in.nextLine());
			if (filter.hasNext(pattern)) {
				value = filter.next(pattern);
				filter.close();
				break;
			}
			filter.close();
			System.out.println("Invalid input!");
		}
		return value;
	}
	
	public static String inputFile(String prompt, String dir, Scanner in) {
		String path = null;
		while (true) {
			path = dir + "/" + input(prompt, ".+", in);
			if (new File(path).exists())
				break;
			System.out.println(path + " does not exist");
		}
		return path;
	}
}
