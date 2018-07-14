package main;

import java.util.List;
import java.util.Scanner;

import csnd6.Csound;
import csnd6.CsoundPerformanceThread;
import csnd6.csnd6;

public class Generator {
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		System.out.println("Voice count: ");
		int voiceCount = in.nextInt();
		System.out.println("Length (measures): ");
		int length = in.nextInt();
		System.out.println("Quantization (measure division): ");
		int quant = in.nextInt();
		System.out.println("Tempo (bpm): ");
		float tempo = in.nextFloat();
		
		Session session = new Session(voiceCount, length, quant, tempo);
		
		while (true) {
			List<Rhythm> batch = session.createBatch();
			for (Rhythm rhythm : batch) {
				
			}
			break;
		}
		
		in.close();
	}
	
	private void perform(String sco) {;
		csnd6.csoundInitialize(csnd6.CSOUNDINIT_NO_ATEXIT | csnd6.CSOUNDINIT_NO_SIGNAL_HANDLER);
		
		Scanner so = new Scanner("config/rhythm.orc");
        String orc = so.useDelimiter("\\Z").next(); 
        so.close();

        Csound c = new Csound();
        c.SetOption("-odac");
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
