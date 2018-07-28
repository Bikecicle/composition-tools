package main;

import evolution.core.Genome;
import evolution.core.Splicer;

public class Sequence implements Genome {

	private static final long serialVersionUID = 2759154287214849102L;

	public final int STRIKE_MAX = 64;
	public final float MUTATION_RATE = 0.1f;

	int strikeCount;
	int length; // In measures
	int quant; // Timesteps per measure
	float tempo;

	int[] strt;
	float[] pos;
	float[] att;
	float[] dec;
	float[] sus;
	float[] rel;
	float[] slev;

	double mRate;
	double score;

	public Sequence(int length, int quant, float tempo) {
		this.length = length;
		this.quant = quant;
		this.tempo = tempo;
		
		strt = new int[STRIKE_MAX];
		pos = new float[STRIKE_MAX];
		att = new float[STRIKE_MAX];
		dec = new float[STRIKE_MAX];
		sus = new float[STRIKE_MAX];
		rel = new float[STRIKE_MAX];
		slev = new float[STRIKE_MAX];
		
		mRate = Session.MUTATION_RATE;
		score = 0;
	}

	@Override
	public Genome breed(Genome g1) {
		Sequence other = (Sequence) g1;
		Sequence child = new Sequence(length, quant, tempo);
		child.strt = Splicer.spliceInt(this.strt, other.strt);
		child.pos = Splicer.spliceFloat(this.pos, other.pos);
		child.att = Splicer.spliceFloat(this.att, other.att);
		child.dec = Splicer.spliceFloat(this.dec, other.dec);
		child.sus = Splicer.spliceFloat(this.sus, other.sus);
		child.rel = Splicer.spliceFloat(this.rel, other.rel);
		child.slev = Splicer.spliceFloat(this.slev, other.slev);
		child.mutate();
		return null;
	}

	@Override
	public void randomize() {
		int tInt = (int) (Math.random() * quant) + 1;
		int phase = (int) (Math.random() * tInt);
		strikeCount = length * quant / tInt;
		for (int i = 0; i < STRIKE_MAX; i++) {
			if (i < strikeCount)
				strt[i] = i * tInt + phase;
			else
				strt[i] = (int) (Math.random() * length * quant);
			pos[i] = (float) Math.random();
			att[i] = (float) Math.random();
			dec[i] = (float) Math.random();
			sus[i] = (float) Math.random();
			rel[i] = (float) Math.random();
			slev[i] = (float) Math.random();
		}
	}

	@Override
	public double getScore() {
		return score;
	}

	@Override
	public int getId() {
		return hashCode();
	}

	public void mutate() {
		for (int i = 0; i < STRIKE_MAX; i++) {
			strt[i] = (int) (Math.random() < mRate ? Math.random() * length * quant : strt[i]);
			pos[i] = (float) (Math.random() < mRate ? Math.random() : pos[i]);
			att[i] = (float) (Math.random() < mRate ? Math.random() : att[i]);
			dec[i] = (float) (Math.random() < mRate ? Math.random() : dec[i]);
			sus[i] = (float) (Math.random() < mRate ? Math.random() : sus[i]);
			rel[i] = (float) (Math.random() < mRate ? Math.random() : rel[i]);
			slev[i] = (float) (Math.random() < mRate ? Math.random() : slev[i]);
		}
	}
	
	public float getQuantLength() {
		return 240 / tempo / quant;
	}
}
