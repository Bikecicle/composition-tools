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
import model.Timbre;

public class RhythmEditor {
	
	public static final int spinnerWidth = 50;

	private JFrame frame;
	private Rhythm rhythm;
	
	TimbrePanel timbrePanel;
	RhythmGrid rhythmGrid;
	private HitPanel hitPanel;

	public RhythmEditor(Rhythm rhythm) {
		this.rhythm = rhythm;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 538, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		rhythmGrid = new RhythmGrid(rhythm);
		rhythmGrid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				update();
			}
		});
		frame.getContentPane().add(rhythmGrid, BorderLayout.CENTER);
		
		timbrePanel = new TimbrePanel(rhythmGrid.getCurrentTimbre());
		frame.getContentPane().add(timbrePanel, BorderLayout.WEST);
		
		hitPanel = new HitPanel();
		frame.getContentPane().add(hitPanel, BorderLayout.EAST);

	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	private void update() {
		timbrePanel.setTimbre(rhythmGrid.getCurrentTimbre());
		hitPanel.setHit(rhythmGrid.getCurrentHit());
	}
}
