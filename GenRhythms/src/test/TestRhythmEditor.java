package test;

import view.RhythmEditor;
import model.Hit;
import model.Rhythm;
import model.Sequence;
import model.Timbre;

public class TestRhythmEditor {

	public static void main(String[] args) {
		
		int voices = 3;
		Timbre[] ts = new Timbre[voices];
		Sequence[] ss = new Sequence[voices];

		Timbre t1 = new Timbre(null, 1, 1);
		t1.sample = "C:/Users/Griffin/Documents/Ableton/User Library/Samples/tools/340619__mickyman5000__drill-sharp-stops-1.wav";
		t1.pos[0] = 0f;
		t1.pos[1] = 1f;
		t1.att[0] = 0.00000001f;
		t1.att[1] = 0.00000001f;
		t1.dec[0] = 0.001f;
		t1.dec[1] = 0.001f;
		t1.sus[0] = 1f;
		t1.sus[1] = 1f;
		t1.rel[0] = 0f;
		t1.rel[1] = 0f;
		t1.slev[0] = 1f;
		t1.slev[1] = 1f;
		ts[0] = t1;
		
		Timbre t2 = new Timbre(null, 1, 1);
		t2.sample = "C:/Users/Griffin/Documents/Ableton/User Library/Samples/tools/340619__mickyman5000__drill-sharp-stops-1.wav";
		t2.pos[0] = 0.25f;
		t2.pos[1] = 0.75f;
		t2.att[0] = 0.1f;
		t2.att[1] = 0.2f;
		t2.dec[0] = 0.001f;
		t2.dec[1] = 0.001f;
		t2.sus[0] = 1f;
		t2.sus[1] = 1f;
		t2.rel[0] = 0f;
		t2.rel[1] = 0f;
		t2.slev[0] = 1f;
		t2.slev[1] = 1f;
		ts[1] = t2;
		ts[2] = t2;

		Sequence s1 = new Sequence(3, 8, 120);
		s1.addHit(new Hit(0, 0, 0, 0, 0, 0, 0));
		s1.addHit(new Hit(8, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
		s1.addHit(new Hit(16, 1, 1, 1, 1, 1, 1));
		ss[0] = s1;
		
		Sequence s2 = new Sequence(3, 8, 120);
		s2.addHit(new Hit(2, 0, 0, 0, 0, 0, 0));
		s2.addHit(new Hit(3, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
		s2.addHit(new Hit(15, 1, 1, 1, 1, 1, 1));
		ss[1] = s2;
		
		ss[2] = new Sequence(3, 8, 120);

		Rhythm r = new Rhythm(ts, ss);
		RhythmEditor window = new RhythmEditor(r);
		window.setVisible(true);
	}
}
