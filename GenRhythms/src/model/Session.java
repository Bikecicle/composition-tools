package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import evolution.algorithms.select.ExemplarInject;
import evolution.algorithms.select.RandomSelect;
import evolution.algorithms.select.Selector;
import evolution.algorithms.select.SurvivalThreshold;
import evolution.core.EvolutionManager;
import evolution.core.Genome;
import evolution.core.Population;

public class Session implements Serializable {

	private static final long serialVersionUID = 1841382270270688255L;

	public static final String SESSION_EXT = ".rhy";
	public static final String SET_EXT = ".set";

	public static final float survivalCf = 0.3f;
	public static final float mutationRate = 0.1f;
	public static final int popSize = 12;
	public static final int batchRep = 1;

	public static final boolean logging = true;

	public String title;
	public String sessionDir;
	public Set set;

	EvolutionManager[] timbreEM;
	EvolutionManager[] sequenceEM;
	String sampleDir;
	int voiceCount;
	int length;
	int quant;
	float tempo;
	
	int stage;

	public Session(String title, int voiceCount, int length, int quant, float tempo,
			String sampleDir) {
		this.title = title;
		this.voiceCount = voiceCount;
		this.length = length;
		this.quant = quant;
		this.tempo = tempo;
		this.sampleDir = sampleDir;
		timbreEM = new EvolutionManager[voiceCount];
		sequenceEM = new EvolutionManager[voiceCount];
		set = new Set();
		for (int v = 0; v < voiceCount; v++) {
			Selector selectorT = new ExemplarInject(new SurvivalThreshold(new RandomSelect(), survivalCf), set.timbres);
			Selector selectorS = new ExemplarInject(new SurvivalThreshold(new RandomSelect(), survivalCf), set.sequences);
			Population initPopT = new Population();
			Population initPopS = new Population();
			for (int g = 0; g < popSize; g++) {
				Timbre timbre = new Timbre(sampleDir);
				timbre.randomize();
				initPopT.add(timbre);
				Sequence sequence = new Sequence(length, quant, tempo);
				sequence.randomize();
				initPopS.add(sequence);
			}
			timbreEM[v] = new EvolutionManager(initPopT, selectorT, logging);
			sequenceEM[v] = new EvolutionManager(initPopS, selectorS, logging);
		}
		stage = 1;
	}
	
	public static Session load(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
		Session session = (Session) in.readObject();
		in.close();
		return session;
	}

	public Batch createBatch() {
		Batch batch = new Batch();
		// List of lists, each corresponding to a voice and containing shuffled indices
		// for that voice's population
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
				// Set each voice to a genome from its population according to the shuffled
				// indices
				timbres[v] = (Timbre) timbreEM[v].getPop().get(indicesT.get(v).get(b));
				sequences[v] = (Sequence) sequenceEM[v].getPop().get(indicesS.get(v).get(b));
			}
			batch.add(new Rhythm(timbres, sequences));
		}
		return batch;
	}

	public void advance() {
		for (int v = 0; v < voiceCount; v++) {
			timbreEM[v].nextGeneration();
			for (Genome t : timbreEM[v].getPop()) {
				System.out.println(((Timbre) t).sample);
			}
			sequenceEM[v].nextGeneration();
		}
		stage++;
	}
	
	public void addToSet(Rhythm rhythm) {
		set.add(rhythm);
	}
	
	public void save(File file) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(this);
		out.close();
	}

	public void exportSet(File file) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(set);
		out.close();
	}
	
	public void importSet(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		set.addAll((Set) in.readObject());
		in.close();
	}
	
	public Set getSet() {
		return set;
	}
	
	public int getStage() {
		return stage;
	}
	
	public float getTempo() {
		return tempo;
	}

	public void setTempo(float tempo) {
		this.tempo = tempo;
	}
}
