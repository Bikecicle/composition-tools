package main;

import csnd6.csnd6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import csnd6.Csound;
import csnd6.CsoundPerformanceThread;

public class Performer {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		String orcFile = null;
		String scoFile = null;
		String out = null;
		if (args.length == 2) {
			orcFile = "config/grainplot.orc";
			scoFile = args[0];
			out = args[1];
		} else {
			File path = new File("");
			String mainDir = path.getAbsolutePath().replace("\\", "/") + "/";
			orcFile = mainDir + "debug/debug_orchestra.orc";
			scoFile = mainDir + "debug/debug_score.sco";
			out = mainDir + "debug/debug_out.wav";
		}
		
        csnd6.csoundInitialize(
                csnd6.CSOUNDINIT_NO_ATEXIT | csnd6.CSOUNDINIT_NO_SIGNAL_HANDLER);
        
        String orc = null;
        String sco = null;
        
		try {
			orc = new Scanner(new File(orcFile)).useDelimiter("\\Z").next();
			sco = new Scanner(new File(scoFile)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

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