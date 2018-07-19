package sound;

import csnd6.Csound;
import csnd6.CsoundPerformanceThread;
import csnd6.csnd6;

public class Performer {

	public static final String RT_OUT = "bob.wav";

	public void play(String orc, String sco, String out) {
		//System.out.println(orc);
		//System.out.println(sco);
		//System.out.println(out);
		
		csnd6.csoundInitialize(csnd6.CSOUNDINIT_NO_ATEXIT | csnd6.CSOUNDINIT_NO_SIGNAL_HANDLER);

		Csound c = new Csound();
		c.SetOption("-o" + out);
		c.CompileOrc(orc);
		c.ReadScore(sco);
		c.Start();

		CsoundPerformanceThread t = new CsoundPerformanceThread(c);
		t.Play();
		t.Join();

		c.Stop();
		c.Cleanup();
	}

	public void play(Performable p, String out) {
		play(p.getOrchestra().read(), p.getScore().read(), out);
	}
	
	public void play(Performable p) {
		play(p, RT_OUT);
	}
}
