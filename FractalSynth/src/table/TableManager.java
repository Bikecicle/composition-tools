package table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.management.openmbean.OpenDataException;

import main.FractalSynth;
import visual.ViewTable;

public class TableManager {

	private List<String> tableList;
	private String tableDir;

	public TableManager(String tableDir) {
		this.tableDir = tableDir;
		tableList = new ArrayList<String>();
		File[] dirList = new File(tableDir).listFiles();
		if (dirList != null) {
			for (File dir : dirList) {
				if (dir.isDirectory()) {
					tableList.add(dir.getName());
				}
			}
		}
	}

	public List<String> getTableList() {
		return tableList;
	}

	public Table getTable(String name) {
		if (tableList.contains(name)) {
			Table table = null;
			String path = tableDir + name + "/" + name + ".table";
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
				table = (Table) in.readObject();
				in.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			return table;
		}
		return null;
	}

	public void saveTable(Table table) {
		String saveDir = tableDir + table.name + "/";
		FractalSynth.openDir(saveDir);
		String tablePath = saveDir + table.name + ".table";
		String imagePath = saveDir + table.name + "_" + table.filters.size() + ".jpg";
		File tbl = new File(tablePath);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tbl));
			out.writeObject(table);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] args = { tablePath, imagePath };
		ViewTable.main(args);
	}

	public void deleteTable(String name) {
		File dir = new File(tableDir + name + "/");
		File[] files = dir.listFiles();
		for (File file : files) {
			file.delete();
		}
		dir.delete();
		tableList.remove(name);
	}

	public void generateTable(String name, int tRes, int fRes, double zoomVel, int zoomMax, int kMax, double posX,
			double posY) {
		Table table = new Table(name, tRes, fRes, zoomVel, zoomMax, kMax, posX, posY);
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
		saveTable(table);
	}

	public void filter(String name, Filter filter) {
		Table table = getTable(name);
		filter.applyTo(table);
		saveTable(table);
	}
}
