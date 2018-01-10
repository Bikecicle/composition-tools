package grain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Layer implements Serializable {

	private static final long serialVersionUID = 2219783412831754585L;

	public String name;
	public double duration;
	public List<Grain> sequence;
	public List<String> sources;

	public Layer(String name, double duration) {
		this.name = name;
		this.duration = duration;
		sequence = new ArrayList<Grain>();
		sources = new ArrayList<String>();
	}

	public void addGrains(List<Grain> matrix) {
		sequence.addAll(matrix);
	}
}
