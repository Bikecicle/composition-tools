package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;

import model.Batch;
import model.Rhythm;
import model.Session;
import sound.Performer;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;

public class GenRhythms {

	public static final String TITLE = "GenRhythms";
	public static final String SESSION_DIR = "sessions";
	public static final String SAMPLE_DIR = "samples";
	public static final int DEFAULT_TEMPO = 120;
	public static final float DEFAULT_DUR = 0.1f;
	public static final float DEFAULT_AMP = 100f;
	
	public static final int RIGHT_BUTTON_WIDTH = 100;

	private JLabel lblBatch;
	private JLabel lblRhythm;
	private JSlider sliderT;
	private JSlider sliderS;

	private JFrame frame;
	
	private Performer performer;
	private Session session;
	private Batch batch;
	private int rIndex;

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
		performer = new Performer();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("Session");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateSessionDialog dialog = new CreateSessionDialog();
				dialog.setVisible(true);
				if (dialog.accepted) {
					session = dialog.session;
					session.createBatch();
					batch = session.getCurrentBatch();
					rIndex = 0;
					updateInfo();
				}
			}
		});
		mnFile.add(mntmNew);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(Filters.sessionFilter());
				chooser.setCurrentDirectory(new File(SESSION_DIR));
				int val = chooser.showSaveDialog(frame);
				if (val == JFileChooser.APPROVE_OPTION) {
					try {
						File file = chooser.getSelectedFile();
						String path = file.getPath();
						if (!path.endsWith(Session.SESSION_EXT)) {
							file = new File(path + Session.SESSION_EXT);
						}
						session.title = file.getName().substring(0, file.getName().length() - 4);
						session.save(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				updateInfo();
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(Filters.sessionFilter());
				chooser.setCurrentDirectory(new File(SESSION_DIR));
				int val = chooser.showOpenDialog(frame);
				if (val == JFileChooser.APPROVE_OPTION) {
					try {
						session = Session.load(chooser.getSelectedFile());
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
				}
				batch = session.getCurrentBatch();
				rIndex = 0;
				updateInfo();
			}
		});
		mnFile.add(mntmLoad);

		JMenu mnSet = new JMenu("Set");
		menuBar.add(mnSet);

		JMenuItem mntmExport = new JMenuItem("Export");
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(Filters.setFilter());
				chooser.setCurrentDirectory(new File(SESSION_DIR));
				int val = chooser.showSaveDialog(frame);
				if (val == JFileChooser.APPROVE_OPTION) {
					try {
						File file = chooser.getSelectedFile();
						String path = file.getPath();
						if (!path.endsWith(Session.SET_EXT))
							file = new File(path + Session.SET_EXT);
						session.exportSet(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		mnSet.add(mntmExport);

		JMenuItem mntmImport = new JMenuItem("Import");
		mntmImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(Filters.setFilter());
				chooser.setCurrentDirectory(new File(SESSION_DIR));
				int val = chooser.showOpenDialog(frame);
				if (val == JFileChooser.APPROVE_OPTION) {
					try {
						session.importSet(chooser.getSelectedFile());
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		mnSet.add(mntmImport);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);

		JPanel panel_4 = new JPanel();
		panel.add(panel_4);

		JLabel lblTempo = new JLabel("Tempo");
		panel_4.add(lblTempo);

		JSpinner spinner = new JSpinner();
		spinner.setToolTipText("Beats per minute as if 4 beats per measure");
		spinner.setPreferredSize(new Dimension(40, 20));
		spinner.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel_4.add(spinner);
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (session != null)
					session.setTempo((float) spinner.getValue());
			}
		});
		spinner.setModel(new SpinnerNumberModel(new Float(DEFAULT_TEMPO), new Float(0), null, new Float(1)));

		JPanel panel_5 = new JPanel();
		panel.add(panel_5);

		JLabel lblAmp = new JLabel("Amp");
		panel_5.add(lblAmp);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (session != null)
					session.setAmp((float) spinner_1.getValue());
			}
		});
		spinner_1.setToolTipText("Unimplemented");
		spinner_1.setPreferredSize(new Dimension(40, 20));
		spinner_1.setModel(new SpinnerNumberModel(new Float(DEFAULT_AMP), new Float(0), null, new Float(1)));
		panel_5.add(spinner_1);

		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JButton btnAddToSet = new JButton("Add to Set");
		btnAddToSet.setToolTipText("List of rhythm exemplars injected back into the gene pool");
		btnAddToSet.setMinimumSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnAddToSet.setMaximumSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnAddToSet.setPreferredSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnAddToSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				session.addToSet(batch.get(rIndex));
			}
		});
		panel_1.add(btnAddToSet);

		JButton btnPlaySet = new JButton("Play Set");
		btnPlaySet.setToolTipText("Play back all rhythms in the set");
		btnPlaySet.setMinimumSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnPlaySet.setMaximumSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnPlaySet.setPreferredSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnPlaySet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Rhythm rhythm : session.set)
					performer.play(rhythm);
			}
		});
		panel_1.add(btnPlaySet);

		JButton btnResetGen = new JButton("Reset Gen");
		btnResetGen.setToolTipText("Wipe ratings and restart current batch");
		btnResetGen.setPreferredSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnResetGen.setMinimumSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnResetGen.setMaximumSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnResetGen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				batch.reset();
				rIndex = 0;
				updateInfo();
			}
		});
		panel_1.add(btnResetGen);

		JButton btnEndBatch = new JButton("Skip Gen");
		btnEndBatch.setToolTipText("Jump to next generation");
		btnEndBatch.setMinimumSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnEndBatch.setMaximumSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnEndBatch.setPreferredSize(new Dimension(RIGHT_BUTTON_WIDTH, 23));
		btnEndBatch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				batch.reset();
				session.advance();
				session.createBatch();
				rIndex = 0;
				updateInfo();
			}
		});
		panel_1.add(btnEndBatch);

		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		JButton btnPlay = new JButton("Play");
		btnPlay.setToolTipText("Play back the current rhythm");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				performer.play(batch.get(rIndex));
			}
		});
		panel_2.add(btnPlay, BorderLayout.WEST);

		JButton btnNext = new JButton("Next");
		btnNext.setToolTipText("Apply rating to current rhythm and bring up the next one");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				batch.get(rIndex).rateTimbre(sliderT.getValue());
				batch.get(rIndex).rateSequence(sliderS.getValue());
				if (rIndex < batch.size() - 1) {
					rIndex++;
					updateInfo();
					performer.play(batch.get(rIndex));
				} else {
					BatchEndDialog dialog = new BatchEndDialog();
					dialog.setVisible(true);
					if (dialog.next) {
						session.advance();
						session.createBatch();
						rIndex = 0;
						updateInfo();
						performer.play(batch.get(rIndex));
					}
				}
			}
		});
		panel_2.add(btnNext, BorderLayout.EAST);

		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));

		lblBatch = new JLabel("Generation #");
		panel_3.add(lblBatch);

		lblRhythm = new JLabel("Rhythm #");
		panel_3.add(lblRhythm);
		
		JPanel panel_6 = new JPanel();
		frame.getContentPane().add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
				panel_7.setLayout(new BorderLayout(0, 0));
		
				JLabel lblTimbreRating = new JLabel("Timbre Rating");
				panel_7.add(lblTimbreRating, BorderLayout.NORTH);
				lblTimbreRating.setHorizontalAlignment(SwingConstants.CENTER);
				
						sliderT = new JSlider();
						panel_7.add(sliderT, BorderLayout.CENTER);
						sliderT.setPaintTicks(true);
						sliderT.setSnapToTicks(true);
						sliderT.setPaintLabels(true);
						sliderT.setMinimum(1);
						sliderT.setMaximum(5);
						sliderT.setMajorTickSpacing(1);
						
						JPanel panel_8 = new JPanel();
						panel_6.add(panel_8);
						panel_8.setLayout(new BorderLayout(0, 0));
						
						JLabel lblSequenceRating = new JLabel("Sequence Rating");
						lblSequenceRating.setHorizontalAlignment(SwingConstants.CENTER);
						panel_8.add(lblSequenceRating, BorderLayout.NORTH);
						
						sliderS = new JSlider();
						sliderS.setSnapToTicks(true);
						sliderS.setPaintTicks(true);
						sliderS.setPaintLabels(true);
						sliderS.setMinimum(1);
						sliderS.setMaximum(5);
						sliderS.setMajorTickSpacing(1);
						panel_8.add(sliderS, BorderLayout.CENTER);
	}

	private void updateInfo() {
		if (session == null) {
			frame.setTitle(TITLE);
		} else {
			frame.setTitle(TITLE + " - " + session.title);
			lblBatch.setText("Generation " + session.getStage());
			lblRhythm.setText("Rhythm " + (rIndex + 1) + "/" + batch.size());
		}
	}
}
