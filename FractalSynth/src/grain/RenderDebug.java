package grain;

/**
 * Example 4 - Using Csound's Performance Thread 
 * Author: Steven Yi <stevenyi@gmail.com>
 * 2013.10.28
 *
 * In this example, we use a CsoundPerformanceThread to run Csound in 
 * a native thread.  Using a native thread is important to get the best
 * runtime performance for the audio engine.  It is especially important
 * for languages such as Python that do not have true native threads
 * and that use a Global Interpreter Lock. CsoundPerformanceThread has
 * some convenient methods for handling events, but does not have
 * features for doing regular processing at block boundaries.  In general,
 * use CsoundPerformanceThread when the only kinds of communication you
 * are doing with Csound are through events, and not using channels.
 *
 * @author stevenyi
 */

import csnd6.csnd6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import csnd6.Csound;
import csnd6.CsoundPerformanceThread;

public class RenderDebug {

    @SuppressWarnings("resource")
	public static void main(String[] args) {
        csnd6.csoundInitialize(
                csnd6.CSOUNDINIT_NO_ATEXIT | csnd6.CSOUNDINIT_NO_SIGNAL_HANDLER);
        
        String output = "debug/debug_output.wav";
        String sco = null;
        String orc = null;
		try {
			sco = new Scanner(new File("debug/debug_score.sco")).useDelimiter("\\Z").next();
			orc = new Scanner(new File("debug/debug_orchestra.orc")).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        Csound c = new Csound();
        c.SetOption("-o" + output);
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