package main;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import grain.GrainManager;
import table.TableManager;

public class FractalSynth {
	
	private GrainManager grainManager;
	private TableManager tableManager;
	
	public FractalSynth() {
		grainManager = new GrainManager();
		tableManager = new TableManager();
	}
	
	public static void main(String[] args) {
		FractalSynth synth = new FractalSynth();
	}
	
}
