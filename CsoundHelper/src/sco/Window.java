package sco;

public enum Window {

	hamming(1),
	hanning(2),
	bartlett(3),
	blackman(4),
	blackmanHarris(5),
	gaussian(6),
	kaiser(7),
	rectangle(8),
	sync(9);
	
	int id;
	
	private Window(int id) {
		this.id = id;
	}
}
