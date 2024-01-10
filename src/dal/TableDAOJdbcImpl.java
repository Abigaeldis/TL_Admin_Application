package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Table;
import bo.Table;

// CRUD
public class TableDAOJdbcImpl implements GenericDAO<Table> {
	private static final String TABLE_NAME = "tables";
	
	private static final String DELETE = "DELETE FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String UPDATE = "UPDATE "+ TABLE_NAME +" SET nom = ?, prenom = ?, mail = ?, motdepasse = ?, "
			+ "								telephone = ?, adresse = ?, role = ?  WHERE id = ?";
	private static final String INSERT = "INSERT INTO "+ TABLE_NAME +" (nom, nature, date_sortie) VALUES (?,?,?)";
	private static final String SELECT_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String SELECT = "SELECT * FROM "+ TABLE_NAME;
	
	private Connection cnx;
	
	public TableDAOJdbcImpl() throws DALException {
		cnx = ConnectionProvider.getConnection();
	}
	
	public List<Table> selectAll() throws DALException {
		List<Table> tables = new ArrayList<>(); 
		// alt + shift + r pour renommer partout
		
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Table table = new Table();
				table.setId(rs.getInt("id"));
				table.setNom(rs.getString("nom"));
				table.setPreom(rs.getString("prenom"));
				table.setNature(rs.getString("nature"));
				table.setDateSortie(rs.getDate("date_sortie").toLocalDate());
				
				tables.add(table);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de recuperer les informations", e);
		}
		return tables;
	}
	
	public Table selectById(int id) throws DALException {
		Table composant = null;
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id); // Remplace le '?' numero 1 par la valeur de l'id
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				composant = new Table();
				composant.setId(rs.getInt("id"));
				composant.setNom(rs.getString("nom"));
				composant.setNature(rs.getString("nature"));
				/*
				 * rs.getDate() --> restitue une java.sql.Date
				 * composant.setDateSortie(...) s'attend à une LocalDate
				 * .toLocalDate() permet la conversion de java.sql.Date vers LocalDate
				 */
				composant.setDateSortie(rs.getDate("date_sortie").toLocalDate());
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de recuperer les informations pour l'id "+ id, e);
		}
		return composant;
	}
	
	public void insert(Table composant) throws DALException {
		try {
			// L'ajout de RETURN_GENERATED_KEYS permet de récupérer l'id généré par la base
			PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, composant.getNom());
			ps.setString(2, composant.getNature());
			ps.setDate(3, Date.valueOf(composant.getDateSortie()));
			ps.executeUpdate();
			
			// Le bloc suivant permet de faire la récupération de l'id
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) { // Va chercher dans le resultat, la première ligne
				int id = rs.getInt(1); // plus précisément, le int à la première colonne
				composant.setId(id);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible d'inserer les donnees.", e);
		}
	}
	
	public void update(Table composant) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(UPDATE);
			ps.setString(1, composant.getNom());
			ps.setString(2, composant.getNature());
			ps.setDate(3, Date.valueOf(composant.getDateSortie()));
			ps.setInt(4, composant.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Impossible de mettre a jour les informations pour l'id "+ composant.getId(), e);
		}
	}
	
	public void delete(int id) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(DELETE);
			ps.setInt(1, id);
			int nbLignesSupprimees = ps.executeUpdate();
			if (nbLignesSupprimees == 0) {
				throw new DALException("Echec de suppression du composant d'id " + id, null);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de supprimer le composant d'id "+ id, e);
		}
	}
}
