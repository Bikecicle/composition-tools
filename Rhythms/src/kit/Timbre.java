package kit;

import evolution.core.Genome;

public class Timbre implements Genome {

	private static final long serialVersionUID = 1008255419750499780L;
	
	public static final int ENVELOPE_DIM = 128;
	public static final int SPEED_DIM = 8;
	public static final float DEFAULT_DUR = 0.2f;

	String sample;
	float posMin;
	float posMax;
	float durMin;
	float durMax;
	float[] env; // Normalized amplitude
	float envDev;
	float[] speed;
	float speedDev;

	double score;

	public Timbre(String sample, int quant, float tempo) {
		this.sample = sample;
		posMin = (float) Math.random();
		posMax = posMin; // No deviation by default
		durMin = (float) (Math.random() * DEFAULT_DUR);
		durMax = durMin; // No deviation by default
		env = new float[ENVELOPE_DIM];
		for (int i = 0; i < ENVELOPE_DIM; i++) {
			env[i] = (float) Math.random();
		}
		envDev = 0;
		speed = new float[SPEED_DIM];
		for (int i = 0; i < SPEED_DIM; i++) {
			speed[i] = 1;
		}
		speedDev = 0;
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
}
