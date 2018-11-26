package pojo;

import java.sql.SQLException;
import java.util.List;

public interface IDeck{
	public long getOwnerId();

	public List<Card> getCards() throws SQLException, Exception;

}
