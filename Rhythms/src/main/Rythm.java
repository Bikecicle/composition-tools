package main;

import evolution.core.EvolutionManager;
import evolution.core.Population;

public class Rythm {
	
	EvolutionManager[] timbres;
	EvolutionManager[] sequences;
	int voiceCount;
	
	public Rythm(int voiceCount) {
		this.voiceCount = voiceCount;
		timbres = new EvolutionManager[voiceCount];
		sequences = new EvolutionManager[voiceCount];
		for (int i = 0; i < voiceCount; i++) {
			//timbres[i] = new EvolutionManager();
		}
	}
	
	private Population initTimbre() {
		return null;
	}
	
	private Population initSequence() {
		return null;
	}
}
