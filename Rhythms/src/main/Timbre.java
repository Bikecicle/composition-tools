package main;

import evolution.core.Genome;
import evolution.core.Splicer;

public class Timbre implements Genome {

	private static final long serialVersionUID = 1008255419750499780L;
	
	public static final int ENVELOPE_DIM = 64;
	public static final int SPEED_DIM = 8;
	
	public static final float DEFAULT_DUR = 0.2f;
	public static final float MIN_PTCH = 0.5f;
	public static final float MAX_PTCH = 1.5f;

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
		mRate = Session.MUTATION_RATE;
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
		Splicer.randomizeRange(att, 0, DEFAULT_DUR);
		Splicer.randomizeRange(dec, 0, DEFAULT_DUR);
		Splicer.randomizeRange(sus, 0, DEFAULT_DUR);
		Splicer.randomizeRange(rel, 0, DEFAULT_DUR);
		Splicer.randomizeRange(slev, 0, 1);
		Splicer.randomizeRange(ptch, MIN_PTCH, MAX_PTCH);
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
		Splicer.mutateRange(pos, 0, 1, Session.MUTATION_RATE);
		Splicer.mutateRange(att, 2, Session.MUTATION_RATE);
		Splicer.mutateRange(dec, 2, Session.MUTATION_RATE);
		Splicer.mutateRange(sus, 2, Session.MUTATION_RATE);
		Splicer.mutateRange(rel, 2, Session.MUTATION_RATE);
		Splicer.mutateRange(slev, 0, 1, Session.MUTATION_RATE);
		Splicer.mutateRange(ptch, MIN_PTCH, MAX_PTCH, Session.MUTATION_RATE);
	}
}
