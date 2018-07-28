package main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import sound.Performer;

public class Generator {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Voice count: ");
		int voiceCount = in.nextInt();
		System.out.println("Length (measures): ");
		int length = in.nextInt();
		System.out.println("Quantization (measure division): ");
		int quant = in.nextInt();
		System.out.println("Tempo (bpm): ");
		float tempo = in.nextFloat();
		System.out.println("Sample path: ");
		in.nextLine();
		String sample = in.nextLine();

		Session session = new Session(voiceCount, length, quant, tempo, sample);
		Performer performer = new Performer();

		int b = 1;
		boolean stopped = false;
		while (!stopped) {
			System.out.println("[Batch " + b + "]");
			List<Rhythm> batch = session.createBatch();
			for (Rhythm rhythm : batch) {
				performer.play(rhythm);
				while (true) {
					System.out.println("Rating (0-5): ");
					try {
						int r = in.nextInt();
						if (r < 0 || r > 5) {
							rhythm.rate(r);
							break;
						}	
					} catch (InputMismatchException e) {
						in.nextLine();
					}
				}
			}
			boolean valid = false;
			while (!valid) {
				System.out.println("(c)ontinue, (r)epeat, (q)uit");
				String action = in.nextLine();
				valid = true;
				if (action.startsWith("c")) {
					session.advance();
				} else if (action.startsWith("r")) {
					// Go over the same population again
				} else if (action.startsWith("q")) {
					stopped = true;
				} else {
					valid = false;
				}
			}
		}

		in.close();
	}
}
