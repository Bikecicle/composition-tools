package main;

import orc.Orchestra;
import sco.Score;

public class RealTimePerformer extends Performer {
	
	public static final String OUTPUT = "dac";

	public RealTimePerformer(Orchestra orc, Score sco) {
		super(orc.read(), sco.read(), OUTPUT);
	}

}
