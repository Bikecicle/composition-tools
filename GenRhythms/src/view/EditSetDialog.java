package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Rhythm;
import model.Set;
import sound.Performer;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditSetDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	public EditSetDialog(Set set, Performer performer) {
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		contentPanel.add(panel_1, BorderLayout.EAST);
		
		List<ExPanel> exPanels = new ArrayList<>();
		for (Rhythm rhythm : set) {
			ExPanel exPanel = new ExPanel(rhythm, performer);
			panel.add(exPanel);
			exPanels.add(exPanel);
		}
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (ExPanel exPanel : exPanels) {
					if (exPanel.isSelected()) {
						set.remove(exPanel.rhythm);
						exPanel.setVisible(false);
					}
				}
			}
		});
		panel_1.add(btnRemove);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPanel.add(panel_2, BorderLayout.SOUTH);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		panel_2.add(btnBack);


	}
}
