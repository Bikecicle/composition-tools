package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.Session;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Dimension;
import javax.swing.SpinnerNumberModel;

public class CreateSessionDialog extends JDialog {

	private static final long serialVersionUID = -5446177297454014L;

	private final JPanel contentPanel = new JPanel();
	private JTextField sampleField;
	private JSpinner voiceSpinner;
	private JSpinner measureSpinner;
	private JSpinner quantSpinner;
	private JButton okButton;

	Session session;
	boolean accepted;

	/**
	 * Create the dialog.
	 */
	public CreateSessionDialog() {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 152);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel lblSample = new JLabel("Sample pool");
				lblSample.setToolTipText("File containing samples to select from");
				panel.add(lblSample);
			}
			{
				sampleField = new JTextField();
				sampleField.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						checkComplete();
					}
				});
				panel.add(sampleField);
				sampleField.setColumns(10);
			}
			{
				JButton btnNewButton = new JButton("Browse");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						final JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						chooser.setCurrentDirectory(new File(GenRhythms.SAMPLE_DIR));
						int val = chooser.showOpenDialog(contentPanel);
						if (val == JFileChooser.APPROVE_OPTION) {
							sampleField.setText(chooser.getSelectedFile().getAbsolutePath());
						}
					}
				});
				panel.add(btnNewButton);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel lblVoiceCount = new JLabel("Voice count");
				panel.add(lblVoiceCount);
			}
			{
				voiceSpinner = new JSpinner();
				voiceSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
				voiceSpinner.setPreferredSize(new Dimension(40, 20));
				voiceSpinner.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						checkComplete();
					}
				});
				panel.add(voiceSpinner);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel lblMeasureCount = new JLabel("Measure count");
				panel.add(lblMeasureCount);
			}
			{
				measureSpinner = new JSpinner();
				measureSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
				measureSpinner.setPreferredSize(new Dimension(40, 20));
				measureSpinner.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						checkComplete();
					}
				});
				panel.add(measureSpinner);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel lblQuantization = new JLabel("Quantization");
				lblQuantization.setToolTipText("Maximim divisions of a measure");
				panel.add(lblQuantization);
			}
			{
				quantSpinner = new JSpinner();
				quantSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
				quantSpinner.setPreferredSize(new Dimension(40, 20));
				quantSpinner.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						checkComplete();
					}
				});
				panel.add(quantSpinner);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						session = new Session("untitled", (int) voiceSpinner.getValue(),
								(int) measureSpinner.getValue(), (int) quantSpinner.getValue(),
								GenRhythms.DEFAULT_TEMPO, sampleField.getText());
						accepted = true;
						setVisible(false);
					}
				});
				okButton.setEnabled(false);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						accepted = false;
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void checkComplete() {
		if ((int) voiceSpinner.getValue() > 0 && (int) measureSpinner.getValue() > 0
				&& (int) quantSpinner.getValue() > 0 && !sampleField.getText().equals(""))
			okButton.setEnabled(true);
		else
			okButton.setEnabled(false);
	}
}
