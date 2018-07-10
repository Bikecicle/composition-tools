package main;

import evolution.core.Genome;

public class Sequence implements Genome {
	
	private static final long serialVersionUID = 2759154287214849102L;

	String sample;
	Strike[] strikes;
	int strikeCount;
	int quant;
	
	double score;
	
	public Sequence(String sample, int quant) {
		this.sample = sample;
		this.quant = quant;
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
	
	public static float[] splice(float[] g1, float[] g2) {
		int a = (int) (Math.random() * g1.length);
		int b = (int) (Math.random() * g1.length);
		if (a > b) {
			int t = a;
			a = b;
			b = t;
		}
		float[] g3 = new float[g1.length];
		for (int i = 0; i < g1.length; i++) {
			if (i < a) {
				g3[i] = g1[i];
			} else if (i < b) {
				g3[i] = g2[i];
			} else {
				g3[i] = g1[i];
			}
		}
		return g3;
	}

	public void randomize() {
		int initFreq = (int) (Math.random() * quant) + 1;
		
	}
}
