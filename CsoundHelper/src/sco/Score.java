package sco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Score {

	private ParamMap map;
	private List<FTable> fStmt;
	private List<Note> iStmt;
	
	public Score(ParamMap map) {
		this.map = map;
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
	
	public void addNote(Note note) {
		iStmt.add(note);
	}

	public String read() {
		String s = "";
		for (FTable fTable : fStmt) {
			s += fTable.read() + "\n";
		}
		for (Note note : iStmt) {
			s += note.read(map) + "\n";
		}
		s += "e";
		return s;
	}
}
