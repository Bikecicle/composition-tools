package test;

import model.Hit;
import model.Rhythm;
import model.Sequence;
import model.Timbre;
import sound.Performer;

public class TestInstrument {

	public static void main(String[] args) {
		int voices = 1;
		Timbre[] ts = new Timbre[voices];
		Sequence[] ss = new Sequence[voices];

		Timbre t = new Timbre("C:/Users/Griffin/Documents/Ableton/User Library/Samples/tools", 1, 1);
		t.pos[0] = 0f;
		t.pos[1] = 1f;
		t.att[0] = 0.00000001f;
		t.att[1] = 0.00000001f;
		t.dec[0] = 0.001f;
		t.dec[1] = 0.001f;
		t.sus[0] = 1f;
		t.sus[1] = 1f;
		t.rel[0] = 0f;
		t.rel[1] = 0f;
		t.slev[0] = 1f;
		t.slev[1] = 1f;
		ts[0] = t;

		Sequence s = new Sequence(3, 8, 120);
		s.addHit(new Hit(0, 0, 0, 0, 0, 0, 0));
		s.addHit(new Hit(8, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
		s.addHit(new Hit(16, 1, 1, 1, 1, 1, 1));
		ss[0] = s;

		Rhythm r = new Rhythm(ts, ss);
		System.out.println(r.getOrchestra());
		System.out.println(r.getScore());

		while (true) {
			Performer p = new Performer();
			p.play(r);
		}

		// Performer.playFile("csd/test_orc.orc", "csd/test_sco.sco");
	}

}
