package main;

import visual.ViewStream;
import visual.ViewTable;

public class MediaThread implements Runnable {
	
	private Medium medium;
	private String[] args;
	
	public MediaThread(Medium medium, String[] args) {
		this.medium = medium;
		this.args = args;
	}

	@Override
	public void run() {
		if (medium == Medium.performer) {
			Performer.main(args);
		} else if (medium == Medium.streamViewer) {
			ViewStream.main(args);
		} else if (medium == Medium.tableViewer) {
			ViewTable.main(args);
		}
	}

}
