package sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import csnd6.Csound;
import csnd6.csnd6;
import orc.Orchestra;

public class Performer {

	public static final String RT_OUT = "dac0";

	String out;
	PrintWriter trace;
	int iter;

	public Performer(String out) {
		csnd6.csoundInitialize(csnd6.CSOUNDINIT_NO_ATEXIT | csnd6.CSOUNDINIT_NO_SIGNAL_HANDLER);
		this.out = out;
		try {
			trace = new PrintWriter(new File("trace.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		iter = 0;
	}

	public Performer() {
		this(RT_OUT);
	}

	public void play(String orc, String sco) {
		trace.println("[Performance " + iter + "]");
		trace.println();
		trace.println(orc);
		trace.println();
		trace.println(sco);
		trace.println();
		trace.flush();
		iter++;
		
		Csound c = new Csound();
		c.SetOption("-o" + out);
		c.CompileOrc(orc);
		c.ReadScore(sco);
		c.Start();

		while (c.PerformKsmps() == 0) {
		}

		c.Stop();
		c.Cleanup();
	}

	public void play(Performance performance) {
		play(performance.getOrchestra().toString(), performance.getScore().toString());
	}

	@SuppressWarnings("resource")
	public void playFile(String orcPath, String scoPath) {
		String orc = null;
		String sco = null;

		try {
			orc = new Scanner(new File(orcPath)).useDelimiter("\\Z").next();
			sco = new Scanner(new File(scoPath)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		play(orc, sco);
	}
	
	public static void debugOrc(String orc) {
		Csound c = new Csound();
		c.CompileOrc(orc);
	}
}
