package model;

import java.util.ArrayList;

public class Batch extends ArrayList<Rhythm> {
	
	private static final long serialVersionUID = -6672275370540635595L;

	public void rate(int index, int tRating, int sRating) {
		get(index).rateTimbre(tRating);
		get(index).rateSequence(sRating);
	}
	
	public void reset() {
		for (Rhythm r : this) {
			r.resetScore();
		}
	}
}
