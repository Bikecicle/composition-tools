package main;

import evolution.core.Genome;
import evolution.core.Splicer;

public class Sequence implements Genome {

	private static final long serialVersionUID = 2759154287214849102L;

	public final int STRIKE_MAX = 64;
	public final float MUTATION_RATE = 0.1f;

	String sample;
	int strikeCount;
	int length; // In measures
	int quant; // Timesteps per measure

	int[] strt;
	float[] dur;
	float[] pos;
	float[] env;
	float[] speed;

	double mRate;
	double score;

	public Sequence(String sample, int length, int quant) {
		this.sample = sample;
		this.length = length;
		this.quant = quant;

		strt = new int[STRIKE_MAX];
		dur = new float[STRIKE_MAX];
		pos = new float[STRIKE_MAX];
		env = new float[STRIKE_MAX];
		speed = new float[STRIKE_MAX];
		
		mRate = Session.MUTATION_RATE;
		score = 0;
	}

	@Override
	public Genome breed(Genome g1) {
		Sequence other = (Sequence) g1;
		Sequence child = new Sequence(sample, length, quant);
		child.strt = Splicer.splice(this.strt, other.strt);
		child.dur = Splicer.splice(this.dur, other.dur);
		child.pos = Splicer.splice(this.pos, other.pos);
		child.env = Splicer.splice(this.env, other.env);
		child.speed = Splicer.splice(this.speed, other.speed);
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
			dur[i] = (float) Math.random();
			pos[i] = (float) Math.random();
			env[i] = (float) Math.random();
			speed[i] = (float) Math.random();
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
			strt[i] = (int) (Math.random() > mRate ? strt[i] : Math.random() * length * quant);
			dur[i] = (float) (Math.random() > mRate ? dur[i] : Math.random());
			pos[i] = (float) (Math.random() > mRate ? pos[i] : Math.random());
			env[i] = (float) (Math.random() > mRate ? env[i] : Math.random());
			speed[i] = (float) (Math.random() > mRate ? speed[i] : Math.random());
		}
	}
}
