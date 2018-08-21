package view;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JButton;

public class RhythmPanel extends JPanel {
	public RhythmPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblRhythm = new JLabel("Rhythm");
		add(lblRhythm);
		
		JButton btnPlay = new JButton("Play");
		add(btnPlay);
		
		JButton btnRemove = new JButton("Remove");
		add(btnRemove);
	}
	
	private static final long serialVersionUID = -6356605104253250220L;

}
