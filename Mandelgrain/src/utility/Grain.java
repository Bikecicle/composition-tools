package utility;

public class Grain {

	public int instrumentId;
	public float start;
	public float duration;
	public float amplitude;
	public int frequency;
	public float attack;
	public float decay;

	public Grain(int instrumentId, float start, float duration, float amplitude, int frequency, float attack,
			float decay) {
		this.instrumentId = instrumentId;
		this.start = start;
		this.duration = duration;
		this.amplitude = amplitude;
		this.frequency = frequency;
		this.attack = attack;
		this.decay = decay;
	}

	public String statement(int resolution) {
		return "i" + instrumentId + " " + (start / 1000.0) + " " + (duration / 1000.0) + " "
				+ String.format("%." + Integer.toString((int) Math.ceil(Math.log10(resolution * 100))) + "f", amplitude) + " " + frequency + " " + (attack / 1000.0) + " "
				+ (decay / 1000.0);
	}
}
