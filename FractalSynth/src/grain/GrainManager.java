package grain;

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
import table.Table;
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
			//newLayer("default", 1);
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
		writeScore(active);
		System.out.println(active.name + " - score saved");
		// String[] args = new String[2];
		// args[0] = layerDir + layer.name + ".sco";
		// args[1] = imageDir + layer.name + ".jpg";
		// ViewStream.main(args);

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

	public void writeScore(Layer layer) {
		int sCount = layer.sequence.size() / MAX_GRAINS + 1;
		List<List<Grain>> sections = new ArrayList<List<Grain>>();
		for (int s = 0; s < sCount; s++) {
			sections.add(new ArrayList<Grain>());
		}
		for (int g = 0; g < layer.sequence.size(); g++) {
			sections.get(g / MAX_GRAINS).add(layer.sequence.get(g));
		}

		try {
			for (int s = 0; s < sCount; s++) {
				PrintWriter out = new PrintWriter(layerPath + layer.name + "_" + s + ".sco");
				if (layer.sources.isEmpty()) {
				out.println("f1 0 4096 10 1");
				} else {
					for (int id : layer.sources.keySet()) {
						// TODO print out ftgen statement
					}
				}
				for (Grain g : sections.get(s)) {
					out.println(g.statement());
				}
				out.println("e");
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
		File[] files = new File(layerPath).listFiles();
		for (File file : files) {
			if (file.getName().endsWith(".sco")) {
				System.out.println("Rendering " + file.getName());
				String sco = layerPath + file.getName();
				String out = path + title + "_" + file.getName().replace(".sco", ".wav");
				String[] args = { sco, out };
				new Thread(new MediaThread(Medium.performer, args)).start();
			}
		}
	}

	/**
	 * Generates a new grain matrix into the active layer. Grain matrix is
	 * defined here as a discrete distribution of grains with binary amplitudes
	 * 
	 * @param fRes
	 *            Frequency resolution
	 * @param fMin
	 *            Minimum frequency
	 * @param fMax
	 *            Maximum freqTency
	 * @param tRes
	 *            Time resolution (steps/second)
	 * @param zoomMax
	 *            Maximum zoom (10^zoomMax)
	 * @param zoomVel
	 *            Zoom velocity (magnification/second)
	 * @param table
	 *            Source table
	 * @return Grains added
	 */
	public int genMatrix(int fRes, int fMin, int fMax, int tRes, int zoomMax, int zoomVel, Table table) {
		List<Grain> matrix = new ArrayList<Grain>();
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel + 1);
		int tSteps = (int) (dur * tRes);
		double fStep = 1.0 * fRes / (fMax - fMin);
		float gAmp = 1.0f / table.getMaxDensity();

		for (int t = 0; t < tSteps; t++) {
			for (int f = 0; f < fRes; f++) {
				if (table.get(t, f, tRes, fRes, zoomVel) == 1) {
					float gTime = 1.0f * t / tRes;
					int gFreq = (int) (fMin + (fStep * f));
					matrix.add(new OscGrain(gTime, Grain.DEFAULT_DUR, gAmp, gFreq, Grain.DEFAULT_ATT,
							Grain.DEFAULT_DEC, fMin, fMax, active.duration));
				}
			}
		}
		active.addGrains(matrix);
		return matrix.size();
	}

	public int genPulsarMatrix(int fMinP, int fMaxP, int fResP, int fMinD, int fMaxD, int minResD, int maxResD,
			double zoomVel, int zoomMax, Table tableP, Table tableD) {
		List<Grain> matrix = new ArrayList<Grain>();
		int tRes = fMaxP;
		double fStepP = 1.0 * (fMaxP - fMinP) / fResP;
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel + 1);
		int tSteps = (int) (dur * tRes);

		for (int t = 0; t < tSteps; t++) {
			for (int fp = 0; fp < fResP; fp++) {
				int pFreq = (int) (fMinP + (fStepP * fp));
				// Add a grain if this frequency releases a pulse at this time
				// step, and the corresponding value in tableP is 1
				if ((int) (t % (1.0 * fMaxP / pFreq)) == 0 && tableP.get(t, fp, tRes, fResP, zoomVel) == 1) {
					double scl = 1.0 * (pFreq - fMinP) / (fMaxP - fMinP);
					int fResD = (int) ((maxResD - minResD) * scl) + minResD;
					double fStepD = 1.0 * (fMaxD - fMinD) / fResD;
					for (int f = 0; f < fResD; f++) {
						if (tableD.get(t, f, tRes, fResD, zoomVel) == 1) {
							float gTime = 1.0f * t / tRes;
							int gFreq = (int) (fMinD + (fStepD * f));
							matrix.add(new OscGrain(gTime, Grain.DEFAULT_DUR, Grain.DEFAULT_AMP, gFreq,
									Grain.DEFAULT_ATT, Grain.DEFAULT_DEC, fMinD, fMaxD, dur));
						}
					}
				}
			}
		}
		active.addGrains(matrix);
		if (active.duration < dur)
			active.duration = dur;
		return matrix.size();
	}

	public int applyMod(Modifier mod) {
		return mod.applyTo(active);
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
