package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.Label;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import model.Sequence;

public class RhythmEditor {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RhythmEditor window = new RhythmEditor();
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
	public RhythmEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
		
		JSpinner spinner = new JSpinner();
		panel_2.add(spinner);
		
		JLabel lblMax = new JLabel("max");
		panel_2.add(lblMax);
		
		JSpinner spinner_1 = new JSpinner();
		panel_2.add(spinner_1);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		Label label_1 = new Label("att");
		panel_3.add(label_1);
		
		JLabel label_2 = new JLabel("min");
		panel_3.add(label_2);
		
		JSpinner spinner_2 = new JSpinner();
		panel_3.add(spinner_2);
		
		JLabel label_3 = new JLabel("max");
		panel_3.add(label_3);
		
		JSpinner spinner_3 = new JSpinner();
		panel_3.add(spinner_3);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		
		Label label_4 = new Label("dec");
		panel_4.add(label_4);
		
		JLabel label_5 = new JLabel("min");
		panel_4.add(label_5);
		
		JSpinner spinner_4 = new JSpinner();
		panel_4.add(spinner_4);
		
		JLabel label_6 = new JLabel("max");
		panel_4.add(label_6);
		
		JSpinner spinner_5 = new JSpinner();
		panel_4.add(spinner_5);
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		
		Label label_7 = new Label("sus");
		panel_5.add(label_7);
		
		JLabel label_8 = new JLabel("min");
		panel_5.add(label_8);
		
		JSpinner spinner_6 = new JSpinner();
		panel_5.add(spinner_6);
		
		JLabel label_9 = new JLabel("max");
		panel_5.add(label_9);
		
		JSpinner spinner_7 = new JSpinner();
		panel_5.add(spinner_7);
		
		JPanel panel_6 = new JPanel();
		panel_1.add(panel_6);
		
		Label label_10 = new Label("rel");
		panel_6.add(label_10);
		
		JLabel label_11 = new JLabel("min");
		panel_6.add(label_11);
		
		JSpinner spinner_8 = new JSpinner();
		panel_6.add(spinner_8);
		
		JLabel label_12 = new JLabel("max");
		panel_6.add(label_12);
		
		JSpinner spinner_9 = new JSpinner();
		panel_6.add(spinner_9);
		
		JPanel panel_7 = new JPanel();
		panel_1.add(panel_7);
		
		Label label_13 = new Label("slev");
		panel_7.add(label_13);
		
		JLabel label_14 = new JLabel("min");
		panel_7.add(label_14);
		
		JSpinner spinner_10 = new JSpinner();
		panel_7.add(spinner_10);
		
		JLabel label_15 = new JLabel("max");
		panel_7.add(label_15);
		
		JSpinner spinner_11 = new JSpinner();
		panel_7.add(spinner_11);
		
		JPanel panel_8 = new JPanel();
		frame.getContentPane().add(panel_8, BorderLayout.EAST);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9);
		
		Label label_16 = new Label("pos");
		panel_9.add(label_16);
		
		JSpinner spinner_12 = new JSpinner();
		panel_9.add(spinner_12);
		
		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10);
		
		Label label_17 = new Label("att");
		panel_10.add(label_17);
		
		JSpinner spinner_13 = new JSpinner();
		panel_10.add(spinner_13);
		
		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11);
		
		Label label_18 = new Label("dec");
		panel_11.add(label_18);
		
		JSpinner spinner_14 = new JSpinner();
		panel_11.add(spinner_14);
		
		JPanel panel_12 = new JPanel();
		panel_8.add(panel_12);
		
		Label label_19 = new Label("sus");
		panel_12.add(label_19);
		
		JSpinner spinner_15 = new JSpinner();
		panel_12.add(spinner_15);
		
		JPanel panel_13 = new JPanel();
		panel_8.add(panel_13);
		
		Label label_20 = new Label("rel");
		panel_13.add(label_20);
		
		JSpinner spinner_16 = new JSpinner();
		panel_13.add(spinner_16);
		
		JPanel panel_14 = new JPanel();
		panel_8.add(panel_14);
		
		Label label_21 = new Label("slev");
		panel_14.add(label_21);
		
		JSpinner spinner_17 = new JSpinner();
		panel_14.add(spinner_17);
	}

}
