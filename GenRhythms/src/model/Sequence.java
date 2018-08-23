package model;

import evolution.core.Genome;
import evolution.core.Splicer;

public class Sequence implements Genome {

	private static final long serialVersionUID = 2759154287214849102L;

	public final int strikeMax = 64;
	public final float mutationRate = 0.1f;

	public int strikeCount;
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
	public float[] ptch;

	double mRate;
	double score;

	public Sequence(int length, int quant, float tempo) {
		this.length = length;
		this.quant = quant;
		this.tempo = tempo;

		strt = new int[strikeMax];
		pos = new float[strikeMax];
		att = new float[strikeMax];
		dec = new float[strikeMax];
		sus = new float[strikeMax];
		rel = new float[strikeMax];
		slev = new float[strikeMax];
		ptch = new float[strikeMax];

		mRate = Session.mutationRate;
		score = 0;
	}

	@Override
	public Genome breed(Genome g1) {
		Sequence other = (Sequence) g1;
		Sequence child = new Sequence(length, quant, tempo);
		child.strikeCount = Splicer.chooseInt(this.strikeCount, other.strikeCount);
		child.strt = Splicer.spliceInt(this.strt, other.strt);
		child.pos = Splicer.spliceFloat(this.pos, other.pos, strikeCount);
		child.att = Splicer.spliceFloat(this.att, other.att, strikeCount);
		child.dec = Splicer.spliceFloat(this.dec, other.dec, strikeCount);
		child.sus = Splicer.spliceFloat(this.sus, other.sus, strikeCount);
		child.rel = Splicer.spliceFloat(this.rel, other.rel, strikeCount);
		child.slev = Splicer.spliceFloat(this.slev, other.slev, strikeCount);
		child.ptch = Splicer.spliceFloat(this.ptch, other.ptch, strikeCount);
		child.mutate();
		return child;
	}

	@Override
	public void randomize() {
		strikeCount = (int) (Math.random() * length * quant / 2) + 1;
		for (int i = 0; i < strikeMax; i++) {
			strt[i] = (int) (Math.random() * length * quant);
			pos[i] = (float) Math.random();
			att[i] = (float) Math.random();
			dec[i] = (float) Math.random();
			sus[i] = (float) Math.random();
			rel[i] = (float) Math.random();
			slev[i] = (float) Math.random();
			ptch[i] = (float) Math.random();
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
		int i = strikeCount;
		strikeCount++;
		this.strt[i] = strt;
		this.pos[i] = pos;
		this.att[i] = att;
		this.dec[i] = dec;
		this.sus[i] = sus;
		this.rel[i] = rel;
		this.slev[i] = slev;
		this.ptch[i] = ptch;
	}
	
	public float getQuantLength() {
		return 240 / tempo / quant;
	}
	
	public void removeAt(int t) {
		for (int i = 0; i < strikeCount; i++) {
			if ()
		}
	}

	private void mutate() {
		strikeCount = (int) (Math.random() < mRate ? strikeCount + Math.signum(Math.random() - 0.5) : strikeCount);
		for (int i = 0; i < strikeMax; i++) {
			strt[i] = (int) (Math.random() < mRate ? Math.random() * length * quant : strt[i]);
			pos[i] = (float) (Math.random() < mRate ? Math.random() : pos[i]);
			att[i] = (float) (Math.random() < mRate ? Math.random() : att[i]);
			dec[i] = (float) (Math.random() < mRate ? Math.random() : dec[i]);
			sus[i] = (float) (Math.random() < mRate ? Math.random() : sus[i]);
			rel[i] = (float) (Math.random() < mRate ? Math.random() : rel[i]);
			slev[i] = (float) (Math.random() < mRate ? Math.random() : slev[i]);
			ptch[i] = (float) (Math.random() < mRate ? Math.random() : ptch[i]);
		}
	}
}
