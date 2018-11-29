package command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ICommand {
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException, Exception;
}
