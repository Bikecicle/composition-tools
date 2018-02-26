package grain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Layer implements Serializable {

	private static final long serialVersionUID = 2219783412831754585L;

	public String name;
	public double duration;
	public List<Grain> sequence;
	public List<FTable> fTables;

	public Layer(String name, double duration) {
		this.name = name;
		this.duration = duration;
		sequence = new ArrayList<Grain>();
		fTables = new ArrayList<FTable>();
	}

	public void addGrains(List<Grain> matrix) {
		sequence.addAll(matrix);
	}

	public int addFTable(FTable ft) {
		ft.ifn = fTables.size() + 1;
		fTables.add(ft);
		return ft.ifn;
	}

	public void clear() {
		sequence.clear();
		fTables.clear();
	}
}
