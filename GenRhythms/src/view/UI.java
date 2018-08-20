package view;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import model.Rhythm;
import model.Session;
import sound.Performer;

public class UI {

	public static final String testOut = "test.wav";

	public static final String INT = "\\d+";
	public static final String FLOAT = "\\d+|(\\d*\\.\\d+)";
	public static final String STR = ".+";

	public static final String randDir = "RANDOM/";

	public static final String mainDir = "sessions/";

	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			Performer performer = new Performer();
			Session session = null;
			String action = input("(n)ew session, (l)oad saved", "[nl]", in);
			String title = input("Title:", STR, in);
			if (action.equals("n")) {
				int voiceCount = Integer.parseInt(input("Voice count:", INT, in));
				int length = Integer.parseInt(input("Length (measures):", INT, in));
				int quant = Integer.parseInt(input("Quantization (measure division):", INT, in));
				float tempo = Float.parseFloat(input("Tempo (bpm):", FLOAT, in));
				String mainSampleDir = System.getenv("SAMPLES") + "/";
				String sampleDir = inputFile("Sample pool directory:", mainSampleDir, in);
				session = new Session(title, voiceCount, length, quant, tempo, sampleDir);
			} else if (action.equals("l")) {
				session = Session.load(new File(mainDir + title));
			}
			String action1 = input("Load set (y/n):", "[yn]", in);
			if (action1.equals("y")) {
				//String setName = input("Set name:", STR, in);
				//session.load(setName);
			}
			boolean stopped = false;
			while (!stopped) {
				System.out.println("Starting batch " + session.getStage());
				List<Rhythm> batch = session.createBatch();
				int i = 0;
				while (i < batch.size()) {
					performer.play(batch.get(i));
					System.out.println();
					System.out.println("[Batch " + session.getStage() + ", " + (i + 1) + "/" + batch.size() + "]");
					String r = input("Rate (0-5), (r)epeat, (a)dd to set, (e)nd batch:", "[0-5rae]", in);
					if (r.equals("r")) {
						// Do nothing and repeat
					} else if (r.equals("a")) {
						session.addToSet(batch.get(i));
						batch.get(i).rate(5);
						i++;
						System.out.println("Rhythm added to set and given a rating of 5");
					} else if (r.equals("e")) {
						break;
					} else {
						batch.get(i).rate(Integer.parseInt(r));
						i++;
					}
				}
				//session.saveSession();
				while (true) {
					String action2 = input("(c)ontinue, (r)epeat, (p)lay set, (s)ave set (q)uit", "[crpsq]", in);
					if (action2.equals("c")) {
						session.advance();
						break;
					} else if (action2.equals("r")) {
						// Don't advance session
						break;
					} else if (action2.equals("p")) {
						for (int i1 = 0; i1 < session.getSet().size(); i1++) {
							System.out.println("Set rhythm " + i + "/" + session.getSet().size() + "]");
							performer.play(session.getSet().get(i1));
						}
					} else if (action2.equals("s")) {
						//String setName = input("Save as:", STR, in);
						//session.saveSet(setName);
					} else if (action2.equals("q")) {
						stopped = true;
						break;
					}
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			path = dir + input(prompt, STR, in);
			if (new File(path).exists())
				break;
			System.out.println(path + " does not exist");
		}
		return path;
	}
}
