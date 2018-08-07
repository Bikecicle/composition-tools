package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.FactoryConfigurationError;

import main.Rhythm;
import main.Sequence;
import main.Timbre;
import sound.Performer;

public class TestInstrument {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int voices = 1;
		String[] fs = new String[voices];
		Timbre[] ts = new Timbre[voices];
		Sequence[] ss = new Sequence[voices];

		fs[0] = "choir.wav";

		Timbre t = new Timbre();
		t.pos[0] = 0.2f;
		t.pos[1] = 0.8f;
		t.att[0] = 0.1f;
		t.att[1] = 0.2f;
		t.dec[0] = 0.1f;
		t.dec[1] = 0.2f;
		t.sus[0] = 0.1f;
		t.sus[1] = 0.2f;
		t.rel[0] = 0.1f;
		t.rel[1] = 0.2f;
		t.slev[0] = 0.1f;
		t.slev[1] = 0.2f;
		ts[0] = t;

		Sequence s = new Sequence(3, 8, 120);
		s.addStrike(0, 0, 0, 0, 0, 0, 0);
		s.addStrike(8, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f);
		s.addStrike(16, 1, 1, 1, 1, 1, 1);
		ss[0] = s;

		Rhythm r = new Rhythm(fs, ts, ss);
		String out = "output/test.wav";
		
		System.out.println(r.getOrchestra());
		System.out.println(r.getScore());
		
		Performer.play(r, out);
	
		//Performer.playFile("csd/test_orc.orc", "csd/test_sco.sco");
	}

}
