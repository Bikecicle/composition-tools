package grain;

public enum Instrument {
	osc(1),
	sample(2);
	
	public final int id;
	
	private Instrument(int id) {
		this.id = id;
	}
}
