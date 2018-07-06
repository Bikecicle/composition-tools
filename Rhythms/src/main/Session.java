package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
		for (int i = 0; i < voiceCount; i++) {
			Selector selector = new SurvivalThreshold(survivalCf);
			
			Population initPopT = new Population();
			Population initPopS = new Population();
			for (int j = 0; j < popSize; j++) {
				initPopT.add(new Timbre(sample, quant, tempo));
				initPopS.add(new Sequence(sample));
			}
			timbreEM[i] = new EvolutionManager(initPopT, selector, false);			
			sequenceEM[i] = new EvolutionManager(initPopS, selector, false);
		}
	}
	
	public List<Rhythm> createBatch() {
		List<Rhythm> batch = new ArrayList<>();
		List<List<Integer>> indicesT = new ArrayList<>();
		List<List<Integer>> indicesS = new ArrayList<>();
		List<Integer> set = new LinkedList<>();
		for (int i = 0; i < popSize; i++) {
			for (int j = 0; j < batchRep; j++) {
				set.add(i);	
			}	
		}
		for (int i = 0; i < )
		Collections.shuffle(set);
		indicesT.add(set);
		
		int batchSize = voiceCount * batchRep;
		for (int i = 0; i < batchSize; i++) {
			Timbre[] timbres = new Timbre[voiceCount];
			Sequence[] sequences = new Sequence[voiceCount];
			for (int j = 0; j < voiceCount; j++) {
				timbres[j] = indices.get(index)
			}
			Rhythm rhythm = new Rhythm(timbres, sequences, voiceCount);
		}
		return batch;
	}
}
