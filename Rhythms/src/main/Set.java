package main;

import java.util.ArrayList;
import java.util.List;

import evolution.core.Genome;

public class Set extends ArrayList<Rhythm>{
	
	private static final long serialVersionUID = -8481421560399201146L;
	
	List<Genome> timbres;
	List<Genome> sequences;
	List<String> samples;
	
	public Set() {
		super();
		timbres = new ArrayList<>();
		sequences = new ArrayList<>();
		samples = new  ArrayList<>();
	}
	
	@Override
	public boolean add(Rhythm e) {
		for (int v = 0; v < e.voiceCount; v++) {
			timbres.add(e.timbres[v]);
			sequences.add(e.sequences[v]);
			samples.add(e.samples[v]);
		}
		return super.add(e);
	}
}
