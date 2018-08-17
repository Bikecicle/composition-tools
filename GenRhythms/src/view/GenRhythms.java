package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import java.io.IOException;
import javax.swing.JLabel;
import model.Session;

import javax.swing.SpinnerNumberModel;

public class GenRhythms {
	
	public static final String TITLE = "GenRhythms";
	public static final int DEFAULT_TEMPO = 120;

	private JFrame frame;
	private Session session;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GenRhythms window = new GenRhythms();
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
	public GenRhythms() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		updateTitle();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("Session");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateSession dialog = new CreateSession();
				dialog.setVisible(true);
				if (dialog.accepted)
					session = dialog.session;
				updateTitle();
			}
		});
		mnFile.add(mntmNew);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				int val = chooser.showSaveDialog(frame);
				if (val == JFileChooser.APPROVE_OPTION) {
					try {
						session.save(chooser.getSelectedFile());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				int val = chooser.showOpenDialog(frame);
				if (val == JFileChooser.APPROVE_OPTION) {
					try {
						session = Session.load(chooser.getSelectedFile());
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
				}
				updateTitle();
			}
		});
		mnFile.add(mntmLoad);
		
		JMenu mnSet = new JMenu("Set");
		menuBar.add(mnSet);
		
		JMenuItem mntmExport = new JMenuItem("Export");
		mnSet.add(mntmExport);
		
		JMenuItem mntmImport = new JMenuItem("Import");
		mnSet.add(mntmImport);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblTempo = new JLabel("Tempo");
		panel.add(lblTempo);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(DEFAULT_TEMPO), null, null, new Integer(1)));
		panel.add(spinner);
	}
	
	private void updateTitle() { 
		if (session == null) {
			frame.setTitle(TITLE);
		} else {
			frame.setTitle(TITLE + " - " + session.title);
		}
	}
}
