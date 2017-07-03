package utility;

public class TimeInterval {

	long millis;

	public TimeInterval(long millis) {
		this.millis = millis;
	}

	public String toString() {
		long time = millis;
		long days = time / 86400000;
		time = time % 86400000;
		long hrs = time / 3600000;
		time = time % 3600000;
		long min = time / 60000;
		time = time % 60000;
		long sec = time / 1000;
		String str = "";
		if (days > 0 ) {
			str += days + " days, ";
		}	
		if (hrs < 10) {
			str += "0";
		}
		str += hrs + ":";
		if (min < 10) {
			str += "0";
		}
		str += min + ":";
		if (sec < 10) {
			str += "0";
		}
		str += sec;
		return str;
	}
}
