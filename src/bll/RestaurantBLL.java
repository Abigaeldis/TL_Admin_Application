package bll;

import java.util.List;

import bo.Restaurant;
import dal.DALException;
import dal.GenericDAO;
import dal.RestaurantDAOJdbcImpl;

public class RestaurantBLL {
	private GenericDAO<Restaurant> dao;
	
	public RestaurantBLL() throws BLLException {
		try {
			dao = new RestaurantDAOJdbcImpl();
		} catch (DALException e) {
			throw new BLLException("Echec de la connexion", e);
		}
	}
	
	public List<Restaurant> selectAll() throws BLLException {
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation des restaurants", e);
		}
	}
	
	public Restaurant selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation du restaurant d'id " + id, e);
		}
	}
	
	public Restaurant insert(String nom, String adresse, String description) throws BLLException {
		
		BLLException blleException = new BLLException();
		if (nom.length() < 2) {
			blleException.ajouterErreur("Le nom doit faire au moins 2 caractères");
		}
		
		if (nom.length() > 30) {
			blleException.ajouterErreur("Le nom doit faire au maximum 30 caractères");
		}
		
		if (adresse.length() < 2) {
			blleException.ajouterErreur("L'adresse doit faire au moins 2 caractères");
		}
		
		if (adresse.length() > 150) {
			blleException.ajouterErreur("L'adresse doit faire au maximum 150 caractères");
		}
		
		if (description.length() < 2) {
			blleException.ajouterErreur("La description doit faire au moins 2 caractères");
		}
		
		if (description.length() > 300) {
			blleException.ajouterErreur("La description doit faire au maximum 300 caractères");
		}
		
		if (blleException.getErreurs().size() > 0) {
			throw blleException;
		}
		
		Restaurant restaurant = new Restaurant(nom, adresse, description);
		try {
			dao.insert(restaurant);
		} catch (DALException e) {
			throw new BLLException("Echec de l'insertion", e);
		}
		return restaurant;
	}
	
	public void update(Restaurant restaurant) throws BLLException {
		
		try {
			dao.update(restaurant);
		} catch (DALException e) {
			throw new BLLException("Echec de la mise a jour", e);
		}
	}
	
	public void delete(int id) throws BLLException {
		try {
			dao.delete(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la suppression", e);
		}
	}
}
