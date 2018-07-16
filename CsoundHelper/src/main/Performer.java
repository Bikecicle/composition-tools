package main;

import csnd6.Csound;
import csnd6.CsoundPerformanceThread;
import csnd6.csnd6;

public class Performer implements Runnable {

	private String orc;
	private String sco;
	private String out;

	public Performer(String orc, String sco, String out) {
		this.orc = orc;
		this.sco = sco;
		this.out = out;
	}

	@Override
	public void run() {
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
}
