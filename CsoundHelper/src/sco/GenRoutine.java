package sco;

public enum GenRoutine {

	soundfile(1), sine(10);
	
	int id;
	
	private GenRoutine(int id) {
		this.id = id;
	}
}
