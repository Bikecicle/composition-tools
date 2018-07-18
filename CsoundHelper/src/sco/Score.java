package sco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Score {

	private List<HashMap<Integer, String>> paramMap;
	private List<FTable> fStmt;
	private List<Note> iStmt;
	
	public Score(List<HashMap<Integer, String>> paramMap) {
		this.paramMap = paramMap;
		fStmt = new ArrayList<>();
		iStmt = new ArrayList<>();
	}
	
	public int addFTable(FTable fTable) {
		if (!fStmt.contains(fTable)) {
			fTable.ifn = fStmt.size() + 1;
			fStmt.add(fTable);
			return fTable.ifn;
		}
		return -1;
	}

	public String read() {
		String s = "";
		for (FTable fTable : fStmt) {
			s += fTable.read() + "\n";
		}
		for (Note note : iStmt) {
			s += note.read(paramMap) + "\n";
		}
		s += "e";
		return s;
	}
}
