package view;

import javax.swing.JPanel;

import model.Timbre;
import javax.swing.BoxLayout;

public class TimbrePanel extends JPanel {

	private static final long serialVersionUID = -907169325542274455L;

	MinMaxPanel posPanel;
	MinMaxPanel attPanel;
	MinMaxPanel decPanel;
	MinMaxPanel susPanel;
	MinMaxPanel relPanel;
	MinMaxPanel slevPanel;
	
	Timbre timbre;
	
	
	public TimbrePanel(Timbre timbre) {
		super();
		this.timbre = timbre;
		initialize();
	}
	
	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		posPanel = new MinMaxPanel("pos");
		add(posPanel);
		
		attPanel = new MinMaxPanel("att");
		add(attPanel);
		
		decPanel = new MinMaxPanel("dec");
		add(decPanel);
		
		susPanel = new MinMaxPanel("sus");
		add(susPanel);
		
		relPanel = new MinMaxPanel("rel");
		add(relPanel);
		
		slevPanel = new MinMaxPanel("slev");
		add(slevPanel);
	}
	
	public void setTimbre(Timbre timbre) {
		this.timbre = timbre;
		posPanel.setData(timbre.pos);
		attPanel.setData(timbre.att);
		decPanel.setData(timbre.dec);
		susPanel.setData(timbre.sus);
		relPanel.setData(timbre.rel);
		slevPanel.setData(timbre.slev);
	}
}
