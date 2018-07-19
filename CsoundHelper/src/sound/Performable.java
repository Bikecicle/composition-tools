package sound;

import orc.Orchestra;
import sco.Score;

public interface Performable {
	
	public Score getScore();
	
	public Orchestra getOrchestra();

}
