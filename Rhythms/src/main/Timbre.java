package main;

import evolution.core.Genome;

public class Timbre implements Genome {

	private static final long serialVersionUID = 1008255419750499780L;
	
	public static final int ENVELOPE_DIM = 128;
	public static final int SPEED_DIM = 8;
	public static final float DEFAULT_DUR = 0.2f;
	public static final float MUTATION_RATE = 0.1f;

	String sample;
	float posMin;
	float posMax;
	float durMin;
	float durMax;
	float[] envMin;
	float[] envMax;
	float[] speedMin;
	float[] speedMax;

	double score;

	public Timbre(String sample) {
		this.sample = sample;
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
		child.envMin = Sequence.splice(this.envMin, other.envMin);
		child.envMax = Sequence.splice(this.envMax, other.envMax);
		child.speedMin = Sequence.splice(this.speedMin, other.speedMin);
		child.speedMax = Sequence.splice(this.speedMax, other.speedMax);
		child.mutate();
		return child;
	}

	@Override
	public double getScore() {
		return score;
	}

	@Override
	public int getId() {
		return hashCode();
	}
	
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
	
	public void mutate() {
		posMin = (float) (Math.random() > MUTATION_RATE ? posMin : Math.random());
		posMax = (float) (Math.random() > MUTATION_RATE ? posMax : Math.random());
		durMin = (float) (Math.random() > MUTATION_RATE ? durMin : Math.random());
		durMax = (float) (Math.random() > MUTATION_RATE ? durMax : Math.random());
		for (int i = 0; i < ENVELOPE_DIM; i++) {
			envMin[i] = (float) (Math.random() > MUTATION_RATE ? envMin[i] : Math.random());
			envMax[i] = (float) (Math.random() > MUTATION_RATE ? envMax[i] : Math.random());
		}
		for (int i = 0; i < SPEED_DIM; i++) {
			speedMin[i] = (float) (Math.random() > MUTATION_RATE ? speedMin[i] : Math.random());
			speedMax[i] = (float) (Math.random() > MUTATION_RATE ? speedMax[i] : Math.random());
		}
	}
}
