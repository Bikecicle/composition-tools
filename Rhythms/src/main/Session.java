package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import evolution.algorithms.select.RandomSelect;
import evolution.algorithms.select.Selector;
import evolution.algorithms.select.SurvivalThreshold;
import evolution.core.EvolutionManager;
import evolution.core.Population;

public class Session implements Serializable {

	private static final long serialVersionUID = 1841382270270688255L;
	
	public static final float SURVIVAL_CF = 0.3f;
	public static final float MUTATION_RATE = 0.1f;
	public static final int POP_SIZE = 12;
	public static final int BATCH_REP = 1;

	String title;
	String sessionDir;
	List<Rhythm> saved;
	
	EvolutionManager[] timbreEM;
	EvolutionManager[] sequenceEM;
	String[] samples;
	int voiceCount;
	int length;
	int quant;
	float tempo;

	public Session(String title, String mainDir, int voiceCount, int length, int quant, float tempo, String... samples) {
		this.title = title;
		sessionDir = mainDir + title + "/";
		this.voiceCount = voiceCount;
		this.length = length;
		this.quant = quant;
		this.tempo = tempo;
		this.samples = samples;
		timbreEM = new EvolutionManager[voiceCount];
		sequenceEM = new EvolutionManager[voiceCount];
		for (int v = 0; v < voiceCount; v++) {
			Selector selector = new SurvivalThreshold(new RandomSelect(), SURVIVAL_CF);
			Population initPopT = new Population();
			Population initPopS = new Population();
			for (int g = 0; g < POP_SIZE; g++) {
				Timbre timbre = new Timbre();
				timbre.randomize();
				initPopT.add(timbre);
				Sequence sequence = new Sequence(length, quant, tempo);
				sequence.randomize();
				initPopS.add(sequence);
			}
			timbreEM[v] = new EvolutionManager(initPopT, selector, false);
			sequenceEM[v] = new EvolutionManager(initPopS, selector, false);
			saved = new ArrayList<>();
		}
	}
	
	public Session(String title, String mainPath) {
		
	}
	

	public List<Rhythm> createBatch() {
		List<Rhythm> batch = new ArrayList<>();
		// List of lists, each corresponding to a voice and containing shuffled indices
		// for that voice's population
		List<List<Integer>> indicesT = new ArrayList<>();
		List<List<Integer>> indicesS = new ArrayList<>();
		List<Integer> set = new LinkedList<>();
		for (int i = 0; i < POP_SIZE; i++) {
			for (int j = 0; j < BATCH_REP; j++) {
				set.add(i);
			}
		}
		int batchSize = POP_SIZE * BATCH_REP;
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
				// Set each voice to a genome from its population according to the shuffled
				// indices
				timbres[v] = (Timbre) timbreEM[v].getPop().get(indicesT.get(v).get(b));
				sequences[v] = (Sequence) sequenceEM[v].getPop().get(indicesS.get(v).get(b));
			}
			batch.add(new Rhythm(samples, timbres, sequences));
		}
		return batch;
	}

	public void advance() {
		for (int v = 0; v < voiceCount; v++) {
			timbreEM[v].nextGeneration();
			sequenceEM[v].nextGeneration();
		}
	}
	
	public void save() {
		
	}
}
