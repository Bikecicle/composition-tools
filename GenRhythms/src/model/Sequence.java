package model;

import evolution.core.Genome;
import evolution.core.Splicer;

public class Sequence implements Genome {

	private static final long serialVersionUID = 2759154287214849102L;

	public final int hMax = 128;
	public final float mutationRate = 0.1f;

	public int hCount;
	public int length; // In measures
	public int quant; // Timesteps per measure
	public float tempo;

	public Hit[] hits;

	double mRate;
	double score;

	public Sequence(int length, int quant, float tempo) {
		this.length = length;
		this.quant = quant;
		this.tempo = tempo;
		hits = new Hit[hMax];
		for (int i = 0; i < hMax; i++) {
			hits[i] = new Hit();
		}

		mRate = Session.mutationRate;
		score = 0;
	}

	@Override
	public Genome breed(Genome g1) {
		Sequence other = (Sequence) g1;
		Sequence child = new Sequence(length, quant, tempo);
		child.hCount = Math.random() < Splicer.WEIGHT ? this.hCount : other.hCount;
		for (int i = 0; i < hits.length; i++) {
			child.hits[i] = Hit.splice(this.hits[i], other.hits[i]);
		}
		child.mutate();
		return child;
	}

	@Override
	public void randomize() {
		hCount = (int) (Math.random() * length * quant / 4) + 1;
		for (int i = 0; i < hMax; i++) {
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
	
	public void addHit(Hit hit) {
		hits[hCount] = hit;
		hCount++;
	}

	public boolean hasAtTime(int t) {
		for (int i = 0; i < hCount; i++) {
			if (hits[i].strt == t)
				return true;
		}
		return false;
	}

	public Hit getAtTime(int t) {
		for (int i = 0; i < hCount; i++) {
			if (hits[i].strt == t)
				return hits[i];
		}
		return null;
	}

	public void addAtTime(int t) {
		hits[hCount].strt = t;
		hits[hCount].randomize();
		hCount++;
	}

	public void removeAtTime(int t) {
		for (int i = 0; i < hCount; i++) {
			if (hits[i].strt == t) {
				hCount--;
				hits[i] = hits[hCount];
				hits[hCount] = new Hit();
				randomizeAt(hCount);
			}
		}
	}
	
	public float getQuantLen() {
		return 240 / tempo / quant;
	}

	private void mutate() {
		hCount = (int) (Math.random() < mRate ? hCount + Math.signum(Math.random() - 0.5) : hCount);
		for (int i = 0; i < hMax; i++) {
			hits[i].strt = (int) (Math.random() < mRate ? Math.random() * length * quant : hits[i].strt);
			hits[i].mutate(mRate);
		}
	}

	private void randomizeAt(int index) {
		hits[index].strt = (int) (Math.random() * length * quant);
		hits[index].randomize();
	}
}
