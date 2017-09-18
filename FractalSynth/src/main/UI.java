package main;

import java.util.Scanner;

import table.Blur;
import table.Denoise;
import table.EdgeDetection;
import table.PulseLimiter;
import table.Table;

public class UI {

	private static FractalSynth fractalSynth;
	private static Scanner in;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		startup();
		mainMenu();
	}

	private static void startup() {
		in = new Scanner(System.in);
		System.out.println("--FractalSynth--");
		System.out.println("Project:");
		String project = in.nextLine();
		fractalSynth = new FractalSynth(project);
	}

	private static void mainMenu() {
		System.out.println("Main Menu - Current Project: " + fractalSynth.getProject());
		System.out.println("(T)able menu, (G)rain menu");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("t")) {
			tableMenu();
		} else if (action.toLowerCase().startsWith("g")) {
			grainMenu();
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
		System.out.println("(E)dit, (N)ew, (M)ain menu");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("e")) {
			editTable();
		} else if (action.toLowerCase().startsWith("n")) {
			newTable();
		} else if (action.toLowerCase().startsWith("m")) {
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
		System.out.println("(F)ilter, (D)elete, (P)roperties, (M)enu");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("f")) {
			filter(name);
		} else if (action.toLowerCase().startsWith("d")) {
			fractalSynth.deleteTable(name);
			tableMenu();
		} else if (action.toLowerCase().startsWith("p")) {
			System.out.println(fractalSynth.getTableProperties(name));
			editTable();
		} else if (action.toLowerCase().startsWith("m")) {
			tableMenu();
		} else {
			invalid();
			editTable();
		}
	}

	private static void filter(String name) {
		System.out.println("Apply filter to " + name);
		System.out.println("(E)dge detection, (B)lur, (P)ulse limiter, (D)enoise, (M)enu");
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
		} else if (action.toLowerCase().startsWith("m")) {
			tableMenu();
		} else {
			invalid();
		}
		filter(name);
	}

	private static void newTable() {
		System.out.println("New table");
		System.out.println("Name:");
		String name = in.next();
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
		fractalSynth.addTable(name, tRes, fRes, zoomVel, zoomMax, iterMax, posX, posY);
		tableMenu();
	}

	private static void grainMenu() {
		System.out.println("Grain menu");
		System.out.println("(S)elect layer, (N)ew layer, (M)ain menu");
		String action = in.nextLine();
		if (action.toLowerCase().startsWith("s")) {
			selectLayer();
		} else if (action.toLowerCase().startsWith("n")) {
			newLayer();
		} else if (action.toLowerCase().startsWith("m")) {
			mainMenu();
		} else {
			invalid();
			grainMenu();
		}
	}

	private static void selectLayer() {

	}

	private static void newLayer() {

	}

	private static void editLayer() {

	}
}
