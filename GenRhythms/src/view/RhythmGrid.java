package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Hit;
import model.Rhythm;
import model.Timbre;

public class RhythmGrid extends JPanel {

	private static final long serialVersionUID = -5339115350395707876L;

	private static final int hHeight = 30;

	Rhythm rhythm;
	int voice;
	int time;

	int rHeight;
	int rWidth;

	public RhythmGrid(Rhythm rhythm) {
		this.rhythm = rhythm;
		time = -1;
		setBorder(BorderFactory.createLineBorder(Color.black));

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				select(e.getX(), e.getY(), e.getButton());
			}
		});

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (rhythm != null) {
			int vCount = rhythm.voiceCount;
			int rCount = rhythm.sequences[0].quant * rhythm.sequences[0].length;
			rWidth = getWidth() / (rCount + 1);
			rHeight = (getHeight() - hHeight) / vCount;

			// Mark selected voice
			g.setColor(Color.RED);
			g.fillRect(0, voice * rHeight + hHeight, rWidth, rHeight);

			for (int v = 0; v < vCount; v++) {
				// Fill notes
				g.setColor(Color.GRAY);
				for (int i = 0; i < rhythm.sequences[v].hCount; i++) {
					int p = rhythm.sequences[v].hits[i].strt;
					g.fillRect((p + 1) * rWidth, v * rHeight + hHeight, rWidth, rHeight);
				}
				// Draw grid
				g.setColor(Color.BLACK);
				for (int i = 0; i < rCount + 1; i++)
					g.drawRect(i * rWidth, v * rHeight + hHeight, rWidth, rHeight);
			}

			// Draw header
			int hCount = rhythm.sequences[0].length;
			int hWidth = rWidth * rhythm.sequences[0].quant;
			for (int i = 0; i < hCount; i++) {
				g.drawRect(i * hWidth + rWidth, 0, hWidth, hHeight);
				g.drawString(Integer.toString(i + 1), i * hWidth + hWidth / 2 + rWidth, hHeight / 2);
			}

			// Outline selected note
			if (time >= 0) {

				g.setColor(Color.RED);
				g.drawRect((time + 1) * rWidth, voice * rHeight + hHeight, rWidth, rHeight);
			}
		}
	}

	private void select(int x, int y, int button) {
		if (y > hHeight && x > rWidth && rhythm != null) {
			int v = (y - hHeight) / rHeight;
			int t = (x - rWidth) / rWidth;
			if (button == MouseEvent.BUTTON1) {
				if (rhythm.sequences[v].hasAtTime(t))
					rhythm.sequences[v].removeAtTime(t);
				else
					rhythm.sequences[v].addAtTime(t);
			} else if (button == MouseEvent.BUTTON3) {
				voice = v;
				time = t;
			}
			paintComponent(getGraphics());
		}
	}
	
	public Hit getCurrentHit() {
		return rhythm.sequences[voice].getAtTime(time);
	}

	public Timbre getCurrentTimbre() {
		return rhythm.timbres[voice];
	}
}
