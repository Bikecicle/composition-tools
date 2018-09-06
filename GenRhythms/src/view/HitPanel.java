package view;

import java.awt.Dimension;
import java.awt.Label;

import javax.swing.JPanel;
import javax.swing.JSpinner;

import model.Hit;
import javax.swing.BoxLayout;

public class HitPanel extends JPanel {

	private static final long serialVersionUID = -7822024815549297414L;

	JSpinner spinnerPosS;
	JSpinner spinnerAttS;
	JSpinner spinnerDecS;
	JSpinner spinnerSusS;
	JSpinner spinnerRelS;
	JSpinner spinnerSlevS;

	public HitPanel() {
		initialize();
	}

	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel panel_9 = new JPanel();
		add(panel_9);

		Label label_s_pos = new Label("pos");
		panel_9.add(label_s_pos);

		spinnerPosS = new JSpinner();
		panel_9.add(spinnerPosS);
		spinnerPosS.setPreferredSize(new Dimension(GenRhythms.SPINNER_WIDTH, 20));

		JPanel panel_10 = new JPanel();
		add(panel_10);

		Label label_s_att = new Label("att");
		panel_10.add(label_s_att);

		spinnerAttS = new JSpinner();
		spinnerAttS.setPreferredSize(new Dimension(GenRhythms.SPINNER_WIDTH, 20));
		panel_10.add(spinnerAttS);

		JPanel panel_11 = new JPanel();
		add(panel_11);

		Label label_s_dec = new Label("dec");
		panel_11.add(label_s_dec);

		spinnerDecS = new JSpinner();
		spinnerDecS.setPreferredSize(new Dimension(GenRhythms.SPINNER_WIDTH, 20));
		panel_11.add(spinnerDecS);

		JPanel panel_12 = new JPanel();
		add(panel_12);

		Label label_s_sus = new Label("sus");
		panel_12.add(label_s_sus);

		spinnerSusS = new JSpinner();
		spinnerSusS.setPreferredSize(new Dimension(GenRhythms.SPINNER_WIDTH, 20));
		panel_12.add(spinnerSusS);

		JPanel panel_13 = new JPanel();
		add(panel_13);

		Label label_s_rel = new Label("rel");
		panel_13.add(label_s_rel);

		spinnerRelS = new JSpinner();
		spinnerRelS.setPreferredSize(new Dimension(GenRhythms.SPINNER_WIDTH, 20));
		panel_13.add(spinnerRelS);

		JPanel panel_14 = new JPanel();
		add(panel_14);

		Label label_s_slev = new Label("slev");
		panel_14.add(label_s_slev);

		spinnerSlevS = new JSpinner();
		spinnerSlevS.setPreferredSize(new Dimension(GenRhythms.SPINNER_WIDTH, 20));
		panel_14.add(spinnerSlevS);
	}

	public void setHit(Hit hit) {
		if (hit != null) {
			spinnerPosS.setValue(hit.pos);
			spinnerAttS.setValue(hit.att);
			spinnerDecS.setValue(hit.dec);
			spinnerSusS.setValue(hit.sus);
			spinnerRelS.setValue(hit.rel);
			spinnerSlevS.setValue(hit.slev);
		}
	}

}
