package grain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Layer implements Serializable {
	
	private static final long serialVersionUID = 2219783412831754585L;
	
	public String name;
	public List<Grain> sequence;
	
	public Layer(String name) {
		this.name = name;
		sequence = new ArrayList<Grain>();
	}

	public void addGrains(List<Grain> matrix) {
		sequence.addAll(matrix);
	}
	
	public int getFMin() {
		int fMin = Integer.MAX_VALUE;
		for (Grain g : sequence) {
			if (g.freq < fMin)
				fMin = g.freq;
		}
		return fMin;
	}
	
	public int getFMax() {
		int fMax = 0;
		for (Grain g : sequence) {
			if (g.freq > fMax)
				fMax = g.freq;
		}
		return fMax;
	}
}