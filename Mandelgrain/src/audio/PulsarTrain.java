package audio;

import java.util.List;


public class PulsarTrain {
	
	public int[] fp; // Fundamental freq env (1-5000 Hz)
	public int[] fd; // Formant freq env (80-10000 Hz)
	public int[][] v; // Pulsaret env
	public int[] a; // Train env
	public int[] d; // Spatial path
	public int t; // Start time
	
	public List<Grain> p; // Pulsarets
	
	public PulsarTrain(int[] fp, int[] fd, int[][] v, int[] a, int[] d, int t) {
		this.fp = fp;
		this.fd = fd;
		this.v = v;
		this.a = a;
		this.d = d;
		this.t = t;
		genP();
	}
	
	public void genP() {
		int dur = a.length;
		for (int i = 0; i < dur; i++) {
			
		}
	}
	
	public String statement() {
		
		return null;
	}
}
