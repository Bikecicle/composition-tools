package main;

import evolution.core.Genome;
import evolution.core.Splicer;

public class Timbre implements Genome {

	private static final long serialVersionUID = 1008255419750499780L;
	
	public static final float defaultDur = 0.1f;
	public static final float pitchMin = 0.5f;
	public static final float pitchMax = 1.5f;

	public float[] pos;
	public float[] att;
	public float[] dec;
	public float[] sus;
	public float[] rel;
	public float[] slev;
	public float[] ptch;

	double mRate;
	double score;

	public Timbre() {
		pos = new float[2];
		att = new float[2];
		dec = new float[2];
		sus = new float[2];
		rel = new float[2];
		slev = new float[2];
		ptch = new float[2];
		mRate = Session.mutationRate;
		score = 0;
	}

	@Override
	public Genome breed(Genome genome) {
		Timbre other = (Timbre) genome;
		Timbre child = new Timbre();
		child.pos = Splicer.chooseRange(this.pos, other.pos);
		child.att = Splicer.chooseRange(this.att, other.att);
		child.dec = Splicer.chooseRange(this.dec, other.dec);
		child.sus = Splicer.chooseRange(this.sus, other.sus);
		child.rel = Splicer.chooseRange(this.rel, other.rel);
		child.slev = Splicer.chooseRange(this.slev, other.slev);
		child.ptch = Splicer.chooseRange(this.ptch, other.ptch);
		child.mutate();
		return child;
	}
	
	@Override
	public void randomize() {
		Splicer.randomizeRange(pos, 0, 1);
		Splicer.randomizeRange(att, 0, defaultDur);
		Splicer.randomizeRange(dec, 0, defaultDur);
		Splicer.randomizeRange(sus, 0, defaultDur);
		Splicer.randomizeRange(rel, 0, defaultDur);
		Splicer.randomizeRange(slev, 0, 1);
		Splicer.randomizeRange(ptch, pitchMin, pitchMax);
		score = 0;
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
		Splicer.mutateRange(pos, 0, 1, Session.mutationRate);
		Splicer.mutateRange(att, 2, Session.mutationRate);
		Splicer.mutateRange(dec, 2, Session.mutationRate);
		Splicer.mutateRange(sus, 2, Session.mutationRate);
		Splicer.mutateRange(rel, 2, Session.mutationRate);
		Splicer.mutateRange(slev, 0, 1, Session.mutationRate);
		Splicer.mutateRange(ptch, pitchMin, pitchMax, Session.mutationRate);
	}
}
