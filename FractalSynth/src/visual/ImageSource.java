package visual;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImageSource {
	
	public static int TIME_RES = 1000;

	private Scanner in;
	private List<Stripe> stripes;
	private Stripe active;
	private int stripeWidth;

	public ImageSource(String script, int width, int height) {
		try {
			in = new Scanner(new File("scripts/" + script));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String projectName = in.nextLine();
		stripes = new ArrayList<Stripe>();

		System.out.println("Retrieving setup from " + script + "...");
		readScript();
		in.close();

		stripeWidth = width / stripes.size();
		for (Stripe stripe : stripes) {
			System.out.println("Generating stripe - " + stripe.name + ":");
			stripe.generate(projectName, stripeWidth, height);
		}
	}

	public int[] getShade(int x, int y) {
		int s = x / stripeWidth;
		int[] shade = { 0, 0, 0 };
		if (s < stripes.size())
			shade = stripes.get(s).val[x % stripeWidth][y];
		return shade;

	}

	private void readScript() {
		active = null;
		while (in.hasNext()) {
			String[] cmd = in.nextLine().split(" ");
			if (!cmd[0].equals("") && !cmd[0].startsWith("#")) {
				if (cmd[0].equals("inflate")) {
					// active.coats.add(new Coat(cmd[4]));
				} else if (cmd[0].equals("rshift")) {
					active.coats.add(new Coat(cmd[3]));
					System.out.println("Added rshift coat to " + active.name);
				} else if (cmd[0].equals("reverb")) {
					// TODO
				} else if (cmd[0].equals("spatial")) {
					// TODO
				} else if (cmd[0].equals("ushift")) {
					// TODO
				} else if (cmd[0].equals("pulsar")) {
					active.coats.add(new Coat(cmd[10]));
					active.coats.add(new Coat(cmd[11]));
					System.out.println("Added fP and fD coats to " + active.name);
				} else if (cmd[0].equals("layer")) {
					Stripe stripe = new Stripe(cmd[1]);
					stripes.add(stripe);
					active = stripe;
					System.out.println("Created new stripe: " + stripe.name);
				} else if (cmd[0].equals("env")) {
					int dim = (int) (Double.parseDouble(cmd[1]) * TIME_RES);
					Palette env = new Palette(dim);
					int tagCount = cmd.length / 2 - 1;
					for (int i = 0; i < tagCount; i++) {
						int pos = (int) (Double.parseDouble(cmd[2 * i + 2]) * TIME_RES);
						int gain = (int) (255 * Double.parseDouble(cmd[2 * i + 3]));
						env.add(new ColorTag(gain, pos));
					}
					active.envelope = env;
					System.out.println("Envelope applied to " + active.name + ": " + env);
				}
			}
		}
	}
}
