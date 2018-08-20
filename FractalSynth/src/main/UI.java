package main;

import java.util.Scanner;

import grain.mod.Inflate;
import grain.mod.RandomShift;
import table.Cutoff;
import table.Amplify;
import table.Blur;
import table.Denoise;
import table.Differentiate;
import table.EdgeDetection;
import table.Invert;
import table.Power;
import table.PulseLimiter;
import table.Table;

public class UI {

	private static FractalSynth fractalSynth;
	private static Scanner in;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		startup();
		mainMenu();
	}

	private static void startup() {
		in = new Scanner(System.in);
		System.out.println("--FractalSynth--");
		fractalSynth = new FractalSynth();
	}

	private static void mainMenu() {
		System.out.println("Main Menu - Current Project: " + fractalSynth.getProject());
		System.out.println("(T)able menu, (G)rain menu, (X)-exit");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("t")) {
			tableMenu();
		} else if (action.toLowerCase().startsWith("g")) {
			grainMenu();
		} else if (action.toLowerCase().startsWith("x")) {
			System.out.println("Exiting...");
		} else {
			invalid();
			mainMenu();
		}
	}

	private static void invalid() {
		System.out.println("Invalid input");
	}

	private static void tableMenu() {
		System.out.println("Table menu");
		System.out.println("(E)dit, (N)ew, (X)-back");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("e")) {
			editTable();
		} else if (action.toLowerCase().startsWith("n")) {
			newTable();
		} else if (action.toLowerCase().startsWith("x")) {
			mainMenu();
		} else {
			invalid();
			tableMenu();
		}
	}

	private static void editTable() {
		System.out.println("Saved tables:");
		for (String name : fractalSynth.getTableNames()) {
			System.out.println("- " + name);
		}
		System.out.println("Select table:");
		String name = in.nextLine();
		System.out.println("Edit table - " + name);
		System.out.println("(F)ilter, (D)elete, (P)roperties, (X)-back");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("f")) {
			filter(name);
		} else if (action.toLowerCase().startsWith("d")) {
			fractalSynth.deleteTable(name);
			tableMenu();
		} else if (action.toLowerCase().startsWith("p")) {
			System.out.println(fractalSynth.getTableProperties(name));
			editTable();
		} else if (action.toLowerCase().startsWith("x")) {
			tableMenu();
		} else {
			invalid();
			editTable();
		}
	}

	private static void filter(String name) {
		System.out.println("Apply filter to " + name);
		System.out.println("(E)dge detection, (B)lur, (P)ulse limiter, (D)enoise, (C)utoff, di(F)ferentiate,\n"
				+ "(A)mplify, p(O)wer, (I)nvert, (X)-back");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("e")) {
			System.out.println("Threshold:");
			int threshold = in.nextInt();
			fractalSynth.filterTable(name, new EdgeDetection(threshold));
		} else if (action.toLowerCase().startsWith("b")) {
			fractalSynth.filterTable(name, new Blur());
		} else if (action.toLowerCase().startsWith("p")) {
			fractalSynth.filterTable(name, new PulseLimiter());
		} else if (action.toLowerCase().startsWith("d")) {
			fractalSynth.filterTable(name, new Denoise());
		} else if (action.toLowerCase().startsWith("c")) {
			System.out.println("Threshold:");
			int threshold = in.nextInt();
			fractalSynth.filterTable(name, new Cutoff(threshold));
		} else if (action.toLowerCase().startsWith("f")) {
			fractalSynth.filterTable(name, new Differentiate());
		} else if (action.toLowerCase().startsWith("a")) {
			System.out.println("Gain:");
			double gain = in.nextDouble();
			fractalSynth.filterTable(name, new Amplify(gain));
		} else if (action.toLowerCase().startsWith("o")) {
			System.out.println("Exponent:");
			double exponent = in.nextDouble();
			fractalSynth.filterTable(name, new Power(exponent));
		} else if (action.toLowerCase().startsWith("i")) {
			fractalSynth.filterTable(name, new Invert());
		} else if (action.toLowerCase().startsWith("x")) {
			tableMenu();
		} else {
			invalid();
		}
		filter(name);
	}

	private static void newTable() {
		System.out.println("New table");
		System.out.println("Name:");
		String name = in.nextLine();
		System.out.println("(M)anual or (C)opy?");
		String mode = in.nextLine();
		if (mode.toLowerCase().startsWith("m")) {
			System.out.println("Time resolution (steps/sec):");
			int tRes = in.nextInt();
			System.out.println("Frequency resolution (steps/2pi):");
			int fRes = in.nextInt();
			System.out.println("Zoom velocity (*/sec):");
			double zoomVel = in.nextDouble();
			System.out.println("Zoom max (10^n):");
			int zoomMax = in.nextInt();
			System.out.println("Max iterations:");
			int iterMax = in.nextInt();
			System.out.println("X position:");
			double posX = in.nextDouble();
			System.out.println("Y position:");
			double posY = in.nextDouble();
			in.nextLine();
			fractalSynth.generateFractalTable(name, tRes, fRes, zoomVel, zoomMax, iterMax, posX, posY);
		} else if (mode.toLowerCase().startsWith("c")) {
			System.out.println("Table to copy:");
			String other = in.nextLine();
			fractalSynth.generateFractalTable(name, other);
		}
		tableMenu();
	}

	private static void grainMenu() {
		if (!fractalSynth.projectLoaded()) {
			System.out.println("Project:");
			String project = in.nextLine();
			fractalSynth.openProject(project);
		}
		String active = fractalSynth.getActiveLayerName();
		System.out.println("Grain menu - Active layer: " + active);
		System.out.println("All layers:");
		for (String layer : fractalSynth.getLayerNames())
			System.out.println(" - " + layer);
		System.out.println(
				"(G)enerate, (M)odify, r(E)name layer, (C)lear layer, select (L)ayer, (N)ew layer, (V)isualize, (R)ender, (S)ave, (X)-back");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("g")) {
			generateGrains();
		} else if (action.toLowerCase().startsWith("m")) {
			modifyLayer();
		} else if (action.toLowerCase().startsWith("e")) {
			System.out.println("New name:");
			String name = in.nextLine();
			boolean success = fractalSynth.renameLayer(name);
			if (success)
				System.out.println("Active layer successfully renamed");
			else
				System.out.println("Error: name already in use");
			grainMenu();
		} else if (action.toLowerCase().startsWith("c")) {
			fractalSynth.clearLayer();
			grainMenu();
		} else if (action.toLowerCase().startsWith("l")) {
			System.out.println("Layer name:");
			String name = in.nextLine();
			fractalSynth.changeActiveLayer(name);
			grainMenu();
		} else if (action.toLowerCase().startsWith("n")) {
			System.out.println("New layer name:");
			String name = in.nextLine();
			System.out.println("New layer duration:");
			double duration = in.nextDouble();
			in.nextLine();
			fractalSynth.newLayer(name, duration);
			grainMenu();
		} else if (action.toLowerCase().startsWith("v")) {
			fractalSynth.visualizeLayers();
			grainMenu();
		} else if (action.toLowerCase().startsWith("r")) {
			System.out.println("Output name:");
			String title = in.nextLine();
			fractalSynth.renderAll(title);
			;
			grainMenu();
		} else if (action.toLowerCase().startsWith("s")) {
			fractalSynth.saveLayers();
			grainMenu();
		} else if (action.toLowerCase().startsWith("x")) {
			mainMenu();
		} else {
			invalid();
			grainMenu();
		}
	}

	private static void modifyLayer() {
		System.out.println("Modify layer - " + fractalSynth.getActiveLayerName());
		System.out.println("(R)andom shift, (S)patialize, re(V)erb, (I)nflate, (X)-back");
		String mod = in.nextLine();
		if (mod.toLowerCase().startsWith("r")) {
			System.out.println("Maximum shift radius (sec):");
			double rMax = in.nextDouble();
			System.out.println("Zoom velocity (*/sec)");
			//double zoomVel = in.nextDouble();
			in.nextLine();
			System.out.println("Source table name:");
			String name = in.nextLine();
			Table table = fractalSynth.getTable(name);
			int count = fractalSynth.applyMod(new RandomShift(rMax, table));
			System.out.println("Complete - " + count + " grains modified");
			modifyLayer();
		} else if (mod.toLowerCase().startsWith("s")) {
			modifyLayer();
		} else if (mod.toLowerCase().startsWith("v")) {
			modifyLayer();
		} else if (mod.toLowerCase().startsWith("i")) {
			System.out.println("Minimum grain duration (sec):");
			double dMin = in.nextDouble();
			System.out.println("Maximum grain duration (sec):");
			double dMax = in.nextDouble();
			System.out.println("Zoom velocity");
			//double zoomVel = in.nextDouble();
			in.nextLine();
			System.out.println("Source table name:");
			String name = in.nextLine();
			Table table = fractalSynth.getTable(name);
			int count = fractalSynth.applyMod(new Inflate(dMin, dMax, table));
			System.out.println("Complete - " + count + " grains modified");
			modifyLayer();
		} else if (mod.toLowerCase().startsWith("x")) {
			grainMenu();
		} else {
			invalid();
			modifyLayer();
		}
	}

	private static void generateGrains() {
		System.out.println("Generate grains");
		System.out.println("(M)atrix, (P)ulsar matrix, (x)-back");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("m")) {
			// TODO
			grainMenu();
		} else if (action.toLowerCase().startsWith("p")) {
			System.out.println("Generate pulsar matrix");
			System.out.println("Minimum pulsar frequency (Hz):");
			int fMinP = in.nextInt();
			System.out.println("Maximum pulsar frequency (Hz):");
			int fMaxP = in.nextInt();
			System.out.println("Pulsar frequency resolution (steps/2pi):");
			int fResP = in.nextInt();
			System.out.println("Duty cycle minimum frequency (Hz):");
			int fMinD = in.nextInt();
			System.out.println("Duty cycle maximum frequency (Hz):");
			int fMaxD = in.nextInt();
			System.out.println("Duty cycle minimum resolution (grains/pulsar):");
			int minResD = in.nextInt();
			System.out.println("Duty cycle maximum resolution (grains/pulsar):");
			int maxResD = in.nextInt();
			System.out.println("Duration (sec):");
			float dur = in.nextFloat();
			in.nextLine();
			System.out.println("Pulsar table name:");
			String tablePName = in.nextLine();
			System.out.println("Duty cycle table name:");
			String tableDName = in.nextLine();
			fractalSynth.genPulsarMatrix(fMinP, fMaxP, fResP, fMinD, fMaxD, minResD, maxResD, dur,
					tablePName, tableDName);
			grainMenu();
		} else if (action.toLowerCase().startsWith("x")) {
			grainMenu();
		} else {
			invalid();
			generateGrains();
		}
	}
}
