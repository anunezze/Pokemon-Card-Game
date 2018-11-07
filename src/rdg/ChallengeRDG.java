package rdg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

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
	public static List<ChallengeRDG> findAllOpen() throws SQLException{
		Connection connection = new DbRegistry().getConnection();
		List<ChallengeRDG> result = new ArrayList<ChallengeRDG>();
		String query = "SELECT * FROM challenge";
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(new ChallengeRDG(rs.getInt("id"), 
						rs.getInt("challenger"),
						rs.getInt("challengee"), 
						ChallengeStatus.valueOf(rs.getInt("status"))));
		}
		ps.close();
		connection.close();
		
		return result;
	}
	
	public void insert() throws SQLException{
		Connection connection = new DbRegistry().getConnection();
		String query = "INSERT INTO challenge (challenger,challengee, status) VALUES (?,?,?);";
		PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, this.challenger);
		ps.setLong(2, this.challengee);
		ps.setInt(3, this.status.ordinal());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if(rs.next()){
			this.id = rs.getInt(1);
		}
		ps.close();
		connection.close();
	}
	
	public void update() throws SQLException{
		Connection connection = new DbRegistry().getConnection();
		String query = "UPDATE challenge SET challenger=?,challengee=?, status=? WHERE id=?;";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, this.challenger);
		ps.setLong(2, this.challengee);
		ps.setInt(3, this.status.ordinal());
		ps.setLong(4, this.id);
		ps.executeUpdate();
		ps.close();
		connection.close();
	}
	
}
