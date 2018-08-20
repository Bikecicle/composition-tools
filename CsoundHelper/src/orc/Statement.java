package orc;

public abstract class Statement {

	String alias;
	Statement[] params;
	Statement parent;
	int channels;
	
	boolean terminal;
	boolean defined;
	
	public Statement(String alias, boolean terminal, int channels, Statement... params) {
		this.alias = alias;
		this.terminal = terminal;
		this.channels = channels;
		this.params = params;
		for (Statement param : params)
			param.parent = this;
		defined = false;
	}
	
	public boolean isTerminal() {
		return terminal;
	}
}