package factory;

import pojo.Challenge;
import util.ChallengeStatus;
import util.IdGenerator;

public abstract class ChallengeFactory {
	
	public static Challenge createNew(long challenger, long challengee, long challengerDeck) {
		Challenge c = new Challenge(IdGenerator.getInstance().createID(), 1, challenger, challengee, challengerDeck, ChallengeStatus.OPEN);
		c.markNew();
		return c;
	}
	
	public static Challenge createClean(long id, int version, long challenger, long challengee, long challengerDeck,
			ChallengeStatus status) {
		Challenge c = new Challenge(id, version, challenger, challengee, challengerDeck, status);
		c.markClean();
		return c;
	}

}
