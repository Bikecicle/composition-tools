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
	
	public String toScore() {
		String score = "f1 0 4096 10 1\n";
		for (Grain g : sequence) {
			score += g.statement() + "\n";
		}
		score += "e\n";
		return score;
	}
	
}
