package sound;

import java.io.Serializable;

import orc.Orchestra;
import sco.Score;

public interface Performance extends Serializable {
	
	public Score getScore();
	
	public Orchestra getOrchestra();

}
