package main;

import java.io.File;
import java.util.List;

import grain.GrainManager;
import table.EdgeDetection;
import table.Filter;
import table.Table;
import table.TableManager;

public class FractalSynth {
	
	private GrainManager grainManager;
	private TableManager tableManager;
	private String mainDir;
	private String projectDir;
	private String tableDir;
	
	
	public FractalSynth(String project) {
		File path = new File("");
		mainDir = path.getAbsolutePath().replace("\\", "/") + "/";
		projectDir = mainDir + "projects/";
		tableDir = mainDir + "tables/";
		openDir(projectDir);
		openDir(tableDir);
		grainManager = new GrainManager(projectDir, project);
		tableManager = new TableManager(tableDir);
	}
	
	public String getProject() {
		return grainManager.getProject();
	}

	public List<String> getTableNames() {
		return tableManager.getTableList();
	}

	public void deleteTable(String name) {
		tableManager.deleteTable(name);
	}

	public void filterTable(String name, Filter filter) {
		tableManager.filter(name, filter);
	}

	public void addTable(String name, int tRes, int fRes, double zoomVel, int zoomMax, int kMax, double posX, double posY) {
		tableManager.generateTable(name, tRes, fRes, zoomVel, zoomMax, kMax, posX, posY);
	}
	
	public String getTableProperties(String name) {
		return tableManager.getTable(name).toString();
	}
	
	public static boolean openDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
			return true;
		}
		return false;
	}
}
