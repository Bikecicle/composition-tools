package evolution.core;

public class Splicer {
	
	public static float[] splice(float[] g1, float[] g2) {
		int a = (int) (Math.random() * g1.length);
		int b = (int) (Math.random() * g1.length);
		if (a > b) {
			int t = a;
			a = b;
			b = t;
		}
		float[] g3 = new float[g1.length];
		for (int i = 0; i < g1.length; i++) {
			if (i < a) {
				g3[i] = g1[i];
			} else if (i < b) {
				g3[i] = g2[i];
			} else {
				g3[i] = g1[i];
			}
		}
		return g3;
	}
	
	public static int[] splice(int[] g1, int[] g2) {
		int a = (int) (Math.random() * g1.length);
		int b = (int) (Math.random() * g1.length);
		if (a > b) {
			int t = a;
			a = b;
			b = t;
		}
		int[] g3 = new int[g1.length];
		for (int i = 0; i < g1.length; i++) {
			if (i < a) {
				g3[i] = g1[i];
			} else if (i < b) {
				g3[i] = g2[i];
			} else {
				g3[i] = g1[i];
			}
		}
		return g3;
	}

}
