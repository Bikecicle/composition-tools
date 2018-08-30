package view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Label;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

import model.Hit;
import model.Rhythm;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;

public class RhythmEditor {
	
	public static final int spinnerWidth = 50;

	private JFrame frame;
	private Rhythm rhythm;

	JSpinner spinnerPosS;
	JSpinner spinnerAttS;
	JSpinner spinnerDecS;
	JSpinner spinnerSusS;
	JSpinner spinnerRelS;
	JSpinner spinnerSlevS;
	RhythmGrid rhythmGrid;

	public RhythmEditor(Rhythm rhythm) {
		this.rhythm = rhythm;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 538, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);

		Label label = new Label("pos");
		panel_2.add(label);

		JLabel lblMin = new JLabel("min");
		panel_2.add(lblMin);

		JSpinner spinnerPosMin = new JSpinner();
		spinnerPosMin.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_2.add(spinnerPosMin);

		JLabel lblMax = new JLabel("max");
		panel_2.add(lblMax);

		JSpinner spinnerPosMax = new JSpinner();
		spinnerPosMax.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_2.add(spinnerPosMax);

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);

		Label label_1 = new Label("att");
		panel_3.add(label_1);

		JLabel label_2 = new JLabel("min");
		panel_3.add(label_2);

		JSpinner spinnerAttMin = new JSpinner();
		spinnerAttMin.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_3.add(spinnerAttMin);

		JLabel label_3 = new JLabel("max");
		panel_3.add(label_3);

		JSpinner spinnerAttMax = new JSpinner();
		spinnerAttMax.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_3.add(spinnerAttMax);

		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);

		Label label_4 = new Label("dec");
		panel_4.add(label_4);

		JLabel label_5 = new JLabel("min");
		panel_4.add(label_5);

		JSpinner spinnerDecMin = new JSpinner();
		spinnerDecMin.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_4.add(spinnerDecMin);

		JLabel label_6 = new JLabel("max");
		panel_4.add(label_6);

		JSpinner spinnerDecMax = new JSpinner();
		spinnerDecMax.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_4.add(spinnerDecMax);

		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);

		Label label_7 = new Label("sus");
		panel_5.add(label_7);

		JLabel label_8 = new JLabel("min");
		panel_5.add(label_8);

		JSpinner spinnerSusMin = new JSpinner();
		spinnerSusMin.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_5.add(spinnerSusMin);

		JLabel label_9 = new JLabel("max");
		panel_5.add(label_9);

		JSpinner spinnerSusMax = new JSpinner();
		spinnerSusMax.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_5.add(spinnerSusMax);

		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6);

		Label label_10 = new Label("rel");
		panel_6.add(label_10);

		JLabel label_11 = new JLabel("min");
		panel_6.add(label_11);

		JSpinner spinnerRelMin = new JSpinner();
		spinnerRelMin.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_6.add(spinnerRelMin);

		JLabel label_12 = new JLabel("max");
		panel_6.add(label_12);

		JSpinner spinnerRelMax = new JSpinner();
		spinnerRelMax.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_6.add(spinnerRelMax);

		JPanel panel_7 = new JPanel();
		panel_1.add(panel_7);

		Label label_13 = new Label("slev");
		panel_7.add(label_13);

		JLabel label_14 = new JLabel("min");
		panel_7.add(label_14);

		JSpinner spinnerSlevMin = new JSpinner();
		spinnerSlevMin.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_7.add(spinnerSlevMin);

		JLabel label_15 = new JLabel("max");
		panel_7.add(label_15);

		JSpinner spinnerSlevMax = new JSpinner();
		spinnerSlevMax.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_7.add(spinnerSlevMax);

		JPanel panel_8 = new JPanel();
		frame.getContentPane().add(panel_8, BorderLayout.EAST);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));

		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9);

		Label label_s_pos = new Label("pos");
		panel_9.add(label_s_pos);

		spinnerPosS = new JSpinner();
		spinnerPosS.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_9.add(spinnerPosS);

		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10);

		Label label_s_att = new Label("att");
		panel_10.add(label_s_att);

		spinnerAttS = new JSpinner();
		spinnerAttS.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_10.add(spinnerAttS);

		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11);

		Label label_s_dec = new Label("dec");
		panel_11.add(label_s_dec);

		spinnerDecS = new JSpinner();
		spinnerDecS.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_11.add(spinnerDecS);

		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12);

		Label label_s_sus = new Label("sus");
		panel_12.add(label_s_sus);

		spinnerSusS = new JSpinner();
		spinnerSusS.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_12.add(spinnerSusS);

		JPanel panel_13 = new JPanel();
		panel_8.add(panel_13);

		Label label_s_rel = new Label("rel");
		panel_13.add(label_s_rel);

		spinnerRelS = new JSpinner();
		spinnerRelS.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_13.add(spinnerRelS);

		JPanel panel_14 = new JPanel();
		panel_8.add(panel_14);

		Label label_s_slev = new Label("slev");
		panel_14.add(label_s_slev);

		spinnerSlevS = new JSpinner();
		spinnerSlevS.setPreferredSize(new Dimension(spinnerWidth, 20));
		panel_14.add(spinnerSlevS);

		rhythmGrid = new RhythmGrid(rhythm);
		rhythmGrid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Hit hit = rhythmGrid.getCurrentHit();
				if (hit != null) {
					spinnerPosS.setValue(hit.pos);
					spinnerAttS.setValue(hit.att);
					spinnerDecS.setValue(hit.dec);
					spinnerSusS.setValue(hit.sus);
					spinnerRelS.setValue(hit.rel);
					spinnerSlevS.setValue(hit.slev);
				}
			}
		});
		frame.getContentPane().add(rhythmGrid, BorderLayout.CENTER);

	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
}
