package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Plat;

// CRUD
public class PlatDAOJdbcImpl implements GenericDAO<Plat> {
	private static final String TABLE_NAME = " plats ";
	
	private static final String DELETE = "DELETE FROM"+ TABLE_NAME +" WHERE id = ?";
	private static final String UPDATE = "UPDATE "+ TABLE_NAME +" SET nom = ?, description = ?, prix = ?, type = ? WHERE id = ?";
	private static final String INSERT = "INSERT INTO "+ TABLE_NAME +" (nom, description, prix, type, id_carte) VALUES (?,?,?,?,?)";
	private static final String SELECT_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String SELECT = "SELECT * FROM "+ TABLE_NAME;
	
	private Connection cnx;
	
	public PlatDAOJdbcImpl() throws DALException {
		cnx = ConnectionProvider.getConnection();
	}
	
	public List<Plat> selectAll() throws DALException {
		List<Plat> plats = new ArrayList<>(); 
		// alt + shift + r pour renommer partout
		
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Plat plat = new Plat();
				plat.setId(rs.getInt("id"));
				plat.setNom(rs.getString("nom"));
				plat.setDescription(rs.getString("description"));
				plat.setPrix(rs.getFloat("prix"));
				plat.setType(rs.getString("type"));
				plat.setIdCarte(rs.getInt("id_carte"));
				
				plats.add(plat);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de recuperer les informations", e);
		}
		return plats;
	}
	
	public Plat selectById(int id) throws DALException {
		Plat plat = null;
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id); // Remplace le '?' numero 1 par la valeur de l'id
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				plat = new Plat();
				plat.setId(rs.getInt("id"));
				plat.setNom(rs.getString("nom"));
				plat.setDescription(rs.getString("description"));
				/*
				 * rs.getDate() --> restitue une java.sql.Date
				 * plat.setPrix(...) s'attend à une LocalDate
				 * .toLocalDate() permet la conversion de java.sql.Date vers LocalDate
				 */
				plat.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de recuperer les informations pour l'id "+ id, e);
		}
		return plat;
	}
	
	public void insert(Plat plat) throws DALException {
		try {
			// L'ajout de RETURN_GENERATED_KEYS permet de récupérer l'id généré par la base
			PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, plat.getNom());
			ps.setString(2, plat.getDescription());
			ps.setFloat(3, plat.getPrix());
			ps.setString(4, plat.getType());
			ps.setInt(5, plat.getIdCarte());
			ps.executeUpdate();
			
			// Le bloc suivant permet de faire la récupération de l'id
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) { // Va chercher dans le resultat, la première ligne
				int id = rs.getInt(1); // plus précisément, le int à la première colonne
				plat.setId(id);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible d'inserer les donnees.", e);
		}
	}
	
	public void update(Plat plat) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(UPDATE);
			ps.setString(1, plat.getNom());
			ps.setString(2, plat.getDescription());
			ps.setFloat(3, plat.getPrix());
			ps.setString(4, plat.getType());
			ps.setInt(5, plat.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Impossible de mettre a jour les informations pour l'id "+ plat.getId(), e);
		}
	}
	
	public void delete(int id) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(DELETE);
			ps.setInt(1, id);
			int nbLignesSupprimees = ps.executeUpdate();
			if (nbLignesSupprimees == 0) {
				throw new DALException("Echec de suppression du plat d'id " + id, null);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de supprimer le plat d'id "+ id, e);
		}
	}
}
