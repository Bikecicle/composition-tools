package grain;

import java.util.List;

public class Layer {

	public List<Grain> sequence;

	public void addGrains(List<Grain> matrix) {
		sequence.addAll(matrix);
	}
	
}
