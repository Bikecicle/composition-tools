package table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import main.FractalSynth;
import main.MediaThread;
import main.Medium;

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
		String propPath = saveDir + "properties.txt";
		
		// Serialize and write the table to file
		File tbl = new File(tablePath);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tbl));
			out.writeObject(table);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Write the properties to a text file
		try {
			PrintWriter out = new PrintWriter(propPath);
			out.println(table.toString());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Render and save a visualization of the table
		String[] args = { tablePath, imagePath };
		new Thread(new MediaThread(Medium.tableViewer, args)).start();
		if (!tableList.contains(table.name))
			tableList.add(table.name);
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

	public void filter(String name, Filter filter) {
		Table table = getTable(name);
		filter.applyTo(table);
		saveTable(table);
	}
}
