package main;

public class Strike {
	
	float strt;
	
	float dur;
	float pos;
	float[] env;
	float[] speed;
	
	public Strike(float strt) {
		this.strt = strt;
		env = new float[Timbre.ENVELOPE_DIM];
		speed = new float[Timbre.SPEED_DIM];
	}
	
	public void randomize() {
		dur = (float) Math.random();
		pos = (float) Math.random();
		for (int i = 0; i < Timbre.ENVELOPE_DIM; i ++)
			env[i] = (float) Math.random();
		for (int i = 0; i < Timbre.SPEED_DIM; i++)
			speed[i] = (float) Math.random();
	}
}
