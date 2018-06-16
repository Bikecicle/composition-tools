package kit;

import evolution.core.Genome;

public class Voice implements Genome {
	
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
	
	public Voice(String sample, float pos, float dur, float att, float dec) {
		super();
		this.sample = sample;
		this.pos = pos;
		this.dur = dur;
	}
	
	@Override
	public Genome breed(Genome other) {
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
