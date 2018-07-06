package main;

import evolution.core.Genome;

public class Sequence implements Genome {
	
	private static final long serialVersionUID = 2759154287214849102L;

	String sample;
	Strike[] strikes;
	int strikeCount;
	
	double score;
	
	public Sequence(String sample) {
		
	}

	@Override
	public Genome breed(Genome other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
