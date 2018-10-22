package sco;

import java.io.Serializable;
import java.util.HashMap;

public class ParamMap implements Serializable {

	private static final long serialVersionUID = 4383530773644064693L;
	
	HashMap<CompoundKey, String> map;

	public ParamMap() {
		map = new HashMap<>();
	}

	public String put(int i, int p, String name) {
		return map.put(new CompoundKey(i, p), name);
	}

	public String get(int i, int p) {
		return map.get(new CompoundKey(i, p));
	}
	
	public void putAll(ParamMap other) {
		this.map.putAll(other.map);
	}
	
	@Override
	public String toString() {
		String s = "";
		for (CompoundKey key : map.keySet())
			s += "i" + key.i + ", p" + key.p + ": " + map.get(key) + "\n";
		return s;
	}
	
	private class CompoundKey {
		
		int i;
		int p;
		
		public CompoundKey(int i, int p) {
			this.i = i;
			this.p = p;
		}
		
		@Override
		public boolean equals(Object obj) {
			CompoundKey other = (CompoundKey) obj;
			return this.i == other.i && this.p == other.p;
		}
		
		@Override
		public int hashCode() {
			return (i << 16) + p;
		}
	}
}
