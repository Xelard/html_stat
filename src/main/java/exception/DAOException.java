package exception;

import java.sql.SQLException;

public class DAOException extends SQLException {

	public DAOException(String message) {
		super(message);
	}

}
