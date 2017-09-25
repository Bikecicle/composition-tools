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
import main.Performer;
import table.Table;
import visual.ViewStream;

public class GrainManager {

	private List<Layer> layers;
	private Layer active;
	private String project;
	private String mainProjectDir;
	private String projectDir;
	private String layerDir;
	private String imageDir;
	private String soundDir;

	public GrainManager(String mainProjectDir, String project) {
		layers = new ArrayList<Layer>();
		this.mainProjectDir = mainProjectDir;
		this.project = project;
		projectDir = mainProjectDir + project + "/";
		layerDir = projectDir + "layers/";
		imageDir = projectDir + "images/";
		soundDir = projectDir + "sounds/";
		FractalSynth.openDir(projectDir);
		FractalSynth.openDir(layerDir);
		FractalSynth.openDir(imageDir);
		FractalSynth.openDir(soundDir);
		File[] layerFiles = new File(layerDir).listFiles();
		if (layerFiles != null && layerFiles.length > 0) {
			for (File layerFile : layerFiles) {
				if (layerFile.getName().endsWith((".layer"))) {
					try {
						ObjectInputStream in = new ObjectInputStream(new FileInputStream(layerFile.getAbsolutePath()));
						layers.add((Layer) in.readObject());
						in.close();
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}

			active = layers.get(0);
		} else {
			// Start a single empty layer if new project
			newLayer("default");
		}
	}

	public String getProject() {
		return project;
	}

	public boolean setProject(String project) {
		this.project = project;
		projectDir = mainProjectDir + project + "/";
		layerDir = projectDir + "layers/";
		imageDir = projectDir + "images/";
		soundDir = projectDir + "sounds/";
		FractalSynth.openDir(projectDir);
		FractalSynth.openDir(layerDir);
		FractalSynth.openDir(imageDir);
		FractalSynth.openDir(soundDir);
		return true;
	}

	public void save() {
		for (Layer layer : layers) {
			try {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(layerDir + layer.name + ".layer"));
				out.writeObject(layer);
				out.close();
				System.out.println(layer.name + " - layer saved");
			} catch (IOException e) {
				e.printStackTrace();
			}
			writeScore(layer, layerDir + layer.name + ".sco");
			System.out.println(layer.name + " - score saved");
			// String[] args = new String[2];
			// args[0] = layerDir + layer.name + ".sco";
			// args[1] = imageDir + layer.name + ".jpg";
			// ViewStream.main(args);
		}
	}

	public boolean newLayer(String name) {
		for (Layer layer : layers) {
			if (layer.name.equals(name))
				return false;
		}
		Layer layer = new Layer(name);
		layers.add(layer);
		active = layer;
		return true;
	}

	public Layer getActiveLayer() {
		return active;
	}

	public boolean setActiveLayer(String name) {
		for (Layer layer : layers) {
			if (layer.name.equals(name)) {
				active = layer;
				return true;
			}
		}
		return false;
	}

	public void clear() {
		active.sequence.clear();
	}

	public boolean renameLayer(String name) {
		for (Layer layer : layers) {
			if (layer.name.equals(name))
				return false;
		}
		active.name = name;
		return true;
	}

	public void writeScore(Layer layer, String scoreFile) {
		try {
			PrintWriter out = new PrintWriter(scoreFile);
			out.println("f1 0 4096 10 1");
			for (Grain g : layer.sequence) {
				out.println(g.statement());
			}
			out.println("e");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void visualizeLayers() {
		save();
		for (Layer layer : layers) {
			String[] args = new String[2];
			args[0] = layerDir + layer.name + ".layer";
			args[1] = imageDir + layer.name + ".jpg";
			ViewStream.main(args);
		}
	}

	public void render(String name, String filename) {
		String sco = layerDir + name + ".sco";
		String out = soundDir + filename + ".wav";
		String[] args = { sco, out };
		new MediaThread(Medium.performer, args).run();
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
					matrix.add(new Grain(Grain.DEFAULT_IID, gTime, Grain.DEFAULT_DUR, gAmp, gFreq, Grain.DEFAULT_ATT,
							Grain.DEFAULT_DEC));
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
		float gAmp = 2.0f / tableD.getMaxDensity();

		for (int t = 0; t < tSteps; t++) {
			for (int fp = 0; fp < fResP; fp++) {
				int pFreq = (int) (fMinP + (fStepP * fp));
				// Add a grain if this frequency releases a pulse at this time
				// step, and the corresponding value in tableP is 1
				if (t % (int) (fMaxP / pFreq) == 0 && tableP.get(t, fp, tRes, fResP, zoomVel) == 1) {
					double scl = 1.0 * (pFreq - fMinP) / (fMaxP - fMinP);
					int fResD = (int) ((maxResD - minResD) * scl) + minResD;
					double fStepD = 1.0 * (fMaxD - fMinD) / fResD;
					for (int f = 0; f < fResD; f++) {
						if (tableD.get(t, f, tRes, fResD, zoomVel) == 1) {
							float gTime = 1.0f * t / tRes;
							int gFreq = (int) (fMinD + (fStepD * f));
							matrix.add(new Grain(Grain.DEFAULT_IID, gTime, Grain.DEFAULT_DUR, gAmp, gFreq,
									Grain.DEFAULT_ATT, Grain.DEFAULT_DEC));
						}
					}
				}
			}
		}
		active.addGrains(matrix);
		return matrix.size();
	}
	
	public int applyMod(Modulator mod) {
		return mod.applyTo(active);
	}
}
