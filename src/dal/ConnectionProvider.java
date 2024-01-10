package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Classe utilitaire pour mutualiser la connexion à la base de données
 * De cette manière, il n'est pas nécessaire de copier coller le code de connexion dans chaque DAO
 */
public abstract class ConnectionProvider {
	public static Connection getConnection() throws DALException {
		String url = "jdbc:sqlserver://localhost;databasename=COMPOSANTS;trustservercertificate=true";
		try {
			return DriverManager.getConnection(url, System.getenv("USER_SQLSERVER"), System.getenv("PASSWORD_SQLSERVER"));
		} catch (SQLException e) {
			throw new DALException("Erreur de connexion a la base de donnees", e);
		}
	}
}
