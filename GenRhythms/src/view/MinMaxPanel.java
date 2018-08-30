package view;

import java.awt.Dimension;
import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class MinMaxPanel extends JPanel {

	private static final long serialVersionUID = -6109418252463419627L;

	public MinMaxPanel(String name, float[] data, int spinnerWidth) {
		super();

		Label label = new Label(name);
		add(label);

		JLabel lblMin = new JLabel("min");
		add(lblMin);

		JSpinner spinnerPosMin = new JSpinner();
		spinnerPosMin.setPreferredSize(new Dimension(spinnerWidth, 20));
		spinnerPosMin.setValue(data[0]);
		add(spinnerPosMin);

		JLabel lblMax = new JLabel("max");
		add(lblMax);

		JSpinner spinnerPosMax = new JSpinner();
		spinnerPosMax.setPreferredSize(new Dimension(spinnerWidth, 20));
		spinnerPosMax.setValue(data[1]);
		add(spinnerPosMax);
	}

}
