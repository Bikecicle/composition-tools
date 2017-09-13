package grain;

import java.util.ArrayList;
import java.util.List;

import table.Table;

public class GrainManager {

	private List<Layer> layers;
	private Layer active;

	/**
	 * Add a new layer and set it to active
	 * 
	 * @return Index of the new layer
	 */
	public int newLayer() {
		Layer layer = new Layer();
		layers.add(layer);
		active = layer;
		return layers.size() - 1;
	}

	/**
	 * Change the active layer
	 * 
	 * @param index
	 *            Layer index
	 * @return If the index is valid
	 */
	public boolean setActiveLayer(int index) {
		if (index < 0 || index >= layers.size())
			return false;
		active = layers.get(index);
		return true;
	}
	
	public void renderAll() {
		String score = "";
		for (Layer layer : layers) {
			for (Grain grain : layer.sequence) {
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
	public int generateGrainMatrix(int fRes, int fMin, int fMax, int tRes, int zoomMax, int zoomVel, Table table) {
		List<Grain> matrix = new ArrayList<Grain>();
		int dur = (int) (Math.log(Math.pow(10, zoomMax)) / Math.log(zoomVel));
		int tSteps = dur * tRes;
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

}
