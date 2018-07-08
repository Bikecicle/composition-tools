package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import evolution.algorithms.select.Selector;
import evolution.algorithms.select.SurvivalThreshold;
import evolution.core.EvolutionManager;
import evolution.core.Population;

public class Session {
	
	private final double survivalCf = 0.3;
	private final int popSize = 12;
	private final int batchRep = 2;
	
	String sample;
	EvolutionManager[] timbreEM;
	EvolutionManager[] sequenceEM;
	int voiceCount;
	int quant;
	float tempo;
	
	public Session(int voiceCount) {
		this.voiceCount = voiceCount;
		timbreEM = new EvolutionManager[voiceCount];
		sequenceEM = new EvolutionManager[voiceCount];
		for (int v = 0; v < voiceCount; v++) {
			Selector selector = new SurvivalThreshold(survivalCf);
			
			Population initPopT = new Population();
			Population initPopS = new Population();
			for (int g = 0; g < popSize; g++) {
				initPopT.add(new Timbre(sample, quant, tempo));
				initPopS.add(new Sequence(sample));
			}
			timbreEM[v] = new EvolutionManager(initPopT, selector, false);			
			sequenceEM[v] = new EvolutionManager(initPopS, selector, false);
		}
	}
	
	public List<Rhythm> createBatch() {
		List<Rhythm> batch = new ArrayList<>();
		// List of lists, each corresponding to a voice and containing shuffled indices for that voice's population
		List<List<Integer>> indicesT = new ArrayList<>();
		List<List<Integer>> indicesS = new ArrayList<>();
		List<Integer> set = new LinkedList<>();
		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < batchRep; j++) {
				set.add(i);	
			}	
		}
		int batchSize = popSize * batchRep;
		for (int v = 0; v < voiceCount; v++) {
			Collections.shuffle(set);
			List<Integer> voiceT = new ArrayList<>(set);
			indicesT.add(voiceT);
			Collections.shuffle(set);
			List<Integer> voiceS = new ArrayList<>(set);
			indicesS.add(voiceS);
		}
		for (int b = 0; b < batchSize; b++) {
			Timbre[] timbres = new Timbre[voiceCount];
			Sequence[] sequences = new Sequence[voiceCount];
			for (int v = 0; v < voiceCount; v++) {
				// Set each voice to a genome from its population according to the shuffled indices
				timbres[v] = (Timbre) timbreEM[v].getPop().get(indicesT.get(v).get(b));
				sequences[v] = (Sequence) sequenceEM[v].getPop().get(indicesS.get(v).get(b));
			}
			batch.add(new Rhythm(sample, timbres, sequences, voiceCount));
		}
		return batch;
	}
	
	public void advance() {
		for (int v = 0; v < voiceCount; v++) {
			timbreEM[v].nextGeneration();
			sequenceEM[v].nextGeneration();
		}
	}
}
