package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import grain.Inflate;
import grain.NoiseBand;
import grain.Normalize;
import grain.Note;
import grain.OverlaySample;
import grain.PulsarMatrix;
import grain.RandomShift;
import grain.ShredSample;
import grain.IntegralWarp;
import table.EdgeDetection;
import table.Integrate;
import table.Power;
import table.Table;

public class ScriptReader {

	private FractalSynth fractalSynth;
	private Scanner in;

	public ScriptReader(String script) {
		try {
			in = new Scanner(new File("scripts/" + script));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fractalSynth = new FractalSynth();
		fractalSynth.openProject(script);
	}

	public ScriptReader() {
		Scanner userin = new Scanner(System.in);
		System.out.println("Script name:");
		String script = userin.nextLine();
		userin.close();
		try {
			in = new Scanner(new File("scripts/" + script));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fractalSynth = new FractalSynth();
		fractalSynth.openProject(script);
	}

	public void execute() {
		String currentTable = null;
		boolean replace = false;
		boolean skip = false;
		while (in.hasNext()) {
			String[] cmd = in.nextLine().split(" ");
			if (!cmd[0].equals("") && !cmd[0].startsWith("#")) {

				// Grain management
				if (cmd[0].equals("inflate") && !skip) {
					System.out.println("Resizing grains...");
					if (cmd.length == 4) {
						fractalSynth.applyMod(new Inflate(Double.parseDouble(cmd[1]), Double.parseDouble(cmd[2]),
								fractalSynth.getTable(cmd[3])));
					} else if (cmd.length == 2) {
						fractalSynth.applyMod(new Inflate(Double.parseDouble(cmd[1])));
					}
				} else if (cmd[0].equals("rshift") && !skip) {
					System.out.println("Shifting grains...");
					if (cmd.length == 3) {
						fractalSynth
								.applyMod(new RandomShift(Double.parseDouble(cmd[1]), fractalSynth.getTable(cmd[2])));
					} else if (cmd.length == 2) {
						fractalSynth.applyMod(new RandomShift(Double.parseDouble(cmd[1])));
					}
				} else if (cmd[0].equals("reverb") && !skip) {
					// TODO
				} else if (cmd[0].equals("spatial") && !skip) {
					// TODO
				} else if (cmd[0].equals("normalize") && !skip) {
					System.out.println("Normalizing amplitude...");
					fractalSynth.applyMod(new Normalize(Integer.parseInt(cmd[1])));
				} else if (cmd[0].equals("warp") && !skip) {
					System.out.println("Time-warping...");
					fractalSynth.applyMod(new IntegralWarp(fractalSynth.getTable(cmd[1])));
				} else if (cmd[0].equals("pulsar") && !skip) {
					System.out.println("Generating pulsar matrix...");
					int fMinP = Integer.parseInt(cmd[1]);
					int fMaxP = Integer.parseInt(cmd[2]);
					int fResP = Integer.parseInt(cmd[3]);
					int fMinD = Integer.parseInt(cmd[4]);
					int fMaxD = Integer.parseInt(cmd[5]);
					int minResD = Integer.parseInt(cmd[6]);
					int maxResD = Integer.parseInt(cmd[7]);
					double zoomVel = Double.parseDouble(cmd[8]);
					int zoomMax = Integer.parseInt(cmd[9]);
					Table tableP = fractalSynth.getTable(cmd[10]);
					Table tableD = fractalSynth.getTable(cmd[11]);
					int n = fractalSynth.generateGrains(new PulsarMatrix(fMinP, fMaxP, fResP, fMinD, fMaxD, minResD, maxResD, zoomVel, zoomMax, tableP, tableD));
					System.out.println("Generated matrix of size " + n);

				} else if (cmd[0].equals("noise") && !skip) {
					System.out.println("Generating noise band...");
					int n = fractalSynth.generateGrains(new NoiseBand(Integer.parseInt(cmd[1]),
							Integer.parseInt(cmd[2]), Double.parseDouble(cmd[3]), Double.parseDouble(cmd[4])));
					System.out.println("Generated matrix of size " + n);

				} else if (cmd[0].equals("note") && !skip) {
					System.out.println("Adding a note...");
					fractalSynth.generateGrains(
							new Note(Double.parseDouble(cmd[1]), Double.parseDouble(cmd[2]), Integer.parseInt(cmd[3])));
				} else if (cmd[0].equals("shred") && !skip) {
					System.out.println("Shredding sample " + cmd[8] + "...");
					int bandCount = Integer.parseInt(cmd[1]);
					int fMin = Integer.parseInt(cmd[2]); // Frequency min
					int fMax = Integer.parseInt(cmd[3]); // Frequency max
					float sMin = Float.parseFloat(cmd[4]); // Speed min
					float sMax = Float.parseFloat(cmd[5]); // Speed max
					int sRes = Integer.parseInt(cmd[6]); // Segments / second
					double duration = Double.parseDouble(cmd[7]); // Duration in seconds
					Table table = fractalSynth.getTable(cmd[8]);
					String sample = cmd[9];
					fractalSynth.generateGrains(new ShredSample(bandCount, fMin, fMax, sMin, sMax, sRes, duration, table, sample));
				} else if (cmd[0].equals("overlay")) {
					System.out.println("Overlaying sample " + cmd[1] + "...");
					if (cmd.length == 4) {
						fractalSynth.applyMod(
								new OverlaySample(cmd[1], Float.parseFloat(cmd[2]), Integer.parseInt(cmd[3])));
					} else if (cmd.length == 3) {
						fractalSynth.applyMod(new OverlaySample(cmd[1], 1f, Integer.parseInt(cmd[2])));
					} else if (cmd.length == 2) {
						fractalSynth.applyMod(new OverlaySample(cmd[1]));
					}
				} else if (cmd[0].equals("copy")) {
					System.out.println("Copying grains from " + cmd[1] + " to " + fractalSynth.getActiveLayerName());
					fractalSynth.copyFrom(cmd[1]);
				} else if (cmd[0].equals("layer")) {
					if (!fractalSynth.hasLayer(cmd[1]) || replace) {
						System.out.println("[Switching to layer: " + cmd[1] + "]");
						skip = false;
						if (!fractalSynth.changeActiveLayer(cmd[1])) {
							System.out.println("Creating...");
							if (cmd.length == 3) {
								fractalSynth.newLayer(cmd[1], Double.parseDouble(cmd[2]));
							} else if (cmd.length == 2) {
								fractalSynth.newLayer(cmd[1], 0);
							}
						} else {
							System.out.println("Clearing...");
							fractalSynth.clearLayer();
						}
					} else {
						System.out.println("[Skipping layer: " + cmd[1] + "]");
						skip = true;
					}
				} else if (cmd[0].equals("render")) {
					fractalSynth.renderAll(cmd[1]);
				}

				// Table management
				else if (cmd[0].equals("table")) {
					if (!fractalSynth.hasTable(cmd[2]) || replace) {
						skip = false;
						System.out.println("[Generating new table: " + cmd[2] + "]");
						if (cmd[1].equals("new")) {
							fractalSynth.addTable(cmd[2], Integer.parseInt(cmd[3]), Integer.parseInt(cmd[4]),
									Double.parseDouble(cmd[5]), Integer.parseInt(cmd[6]), Integer.parseInt(cmd[7]),
									Double.parseDouble(cmd[8]), Double.parseDouble(cmd[9]));
						} else if (cmd[1].equals("copy")) {
							fractalSynth.addTable(cmd[2], cmd[3]);
						}
						currentTable = cmd[2];
					} else {
						System.out.println("[Skipping table: " + cmd[2] + "]");
						skip = true;
					}
				} else if (cmd[0].equals("edge") && !skip) {
					System.out.println("Applying edge detection...");
					fractalSynth.filterTable(currentTable, new EdgeDetection(Integer.parseInt(cmd[1])));
				} else if (cmd[0].equals("power") && !skip) {
					System.out.println("Raising order...");
					fractalSynth.filterTable(currentTable, new Power(Double.parseDouble(cmd[1])));
				} else if (cmd[0].equals("invert") && !skip) {
					// TODO
				} else if (cmd[0].equals("plimit") && !skip) {
					// TODO
				} else if (cmd[0].equals("blur") && !skip) {
					// TODO
				} else if (cmd[0].equals("integrate") && !skip) {
					System.out.println("Integrating...");
					fractalSynth.filterTable(currentTable, new Integrate(Double.parseDouble(cmd[1]),
							Double.parseDouble(cmd[2]), Double.parseDouble(cmd[3])));
				}

				// Utility
				else if (cmd[0].equals("replace")) {
					replace = Boolean.parseBoolean(cmd[1]);
				} else if (cmd[0].equals("clear")) {
					fractalSynth.clear();
				}
			}
		}
		System.out.println("Script complete");
	}

	public static void main(String[] args) {
		ScriptReader scriptReader;
		if (args.length == 1) {
			scriptReader = new ScriptReader(args[0]);
		} else {
			scriptReader = new ScriptReader();
		}
		scriptReader.execute();
	}
}
