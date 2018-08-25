package model;

import evolution.core.Genome;
import evolution.core.Splicer;

public class Sequence implements Genome {

	private static final long serialVersionUID = 2759154287214849102L;

	public final int sMax = 64;
	public final float mutationRate = 0.1f;

	public int sCount;
	public int length; // In measures
	public int quant; // Timesteps per measure
	public float tempo;

	public int[] strt;
	public float[] pos;
	public float[] att;
	public float[] dec;
	public float[] sus;
	public float[] rel;
	public float[] slev;

	double mRate;
	double score;

	public Sequence(int length, int quant, float tempo) {
		this.length = length;
		this.quant = quant;
		this.tempo = tempo;

		strt = new int[sMax];
		pos = new float[sMax];
		att = new float[sMax];
		dec = new float[sMax];
		sus = new float[sMax];
		rel = new float[sMax];
		slev = new float[sMax];

		mRate = Session.mutationRate;
		score = 0;
	}

	@Override
	public Genome breed(Genome g1) {
		Sequence other = (Sequence) g1;
		Sequence child = new Sequence(length, quant, tempo);
		child.sCount = Splicer.chooseInt(this.sCount, other.sCount);
		child.strt = Splicer.spliceInt(this.strt, other.strt);
		child.pos = Splicer.spliceFloat(this.pos, other.pos, sCount);
		child.att = Splicer.spliceFloat(this.att, other.att, sCount);
		child.dec = Splicer.spliceFloat(this.dec, other.dec, sCount);
		child.sus = Splicer.spliceFloat(this.sus, other.sus, sCount);
		child.rel = Splicer.spliceFloat(this.rel, other.rel, sCount);
		child.slev = Splicer.spliceFloat(this.slev, other.slev, sCount);
		child.mutate();
		return child;
	}

	@Override
	public void randomize() {
		sCount = (int) (Math.random() * length * quant / 2) + 1;
		for (int i = 0; i < sMax; i++) {
			randomizeAt(i);
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
	
	public void addStrike(int strt, float pos, float att, float dec, float sus, float rel, float slev, float ptch) {
		int i = sCount;
		sCount++;
		this.strt[i] = strt;
		this.pos[i] = pos;
		this.att[i] = att;
		this.dec[i] = dec;
		this.sus[i] = sus;
		this.rel[i] = rel;
		this.slev[i] = slev;
	}
	
	public float getQuantLength() {
		return 240 / tempo / quant;
	}
	
	public void removeAtTime(int t) {
		for (int i = 0; i < sCount; i++) {
			if (strt[i] == t) {
				sCount--;
				strt[i] = strt[sCount];
				pos[i] = pos[sCount];
				att[i] = att[sCount];
				dec[i] = dec[sCount];
				sus[i] = sus[sCount];
				rel[i] = rel[sCount];
				slev[i] = slev[sCount];
				randomizeAt(sCount);
			}
		}
	}

	private void mutate() {
		sCount = (int) (Math.random() < mRate ? sCount + Math.signum(Math.random() - 0.5) : sCount);
		for (int i = 0; i < sMax; i++) {
			strt[i] = (int) (Math.random() < mRate ? Math.random() * length * quant : strt[i]);
			pos[i] = (float) (Math.random() < mRate ? Math.random() : pos[i]);
			att[i] = (float) (Math.random() < mRate ? Math.random() : att[i]);
			dec[i] = (float) (Math.random() < mRate ? Math.random() : dec[i]);
			sus[i] = (float) (Math.random() < mRate ? Math.random() : sus[i]);
			rel[i] = (float) (Math.random() < mRate ? Math.random() : rel[i]);
			slev[i] = (float) (Math.random() < mRate ? Math.random() : slev[i]);
		}
	}
	
	private void randomizeAt(int index) {
		strt[index] = (int) (Math.random() * length * quant);
		pos[index] = (float) Math.random();
		att[index] = (float) Math.random();
		dec[index] = (float) Math.random();
		sus[index] = (float) Math.random();
		rel[index] = (float) Math.random();
		slev[index] = (float) Math.random();
	}
}
