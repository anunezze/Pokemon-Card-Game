package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DbRegistry;
import util.ChallengeStatus;

public class ChallengeRDG {
	
	private long id;
	private long challenger;
	private long challengee;
	private ChallengeStatus status;
	
	public ChallengeRDG(long id, long challenger, long challengee, ChallengeStatus status) {
		super();
		this.id = id;
		this.challenger = challenger;
		this.challengee = challengee;
		this.status = status;
	}

	public ChallengeStatus getStatus() {
		return status;
	}

	public void setStatus(ChallengeStatus status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public long getChallenger() {
		return challenger;
	}

	public long getChallengee() {
		return challengee;
	}
	
	public static ChallengeRDG find(long id) throws SQLException{
		ChallengeRDG result = null;
		
		Connection connection = new DbRegistry().getConnection();
		String query = "SELECT * FROM challenge WHERE id =?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, id);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			result = new ChallengeRDG(rs.getInt("id"), 
					 	rs.getInt("challenger"), 
					 	rs.getInt("challengee"), 
					 	ChallengeStatus.valueOf(rs.getInt("status")));
		}
		ps.close();
		connection.close();
		
		return result;
	}
	
	public void insert() throws SQLException{
		Connection connection = new DbRegistry().getConnection();
		String query = "INSERT INTO challenge (challenger,challengee, status) VALUES (?,?,?);";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, this.challenger);
		ps.setLong(2, this.challengee);
		ps.setInt(3, this.status.ordinal());
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
	
}
