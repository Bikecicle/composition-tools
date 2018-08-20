package view;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import model.Session;

public class Filters {
	
	public static FileFilter sessionFilter() {
		return new FileFilter() {
			
			@Override
			public String getDescription() {
				return "GenRhythm sessions (" + Session.SESSION_EXT + ")";
			}

			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(Session.SESSION_EXT))
					return true;
				return false;
			}
		};
		
	}

	public static FileFilter setFilter() {
		return new FileFilter() {
			
			@Override
			public String getDescription() {
				return "GenRhythm sets (" + Session.SET_EXT + ")";
			}
			
			@Override
			public boolean accept(File f) {
				if (f.getName().endsWith(Session.SET_EXT))
					return true;
				return false;
			}
		};
	}

}
