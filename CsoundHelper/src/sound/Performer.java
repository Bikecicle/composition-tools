package sound;

import csnd6.Csound;
import csnd6.CsoundPerformanceThread;
import csnd6.csnd6;

public class Performer {

	public static final String RT_OUT = "dac0";

	public static void play(String orc, String sco, String out) {
		csnd6.csoundInitialize(csnd6.CSOUNDINIT_NO_ATEXIT | csnd6.CSOUNDINIT_NO_SIGNAL_HANDLER);

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

	public static void play(Performable p, String out) {
		play(p.getOrchestra().toString(), p.getScore().toString(), out);
	}

	public static void play(Performable p) {
		play(p, RT_OUT);
	}
}
