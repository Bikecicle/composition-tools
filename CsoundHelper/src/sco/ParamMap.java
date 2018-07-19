package sco;

import java.util.HashMap;

public class ParamMap {

	public static final String KEY_FORMAT = "%d %d";

	public HashMap<String, String> map;

	public ParamMap() {
		map = new HashMap<>();
	}

	public String put(int i, int p, String name) {
		return map.put(String.format(KEY_FORMAT, i, p), name);
	}

	public String get(int i, int p) {
		return map.get(String.format(KEY_FORMAT, i, p));
	}
}
