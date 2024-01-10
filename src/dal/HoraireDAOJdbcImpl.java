package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import bo.Horaire;

// CRUD
public class HoraireDAOJdbcImpl implements GenericDAO<Horaire> {
	private static final String TABLE_NAME = " horaires ";
	
	private static final String DELETE = "DELETE FROM"+ TABLE_NAME +" WHERE id = ?";
	private static final String UPDATE = "UPDATE "+ TABLE_NAME +" SET jour = ?, heurededebut = ?, heuredefin = ?,creneau = ?,id_horaire = ? WHERE id = ?";
	private static final String INSERT = "INSERT INTO "+ TABLE_NAME +" (jour, heurededebut, heuredefin,creneau,id_restaurant) VALUES (?,?,?,?,?)";
	private static final String SELECT_BY_ID = "SELECT * FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String SELECT = "SELECT * FROM "+ TABLE_NAME;
	
	private Connection cnx;
	
	public HoraireDAOJdbcImpl() throws DALException {
		cnx = ConnectionProvider.getConnection();
	}
	
	public List<Horaire> selectAll() throws DALException {
		List<Horaire> horaires = new ArrayList<>(); 
		// alt + shift + r pour renommer partout
		
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Horaire horaire = new Horaire();
				horaire.setId(rs.getInt("id"));
				horaire.setJour(rs.getString("jour"));
				horaire.setHeureDeDebut(rs.getTime("heurededebut").toLocalTime());
				horaire.setHeureDeFin(rs.getTime("heuredefin").toLocalTime());
				horaire.setCreneau(rs.getString("creneau"));
				horaire.setIdRestaurant(rs.getInt("id_restaurant"));
				
				horaires.add(horaire);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de recuperer les informations", e);
		}
		return horaires;
	}
	
	public Horaire selectById(int id) throws DALException {
		Horaire horaire = null;
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id); // Remplace le '?' numero 1 par la valeur de l'id
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				horaire = new Horaire();
				horaire.setId(rs.getInt("id"));
				horaire.setJour(rs.getString("jour"));
				horaire.setHeureDeDebut(rs.getTime("heurededebut").toLocalTime());
				horaire.setHeureDeFin(rs.getTime("heuredefin").toLocalTime());
				horaire.setCreneau(rs.getString("creneau"));
				horaire.setIdRestaurant(rs.getInt("id_restaurant"));
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de recuperer les informations pour l'id "+ id, e);
		}
		return horaire;
	}
	
	public void insert(Horaire horaire) throws DALException {
		try {
			// L'ajout de RETURN_GENERATED_KEYS permet de récupérer l'id généré par la base
			PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, horaire.getJour());
			ps.setTime(2, Time.valueOf(horaire.getHeureDeDebut()));
			ps.setTime(3, Time.valueOf(horaire.getHeureDeDebut()));
			ps.setString(4, horaire.getCreneau());
			ps.setInt(5, horaire.getIdRestaurant());
			ps.executeUpdate();
			
			// Le bloc suivant permet de faire la récupération de l'id
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) { // Va chercher dans le resultat, la première ligne
				int id = rs.getInt(1); // plus précisément, le int à la première colonne
				horaire.setId(id);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible d'inserer les donnees.", e);
		}
	}
	
	public void update(Horaire horaire) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(UPDATE);
			ps.setString(1, horaire.getJour());
			ps.setTime(2, Time.valueOf(horaire.getHeureDeDebut()));
			ps.setTime(3, Time.valueOf(horaire.getHeureDeDebut()));
			ps.setString(4, horaire.getCreneau());
			ps.setInt(5, horaire.getIdRestaurant());
			ps.setInt(6, horaire.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Impossible de mettre a jour les informations pour l'id "+ horaire.getId(), e);
		}
	}
	
	public void delete(int id) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(DELETE);
			ps.setInt(1, id);
			int nbLignesSupprimees = ps.executeUpdate();
			if (nbLignesSupprimees == 0) {
				throw new DALException("Echec de suppression du horaire d'id " + id, null);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible de supprimer le horaire d'id "+ id, e);
		}
	}
}
