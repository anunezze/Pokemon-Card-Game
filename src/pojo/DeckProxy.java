package pojo;

import java.sql.SQLException;
import java.util.List;

import InputMapper.DeckInputMapper;

public class DeckProxy extends DomainObject implements IDeck{
	private long ownerId;
	private List<Card> cards;
	
	public DeckProxy(long id, int version, long ownerId) {
		super(id, version);
		this.ownerId = ownerId;
	}

	@Override
	public long getOwnerId() {
		return ownerId;
	}

	@Override
	public List<Card> getCards() throws SQLException, Exception {
		return DeckInputMapper.findById(ownerId).getCards();
	}
	
}
