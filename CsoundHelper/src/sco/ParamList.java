package sco;

import java.util.ArrayList;

public class ParamList extends ArrayList<Param<?>> {

	private static final long serialVersionUID = -5128191780314019039L;
	
	public Param<?> find(String name) {
		for (Param<?> param : this)
			if (param.name.equals(name))
				return param;
		return null;
	}
}
