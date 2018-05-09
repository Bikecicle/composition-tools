package table;

import java.io.Serializable;
import java.util.List;

public class Table implements Serializable {

	static final long serialVersionUID = -3338690167252241696L;
	
	public String name;
	public int tRes;
	public int fRes;
	public int kMax;
	public int[][] data;
	public List<String> filters;
	
	public Table(String name, int tRes, int fRes, int kMax) {
		this.name = name;
		this.tRes = tRes;
		this.fRes = fRes;
		this.kMax = kMax;
	}

}
