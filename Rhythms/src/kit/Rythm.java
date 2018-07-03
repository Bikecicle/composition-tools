package kit;

import evolution.algorithms.select.Selector;
import evolution.algorithms.select.SurvivalThreshold;
import evolution.core.EvolutionManager;
import evolution.core.Population;

public class Rythm {
	
	private final double survivalCf = 0.3;
	private final int popSizeT = 10;
	private final int popSizeS = 10;
	
	String sample;
	EvolutionManager[] timbres;
	EvolutionManager[] sequences;
	int voiceCount;
	int quant;
	float tempo;
	
	public Rythm(int voiceCount) {
		this.voiceCount = voiceCount;
		timbres = new EvolutionManager[voiceCount];
		sequences = new EvolutionManager[voiceCount];
		for (int i = 0; i < voiceCount; i++) {
			Selector selector = new SurvivalThreshold(survivalCf);
			
			Population initPopT = new Population();
			for (int t = 0; t < popSizeT; t++) {
				initPopT.add(new Timbre(sample, quant, tempo));
			}
			timbres[i] = new EvolutionManager(initPopT, selector, false);
			
			Population initPopS = new Population();
			for (int s = 0; s < popSizeS; s++) {
				initPopS.add(new Sequence(sample));
			}
			sequences[i] = new EvolutionManager(initPopS, selector, false);
		}
	}
}
