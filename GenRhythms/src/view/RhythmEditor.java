package view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import model.Rhythm;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import sound.Performer;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RhythmEditor {

	private JFrame frame;
	private Rhythm rhythm;
	private Performer performer;
	
	private TimbrePanel timbrePanel;
	private RhythmGrid rhythmGrid;
	private HitPanel hitPanel;
	private JPanel panel;
	private JButton btnPlay;
	private JButton btnBack;

	public RhythmEditor(Rhythm rhythm, Performer performer) {
		this.rhythm = rhythm;
		this.performer = performer;
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 621, 347);
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
		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				performer.play(rhythm);
			}
		});
		panel.add(btnPlay);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panel.add(btnBack);

	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	private void update() {
		timbrePanel.setTimbre(rhythmGrid.getCurrentTimbre());
		hitPanel.setHit(rhythmGrid.getCurrentHit());
	}
}
