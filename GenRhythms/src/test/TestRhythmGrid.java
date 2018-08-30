package test;

import java.awt.EventQueue;

import javax.swing.JFrame;

import view.RhythmGrid;
import model.Hit;
import model.Rhythm;
import model.Sequence;
import model.Timbre;

import java.awt.BorderLayout;

public class TestRhythmGrid {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestRhythmGrid window = new TestRhythmGrid();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestRhythmGrid() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int voices = 2;
		Timbre[] ts = new Timbre[voices];
		Sequence[] ss = new Sequence[voices];

		Timbre t = new Timbre(null, 1, 1);
		t.sample = "C:/Users/Griffin/Documents/Ableton/User Library/Samples/tools/340619__mickyman5000__drill-sharp-stops-1.wav";
		t.pos[0] = 0f;
		t.pos[1] = 1f;
		t.att[0] = 0.00000001f;
		t.att[1] = 0.00000001f;
		t.dec[0] = 0.001f;
		t.dec[1] = 0.001f;
		t.sus[0] = 1f;
		t.sus[1] = 1f;
		t.rel[0] = 0f;
		t.rel[1] = 0f;
		t.slev[0] = 1f;
		t.slev[1] = 1f;
		ts[0] = t;
		ts[1] = t;

		Sequence s1 = new Sequence(3, 8, 120);
		s1.addHit(new Hit(0, 0, 0, 0, 0, 0, 0));
		s1.addHit(new Hit(8, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
		s1.addHit(new Hit(16, 1, 1, 1, 1, 1, 1));
		ss[0] = s1;
		
		Sequence s2 = new Sequence(3, 8, 120);
		s2.addHit(new Hit(2, 0, 0, 0, 0, 0, 0));
		s2.addHit(new Hit(3, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f));
		s2.addHit(new Hit(15, 1, 1, 1, 1, 1, 1));
		ss[1] = s2;

		Rhythm r = new Rhythm(ts, ss);
		
		RhythmGrid sequenceGrid = new RhythmGrid(r);
		frame.getContentPane().add(sequenceGrid, BorderLayout.CENTER);
	}

}
