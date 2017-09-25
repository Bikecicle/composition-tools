package main;

import java.io.File;
import java.util.List;

import grain.GrainManager;
import grain.Modulator;
import table.EdgeDetection;
import table.Filter;
import table.Table;
import table.TableManager;

public class FractalSynth {

	private GrainManager grainManager;
	private TableManager tableManager;
	private String mainDir;
	private String mainProjectDir;
	private String mainTableDir;

	public FractalSynth(String project) {
		File path = new File("");
		mainDir = path.getAbsolutePath().replace("\\", "/") + "/";
		mainProjectDir = mainDir + "projects/";
		mainTableDir = mainDir + "tables/";
		openDir(mainProjectDir);
		openDir(mainTableDir);
		grainManager = new GrainManager(mainProjectDir, project);
		tableManager = new TableManager(mainTableDir);
	}

	public String getProject() {
		return grainManager.getProject();
	}

	public List<String> getTableNames() {
		return tableManager.getTableList();
	}
	
	public Table getTable(String name) {
		return tableManager.getTable(name);
	}

	public void deleteTable(String name) {
		tableManager.deleteTable(name);
	}

	public void filterTable(String name, Filter filter) {
		tableManager.filter(name, filter);
	}

	public void addTable(String name, int tRes, int fRes, double zoomVel, int zoomMax, int kMax, double posX,
			double posY) {
		tableManager.generateTable(name, tRes, fRes, zoomVel, zoomMax, kMax, posX, posY);
	}
	
	public void addTable(String name, String other) {
		tableManager.generateTable(name, tableManager.getTable(other));
	}

	public String getTableProperties(String name) {
		return tableManager.getTable(name).toString();
	}

	public void genPulsarMatrix(int fMinP, int fMaxP, int fResP, int fMinD, int fMaxD, int minResD, int maxResD, double zoomVel,
			int zoomMax, String tablePName, String tableDName) {
		Table tableP = tableManager.getTable(tablePName);
		Table tableD = tableManager.getTable(tableDName);
		int grains = grainManager.genPulsarMatrix(fMinP, fMaxP, fResP, fMinD, fMaxD, minResD, maxResD, zoomVel, zoomMax, tableP,
				tableD);
		System.out.println(grains + "grains created");
	}

	public static boolean openDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
			return true;
		}
		return false;
	}

	public String getActiveLayerName() {
		return grainManager.getActiveLayer().name;
	}

	public void saveLayers() {
		grainManager.save();
	}

	public void visualizeLayers() {
		grainManager.visualizeLayers();
	}

	public void renderLayer(String name, String filename) {
		grainManager.save();
		System.out.println("Rendering " + name + "...");
		grainManager.render(name, filename);
	}

	public void clearLayer() {
		grainManager.clear();
	}

	public int applyMod(Modulator mod) {
		return grainManager.applyMod(mod);
	}

	public int getActiveLayerFMax() {
		return grainManager.getActiveLayer().getFMax();
	}

	public int getActiveLayerFMin() {
		return grainManager.getActiveLayer().getFMin();
	}
}
