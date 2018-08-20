package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import grain.FTable;
import grain.GrainManager;
import grain.gen.Generator;
import grain.gen.PulsarMatrix;
import grain.mod.Modifier;
import table.Filter;
import table.FractalTable;
import table.Table;
import table.TableManager;

public class FractalSynth {

	private GrainManager grainManager;
	private TableManager tableManager;
	private String mainDir;
	private String mainProjectDir;
	private String mainTableDir;

	public FractalSynth() {
		File path = new File("");
		mainDir = path.getAbsolutePath().replace("\\", "/") + "/";
		mainProjectDir = mainDir + "projects/";
		mainTableDir = mainDir + "tables/";
		openDir(mainProjectDir);
		openDir(mainTableDir);
		grainManager = null;
		tableManager = new TableManager(mainTableDir);
	}

	public void openProject(String project) {
		grainManager = new GrainManager(mainProjectDir, project);
	}

	public boolean projectLoaded() {
		return grainManager != null;
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

	public boolean hasTable(String name) {
		return tableManager.getTableList().contains(name);
	}

	public void deleteTable(String name) {
		tableManager.deleteTable(name);
	}

	public void filterTable(String name, Filter filter) {
		tableManager.filter(name, filter);
	}

	public void generateFractalTable(String name, int tRes, int fRes, double zoomVel, int zoomMax, int kMax,
			double posX, double posY) {
		Table table = new FractalTable(name, tRes, fRes, zoomVel, zoomMax, kMax, posX, posY);
		double scale = 1.0;
		double angleStep = 2.0 * Math.PI / fRes;
		for (int i = 0; i < table.data.length; i++) {
			for (int j = 0; j < fRes; j++) {
				double angle = angleStep * j;
				double x0 = Math.cos(angle) * 2.0 / scale + posX;
				double y0 = Math.sin(angle) * 2.0 / scale + posY;
				int k = 0;
				double x = 0.0;
				double y = 0.0;
				while ((x * x + y * y) < 4 && k < kMax) {
					double xtemp = x * x - y * y + x0;
					y = 2 * x * y + y0;
					x = xtemp;
					k++;
				}
				table.data[i][j] = k;
			}
			scale *= zoomVel / tRes + 1;
			double progress = Math.round(10000.0 * i / table.data.length) / 100.0;
			System.out.println("Progress: " + progress + "%");
		}
		tableManager.saveTable(table);
	}

	public void generateFractalTable(String name, String otherName) {
		FractalTable other = (FractalTable) tableManager.getTable(otherName);
		int tRes = other.tRes;
		int fRes = other.fRes;
		double zoomVel = other.zoomVel;
		int zoomMax = other.zoomMax;
		int kMax = other.kMax;
		double posX = other.posX;
		double posY = other.posY;
		generateFractalTable(name, tRes, fRes, zoomVel, zoomMax, kMax, posX, posY);
	}

	public void generateImageTable(String name, String imagePath) {
		int kMax = 765;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int[][] red = new int[img.getWidth()][img.getHeight()];
		int[][] green = new int[img.getWidth()][img.getHeight()];
		int[][] blue = new int[img.getWidth()][img.getHeight()];
		int[][] sum = new int[img.getWidth()][img.getHeight()];
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int rgb = img.getRGB(x, img.getHeight() - y - 1);
				red[x][y] = (rgb >> 16) & 0xFF;
				green[x][y] = (rgb >> 8) & 0xFF;
				blue[x][y] = rgb & 0xFF;
				sum[x][y] = red[x][y] + green[x][y] + blue[x][y];
			}
		}
		try {
		tableManager.saveTable(new Table(name + "_r", red, kMax));
		Thread.sleep(1000);
		tableManager.saveTable(new Table(name + "_g", green, kMax));
		Thread.sleep(1000);
		tableManager.saveTable(new Table(name + "_b", blue, kMax));
		Thread.sleep(1000);
		tableManager.saveTable(new Table(name, sum, kMax));
		Thread.sleep(1000);
		} catch (Exception e) {
			
		}
	}

	public String getTableProperties(String name) {
		return tableManager.getTable(name).toString();
	}

	public int genPulsarMatrix(int fMinP, int fMaxP, int fResP, int fMinD, int fMaxD, int minResD, int maxResD, float dur, String tablePName, String tableDName) {
		Table tableP = tableManager.getTable(tablePName);
		Table tableD = tableManager.getTable(tableDName);
		return grainManager.generateGrains(new PulsarMatrix(fMinP, fMaxP, fResP, fMinD, fMaxD, minResD, maxResD, dur, tableP, tableD));
	}

	public int generateGrains(Generator gen) {
		return grainManager.generateGrains(gen);
	}

	public static boolean openDir(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
			return true;
		}
		return false;
	}

	public static void deleteFile(String path) {
		File dir = new File(path);
		dir.delete();
	}

	public String getActiveLayerName() {
		return grainManager.active.name;
	}

	public void saveLayers() {
		grainManager.save();
	}

	public void visualizeLayers() {
		grainManager.visualizeLayers();
	}

	public void renderAll(String title) {
		grainManager.renderAll(title);
	}

	public void clearLayer() {
		grainManager.clear();
	}

	public int applyMod(Modifier mod) {
		return grainManager.applyMod(mod);
	}

	public void copyFrom(String other) {
		grainManager.copyFrom(other);
	}

	public boolean renameLayer(String name) {
		return grainManager.renameLayer(name);
	}

	public boolean changeActiveLayer(String name) {
		return grainManager.setActiveLayer(name);

	}

	public boolean newLayer(String name, double duration) {
		return grainManager.newLayer(name, duration);
	}

	public List<String> getLayerNames() {
		return grainManager.layerNames;
	}

	public boolean hasLayer(String name) {
		return grainManager.layerNames.contains(name);
	}

	public void clear() {
		grainManager.removeAll();
	}

	public void addFTable(FTable ft) {
		grainManager.active.fTables.add(ft);
	}
}
