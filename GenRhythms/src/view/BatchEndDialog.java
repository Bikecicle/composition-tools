package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BatchEndDialog extends JDialog {

	private static final long serialVersionUID = 9071347862037224747L;
	
	boolean next;

	/**
	 * Create the dialog.
	 */
	public BatchEndDialog() {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setBounds(100, 100, 343, 86);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.SOUTH);
			panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			{
				JButton btnContinue = new JButton("Continue");
				btnContinue.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						next = true;
						setVisible(false);
					}
				});
				panel.add(btnContinue);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						next = false;
						setVisible(false);
					}
				});
				panel.add(btnCancel);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			{
				JLabel lblBatchComplete = new JLabel("Batch complete. Continue to the next generation?");
				panel.add(lblBatchComplete);
				lblBatchComplete.setVerticalAlignment(SwingConstants.TOP);
			}
		}
	}

}
