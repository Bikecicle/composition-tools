package grain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import grain.gen.Generator;
import grain.mod.Modifier;
import main.FractalSynth;
import main.MediaThread;
import main.Medium;
import sound.Performance;
import sound.Performer;
import visual.ViewStream;

public class GrainManager {

	public static int MAX_GRAINS = 100000;

	public List<String> layerNames;
	public Layer active;
	public String project;
	public String mainProjectPath;
	public String projectPath;
	public String layerPath;
	public String imagePath;
	public String soundPath;

	public GrainManager(String mainProjectDir, String project) {
		layerNames = new ArrayList<String>();
		this.mainProjectPath = mainProjectDir;
		this.project = project;
		projectPath = mainProjectDir + project + "/";
		layerPath = projectPath + "layers/";
		imagePath = projectPath + "images/";
		soundPath = projectPath + "sounds/";
		FractalSynth.openDir(projectPath);
		FractalSynth.openDir(layerPath);
		FractalSynth.openDir(imagePath);
		FractalSynth.openDir(soundPath);
		File[] layerFiles = new File(layerPath).listFiles();
		if (layerFiles != null && layerFiles.length > 0) {
			for (File layerFile : layerFiles) {
				if (layerFile.getName().endsWith((".layer"))) {
					layerNames.add(layerFile.getName().replace(".layer", ""));
				}
			}
			active = loadLayer(layerNames.get(0));
		} else {
			// Start a single empty layer if new project
			// newLayer("default", 1);
		}
	}

	public String getProject() {
		return project;
	}

	public boolean setProject(String project) {
		this.project = project;
		projectPath = mainProjectPath + project + "/";
		layerPath = projectPath + "layers/";
		imagePath = projectPath + "images/";
		soundPath = projectPath + "sounds/";
		FractalSynth.openDir(projectPath);
		FractalSynth.openDir(layerPath);
		FractalSynth.openDir(imagePath);
		FractalSynth.openDir(soundPath);
		return true;
	}

	public void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(layerPath + active.name + ".layer"));
			out.writeObject(active);
			out.close();
			System.out.println(active.name + " - layer saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Layer loadLayer(String name) {
		if (layerNames.contains(name)) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(layerPath + name + ".layer"));
				Layer layer = (Layer) in.readObject();
				in.close();
				return layer;
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean newLayer(String name, double duration) {
		if (active != null)
			save();
		for (String other : layerNames) {
			if (other.equals(name))
				return false;
		}
		Layer layer = new Layer(name, duration);
		layerNames.add(layer.name);
		active = layer;
		save();
		updateNames();
		return true;
	}

	public boolean setActiveLayer(String name) {
		if (!layerNames.contains(name))
			return false;
		if (active != null)
			save();
		active = loadLayer(name);
		return true;
	}

	public void clear() {
		active.clear();
	}

	public boolean renameLayer(String name) {
		if (layerNames.contains(name))
			return false;
		new File(layerPath + active.name + ".layer").renameTo(new File(layerPath + name + ".layer"));
		active.name = name;
		updateNames();
		return true;
	}

	private void updateNames() {
		layerNames.clear();
		File[] layerFiles = new File(layerPath).listFiles();
		for (File layerFile : layerFiles) {
			if (layerFile.getName().endsWith((".layer")) && !layerFile.getName().equals(".layer")) {
				layerNames.add(layerFile.getName().replace(".layer", ""));
			}
		}
	}

	public void visualizeLayers() {
		save();
		for (String name : layerNames) {
			String[] args = new String[2];
			args[0] = layerPath + name + ".layer";
			args[1] = imagePath + name + ".jpg";
			ViewStream.main(args);
		}
	}

	public void renderAll(String title) {
		save();
		String path = soundPath + title + "/";
		System.out.println("Rendering layers to " + path);
		FractalSynth.openDir(path);
		for (String name : layerNames) {
			Performer performer = new Performer(path + "/" + title + "_" + name + ".wav");
			performer.play(loadLayer(name));
		}
	}

	public int applyMod(Modifier mod) {
		return mod.applyTo(active);
	}
	
	public void copyFrom(String name) {
		Layer other = loadLayer(name);
		active.addGrains(other.sequence);
		if (active.duration < other.duration)
			active.duration = other.duration;
		// TODO active.fTables.addAll(other.fTables);
	}
	
	public int generateGrains(Generator gen) {
		return gen.gen(active);
	}

	public void removeAll() {
		File[] layerFiles = new File(layerPath).listFiles();
		for (File file : layerFiles) {
			file.delete();
			System.out.println(file.getName() + " deleted");
		}
		updateNames();
		active = null;
	}
}
