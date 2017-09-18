package grain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import main.FractalSynth;
import main.Performer;
import table.Table;
import visual.ViewStream;

public class GrainManager {

	private List<Layer> layers;
	private Layer active;
	private String project;
	private String projectDir;
	private String layerDir;
	private String imageDir;
	private String soundDir;

	public GrainManager(String projectDir, String project) {
		layers = new ArrayList<Layer>();
		this.projectDir = projectDir;
		this.project = project;
		layerDir = projectDir + project + "/layers/";
		imageDir = projectDir + project + "/images/";
		soundDir = projectDir + project + "/sounds/";
		FractalSynth.openDir(projectDir + project + "/");
		FractalSynth.openDir(layerDir);
		FractalSynth.openDir(imageDir);
		FractalSynth.openDir(soundDir);
		File[] layerFiles = new File(layerDir).listFiles();
		if (layerFiles != null && layerFiles.length > 0) {
			for (File layerFile : layerFiles) {
				ObjectInputStream in;
				try {
					in = new ObjectInputStream(new FileInputStream(layerFile));
					layers.add((Layer) in.readObject());
					in.close();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
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
		layerDir = projectDir + project + "/layers/";
		imageDir = projectDir + project + "/images/";
		soundDir = projectDir + project + "/sounds/";
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
			} catch (IOException e) {
				e.printStackTrace();
			}
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

	public boolean setActiveLayer(String name) {
		for (Layer layer : layers) {
			if (layer.name.equals(name)) {
				active = layer;
				return true;
			}
		}
		return false;
	}

	public boolean renameLayer(String name) {
		for (Layer layer : layers) {
			if (layer.name.equals(name))
				return false;
		}
		active.name = name;
		return true;
	}

	public String writeScore() {
		String score = "";
		for (Layer layer : layers) {
			score += layer.toScore();
		}
		return score;
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

	public void render(String filename) {
		String sco = writeScore();
		String out = soundDir + filename + ".wav";
		Performer.render(sco, out);
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
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel);
		int tSteps = (int) (dur * tRes);
		double fStep = 1.0 * fRes / (fMax - fMin);
		float gAmp = 1.0f / table.getMaxDensity();

		for (int t = 0; t < tSteps; t++) {
			for (int f = 0; f < fRes; f++) {
				if (table.get(t, f, tRes, fRes) == 1) {
					float gTime = 1.0f * t / tRes;
					int gFreq = (int) (fMin + (fStep * f));
					matrix.add(new Grain(gTime, gFreq, gAmp));
				}
			}
		}
		active.addGrains(matrix);
		return matrix.size();
	}

	public int genMatriax(int fRes1, int fMin1, int fMax1, int tRes1, int zoomMax, int zoomVel, Table table1, int fRes2,
			int fmin2, int fMax2, int tRes2, Table table2) {
		List<Grain> matrix = new ArrayList<Grain>();
		double dur = Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel);
		int tSteps = (int) (dur * tRes1);
		double fStep = 1.0 * fRes1 / (fMax1 - fMin1);
		float gAmp = 1.0f / table1.getMaxDensity();
		for (int t = 0; t < tSteps; t++) {
			int pSteps = getPulses(table2, t / tRes1);
			for (int p = 0; p < pSteps; p++)
			for (int f = 0; f < fRes1; f++) {
				if (table1.get(t, f, tRes1, fRes1) == 1) {
					float gTime = 1.0f * t / tRes1;
					int gFreq = (int) (fMin1 + (fStep * f));
					matrix.add(new Grain(gTime, gFreq, gAmp));
				}
			}
		}
		active.addGrains(matrix);
		return matrix.size();
	}
	
	private int getPulses(Table table, double time) {
		return 0;
	}
}
