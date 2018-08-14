package main;

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

	String title;
	String sessionDir;
	Set set;

	EvolutionManager[] timbreEM;
	EvolutionManager[] sequenceEM;
	String[] samples;
	int voiceCount;
	int length;
	int quant;
	float tempo;
	
	int stage;

	public Session(String mainDir, String title, int voiceCount, int length, int quant, float tempo,
			String... samples) {
		this.title = title;
		sessionDir = mainDir + title + "/";
		openDir(mainDir);
		openDir(sessionDir);
		this.voiceCount = voiceCount;
		this.length = length;
		this.quant = quant;
		this.tempo = tempo;
		this.samples = samples;
		timbreEM = new EvolutionManager[voiceCount];
		sequenceEM = new EvolutionManager[voiceCount];
		set = new Set();
		for (int v = 0; v < voiceCount; v++) {
			Selector selectorT = new ExemplarInject(new SurvivalThreshold(new RandomSelect(), survivalCf), set.timbres);
			Selector selectorS = new ExemplarInject(new SurvivalThreshold(new RandomSelect(), survivalCf), set.sequences);
			Population initPopT = new Population();
			Population initPopS = new Population();
			for (int g = 0; g < popSize; g++) {
				Timbre timbre = new Timbre();
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
	
	public static Session loadSession(String mainDir, String title) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(mainDir + title + "/" + title + SESSION_EXT));
		Session session = (Session) in.readObject();
		in.close();
		return session;
	}

	public List<Rhythm> createBatch() {
		List<Rhythm> batch = new ArrayList<>();
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
			batch.add(new Rhythm(samples, timbres, sequences));
		}
		return batch;
	}

	public void advance() {
		for (int v = 0; v < voiceCount; v++) {
			timbreEM[v].nextGeneration();
			sequenceEM[v].nextGeneration();
		}
		stage++;
	}
	
	public void addToSet(Rhythm rhythm) {
		set.add(rhythm);
	}

	public void saveSession() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(sessionDir + title + SESSION_EXT));
		out.writeObject(this);
		out.close();

	}

	public void saveSet(String setName) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(sessionDir + setName + SET_EXT));
		out.writeObject(set);
		out.close();
	}
	
	public void loadSet(String setName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(sessionDir + setName + SET_EXT));
		set = (Set) in.readObject();
		in.close();
	}
	
	
	private boolean openDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
			return true;
		}
		return false;
	}
}
