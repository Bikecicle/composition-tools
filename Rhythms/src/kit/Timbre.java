package kit;

import evolution.core.Genome;

public class Timbre implements Genome {
	
	private static final long serialVersionUID = 1008255419750499780L;
	
	String sample;
	float pos;
	float posDev;
	float dur;
	float durDev;
	int envDim;
	float[] env;
	float envDev;
	int speedDim;
	float[] speed;
	float speedDev;
	
	double score;
	
	
	
	public Timbre(String sample, float pos, float posDev, float dur, float durDev, int envDim, float[] env,
			float envDev, int speedDim, float[] speed, float speedDev) {
		super();
		this.sample = sample;
		this.pos = pos;
		this.posDev = posDev;
		this.dur = dur;
		this.durDev = durDev;
		this.envDim = envDim;
		this.env = env;
		this.envDev = envDev;
		this.speedDim = speedDim;
		this.speed = speed;
		this.speedDev = speedDev;
	}
	
	public Timbre(String sample) {
		this.sample = sample;
		pos = (float) Math.random();
		posDev = (float) Math.random();
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
