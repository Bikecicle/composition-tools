package grain;

import java.io.Serializable;

public enum Instrument implements Serializable {

	osc(1, 10), sample(2, 1), warp(3, 1);

	public final int id;
	public final int genRoutine;

	private Instrument(int id, int genRoutine) {
		this.id = id;
		this.genRoutine = genRoutine;
	}
}
