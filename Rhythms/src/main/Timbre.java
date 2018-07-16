package main;

import evolution.core.Genome;
import evolution.core.Splicer;

public class Timbre implements Genome {

	private static final long serialVersionUID = 1008255419750499780L;
	
	public static final int ENVELOPE_DIM = 64;
	public static final int SPEED_DIM = 8;
	public static final float DEFAULT_DUR = 0.2f;

	String sample;
	float posMin;
	float posMax;
	float durMin;
	float durMax;
	float[] envMin;
	float[] envMax;
	float[] speedMin;
	float[] speedMax;

	double mRate;
	double score;

	public Timbre(String sample) {
		this.sample = sample;
		mRate = Session.MUTATION_RATE;
		score = 0;
	}

	@Override
	public Genome breed(Genome genome) {
		Timbre other = (Timbre) genome;
		Timbre child = new Timbre(sample);
		double s = 0.5;
		child.posMin = Math.random() > s ? this.posMin : other.posMin;
		child.posMax = Math.random() > s ? this.posMax : other.posMax;
		child.durMin = Math.random() > s ? this.durMin : other.durMin;
		child.durMax = Math.random() > s ? this.durMax : other.durMax;
		child.envMin = Splicer.splice(this.envMin, other.envMin);
		child.envMax = Splicer.splice(this.envMax, other.envMax);
		child.speedMin = Splicer.splice(this.speedMin, other.speedMin);
		child.speedMax = Splicer.splice(this.speedMax, other.speedMax);
		child.mutate();
		return child;
	}
	
	@Override
	public void randomize() {
		posMin = (float) Math.random();
		posMax = posMin; // No deviation by default
		durMin = (float) (Math.random() * DEFAULT_DUR);
		durMax = durMin; // No deviation by default
		envMin = new float[ENVELOPE_DIM];
		envMax = new float[ENVELOPE_DIM];
		for (int i = 0; i < ENVELOPE_DIM; i++) {
			envMin[i] = (float) Math.random();
			envMax[i] = (float) Math.random();
		}
		speedMin = new float[SPEED_DIM];
		speedMax = new float[SPEED_DIM];
		for (int i = 0; i < SPEED_DIM; i++) {
			speedMin[i] = 1;
			speedMax[i] = 1;
		}
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
		posMin = (float) (Math.random() > mRate ? posMin : Math.random());
		posMax = (float) (Math.random() > mRate ? posMax : Math.random());
		durMin = (float) (Math.random() > mRate ? durMin : Math.random());
		durMax = (float) (Math.random() > mRate ? durMax : Math.random());
		for (int i = 0; i < ENVELOPE_DIM; i++) {
			envMin[i] = (float) (Math.random() > mRate ? envMin[i] : Math.random());
			envMax[i] = (float) (Math.random() > mRate ? envMax[i] : Math.random());
		}
		for (int i = 0; i < SPEED_DIM; i++) {
			speedMin[i] = (float) (Math.random() > mRate ? speedMin[i] : Math.random());
			speedMax[i] = (float) (Math.random() > mRate ? speedMax[i] : Math.random());
		}
	}
	
	public float truePos(float c) {
		return c * (posMax - posMin) + posMin;
	}
	
	public float trueDur(float c) {
		return c * (durMax - durMin) + durMin;
	}
	
	public Float[] trueEnv(float c) {
		Float[] env = new Float[ENVELOPE_DIM];
		for (int i = 0; i < ENVELOPE_DIM; i++)
			env[i] = c * (envMax[i] - envMin[i]) + envMin[i];
		return env;
	}
	
	public float[] trueSpeed(float c) {
		float[] speed = new float[SPEED_DIM];
		for (int i = 0; i < SPEED_DIM; i++)
			speed[i] = c * (speedMax[i] - speedMin[i]) + speedMin[i];
		return speed;
	}
}
