package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Rhythm;
import model.Sequence;

public class RhythmGrid extends JPanel {

	private static final long serialVersionUID = -5339115350395707876L;

	private static final int hHeight = 30;

	Rhythm rhythm;
	int voice;
	
	int rHeight;
	int rWidth;

	public RhythmGrid(Rhythm rhythm) {
		this.rhythm = rhythm;
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

		Sequence sequence = rhythm.sequences[0];

		// Draw grid
		int vCount = rhythm.voiceCount;
		int rCount = sequence.quant * sequence.length;
		rWidth = getWidth() / (rCount + 1);
		rHeight = (getHeight() - hHeight) / vCount;
		
		g.setColor(Color.RED);
		g.fillRect(0, voice * rHeight + hHeight, rWidth, rHeight);
		
		for (int v = 0; v < vCount; v++) {
			g.setColor(Color.GRAY);
			for (int i = 0; i < sequence.strikeCount; i++) {
				int p = rhythm.sequences[v].strt[i];
				g.fillRect((p + 1) * rWidth, v * rHeight + hHeight, rWidth, rHeight);
			}
			g.setColor(Color.BLACK);
			for (int i = 0; i < rCount + 1; i++)
				g.drawRect(i * rWidth, v * rHeight + hHeight, rWidth, rHeight);
		}

		int hCount = sequence.length;
		int hWidth = rWidth * sequence.quant;
		for (int i = 0; i < hCount; i++) {
			g.drawRect(i * hWidth + rWidth, 0, hWidth, hHeight);
			g.drawString(Integer.toString(i + 1), i * hWidth + hWidth / 2 + rWidth, hHeight / 2);
		}
	}

	private void select(int x, int y, int button) {
		int v = -1;
		if (y > hHeight)
			v = (y - hHeight) / rHeight;
		int s = -1;
		if (s > rWidth)
			s = (x - rWidth) / rWidth;
		
		if (button == MouseEvent.BUTTON1) {
			if ()
		} else if (button == MouseEvent.BUTTON2) {
			
		}
	}
}
