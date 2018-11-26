package pojo;

import util.ChallengeStatus;

public class Challenge extends DomainObject {
	private long challenger;
	private long challengee;
	private long challengerDeck;
	private ChallengeStatus status;
	
	public Challenge(long id, int version, long challenger, long challengee, long challengerDeck,
			ChallengeStatus status) {
		super(id, version);
		this.challenger = challenger;
		this.challengee = challengee;
		this.challengerDeck = challengerDeck;
		this.status = status;
	}

	public long getChallenger() {
		return challenger;
	}

	public void setChallenger(long challenger) {
		this.challenger = challenger;
	}

	public long getChallengee() {
		return challengee;
	}

	public void setChallengee(long challengee) {
		this.challengee = challengee;
	}

	public long getChallengerDeck() {
		return challengerDeck;
	}

	public void setChallengerDeck(long challengerDeck) {
		this.challengerDeck = challengerDeck;
	}

	public ChallengeStatus getStatus() {
		return status;
	}

	public void setStatus(ChallengeStatus status) {
		this.status = status;
		this.markDirty();
	}
	
}
