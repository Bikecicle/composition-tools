package view;

import javax.swing.JPanel;

import model.Rhythm;
import model.Set;
import sound.Performer;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class ExPanel extends JPanel {
	
	Rhythm rhythm;
	Performer performer;
	
	JCheckBox checkBox;

	public ExPanel(Rhythm rhythm, Performer performer) {
		super();
		this.rhythm = rhythm;
		this.performer = performer;
		initialize();
	}
	
	private void initialize() {

		JLabel lblTitle = new JLabel("title");
		add(lblTitle);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				performer.play(rhythm);
			}
		});
		add(btnPlay);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RhythmEditor rhythmEditor = new RhythmEditor(rhythm);
				rhythmEditor.setVisible(true);
			}
		});
		add(btnEdit);
		
		checkBox = new JCheckBox("");
		add(checkBox);
	}
	
	public boolean isSelected() {
		return checkBox.isSelected();
	}
}
