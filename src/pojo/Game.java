package pojo;

public class Game extends DomainObject{
	private long player1;
	private long player2;
	private long currentPlayer;
	private long deck1;
	private long deck2;
	private String p1Status;
	private String p2Status;
	
	public Game(long id, int version, long player1, long player2, long currentPlayer, long deck1, long deck2,
			String p1Status, String p2Status) {
		super(id, version);
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = currentPlayer;
		this.deck1 = deck1;
		this.deck2 = deck2;
		this.p1Status = p1Status;
		this.p2Status = p2Status;
	}

	public long getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(long currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public String getP1Status() {
		return p1Status;
	}

	public void setP1Status(String p1Status) {
		this.p1Status = p1Status;
	}

	public String getP2Status() {
		return p2Status;
	}

	public void setP2Status(String p2Status) {
		this.p2Status = p2Status;
	}

	public long getPlayer1() {
		return player1;
	}

	public long getPlayer2() {
		return player2;
	}

	public long getDeck1() {
		return deck1;
	}

	public long getDeck2() {
		return deck2;
	}
}
