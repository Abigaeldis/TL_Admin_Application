package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bll.BLLException;
import bll.RestaurantBLL;
import bo.Restaurant;
import bo.Table;

// CRUD
public class TableDAOJdbcImpl implements GenericDAO<Table> {
	private static final String TABLE_NAME = "tables";
	private static final String DELETE = "DELETE FROM "+ TABLE_NAME +" WHERE id = ?";
	private static final String UPDATE = "UPDATE "+ TABLE_NAME +" SET num_table = ?, capacite_table = ?, etat = ?, id_restaurant = ? WHERE id = ?";
	private static final String INSERT = "INSERT INTO "+ TABLE_NAME +" (num_table, capacite_table, etat, id_restaurant) VALUES (?,?,?,?)";
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
				table.setNumTable(rs.getInt("num_table"));
				table.setCapaciteTable(rs.getInt("capacite_table"));
				table.setEtat(rs.getString("etat"));
				int idRestaurant = rs.getInt("id_restaurant");
				RestaurantBLL restaurantBll = new RestaurantBLL();
				List<Restaurant> restaurants = restaurantBll.selectAll();
				Restaurant restaurant = null;
				for (Restaurant current : restaurants) {
					if (current.getId() == idRestaurant) {
						restaurant = current;
					}
				}
				table.setRestaurant(restaurant);
				tables.add(table);
			}
		} catch (SQLException | BLLException e) {
			throw new DALException("Impossible de recuperer les informations", e);
		}
		return tables;
	}
	
	public Table selectById(int id) throws DALException {
		Table table = null;
		try {
			PreparedStatement ps = cnx.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id); // Remplace le '?' numero 1 par la valeur de l'id
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				table = new Table();
				table.setId(rs.getInt("id"));
				table.setNumTable(rs.getInt("num_table"));
				table.setCapaciteTable(rs.getInt("capacite_table"));
				table.setEtat(rs.getString("etat"));
				int idRestaurant = rs.getInt("id_restaurant");
				RestaurantBLL restaurantBll = new RestaurantBLL();
				List<Restaurant> restaurants = restaurantBll.selectAll();
				Restaurant restaurant = null;
				for (Restaurant current : restaurants) {
					if (current.getId() == idRestaurant) {
						restaurant = current;
					}
				}
				table.setRestaurant(restaurant);
			}
		} catch (SQLException | BLLException e) {
			throw new DALException("Impossible de recuperer les informations pour l'id "+ id, e);
		}
		return table;
	}
	
	public void insert(Table table) throws DALException {
		try {
			// L'ajout de RETURN_GENERATED_KEYS permet de récupérer l'id généré par la base
			PreparedStatement ps = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, table.getNumTable());
			ps.setInt(2, table.getCapaciteTable());
			ps.setString(3, table.getEtat());
			ps.setInt(4, table.getRestaurant().getId());
			ps.executeUpdate();
			
			// Le bloc suivant permet de faire la récupération de l'id
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) { // Va chercher dans le resultat, la première ligne
				int id = rs.getInt(1); // plus précisément, le int à la première colonne
				table.setId(id);
			}
		} catch (SQLException e) {
			throw new DALException("Impossible d'inserer les donnees.", e);
		}
	}
	
	public void update(Table table) throws DALException {
		try {
			PreparedStatement ps = cnx.prepareStatement(UPDATE);
			ps.setInt(1, table.getNumTable());
			ps.setInt(2, table.getCapaciteTable());
			ps.setString(3, table.getEtat());
			ps.setInt(4, table.getRestaurant().getId());
			ps.setInt(5, table.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DALException("Impossible de mettre a jour les informations pour l'id "+ table.getId(), e);
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
