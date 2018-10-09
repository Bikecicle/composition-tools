package sco;

import java.util.ArrayList;
import java.util.List;

public class Score {

	List<FTable> fStmt;
	List<Note> iStmt;
	
	public Score() {
		fStmt = new ArrayList<>();
		iStmt = new ArrayList<>();
	}
	
	public int addFTable(FTable fTable) {
		if (!fStmt.contains(fTable)) {
			fTable.ifn = fStmt.size() + 1;
			fStmt.add(fTable);
			return fTable.ifn;
		}
		return fStmt.get(fStmt.indexOf(fTable)).ifn;
	}
	
	public void addNote(Note note) {
		iStmt.add(note);
	}

	public String toString() {
		String s = "";
		for (FTable fTable : fStmt) {
			s += fTable + "\n";
		}
		for (Note note : iStmt) {
			s += note + "\n";
		}
		s += "e";
		return s;
	}

	public void clear() {
		fStmt.clear();
		iStmt.clear();
	}
}
