package view;

import java.awt.Dimension;
import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.SpinnerNumberModel;

public class MinMaxPanel extends JPanel {

	private static final long serialVersionUID = -6109418252463419627L;

	JSpinner spinnerMin;
	JSpinner spinnerMax;

	String name;
	float[] data;

	public MinMaxPanel(String name) {
		super();
		this.name = name;
		initialize();
	}

	private void initialize() {
		Label label = new Label(name);
		add(label);

		JLabel lblMin = new JLabel("min");
		add(lblMin);

		spinnerMin = new JSpinner();
		spinnerMin.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(1)));
		spinnerMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				data[0] = (float) spinnerMin.getValue();
			}
		});
		spinnerMin.setPreferredSize(new Dimension(RhythmEditor.spinnerWidth, 20));
		add(spinnerMin);

		JLabel lblMax = new JLabel("max");
		add(lblMax);

		spinnerMax = new JSpinner();
		spinnerMax.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(1)));
		spinnerMax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				data[1] = (float) spinnerMax.getValue();
			}
		});
		spinnerMax.setPreferredSize(new Dimension(RhythmEditor.spinnerWidth, 20));
		add(spinnerMax);
	}

	public void setData(float[] data) {
		this.data = data;
		spinnerMin.setValue(data[0]);
		spinnerMax.setValue(data[1]);
	}
}
