package bll;

import java.util.List;

import bo.Carte;
import bo.Plat;
import dal.DALException;
import dal.GenericDAO;
import dal.PlatDAOJdbcImpl;

public class PlatBLL {
	private GenericDAO<Plat> dao;
	
	public PlatBLL() throws BLLException {
		try {
			dao = new PlatDAOJdbcImpl();
		} catch (DALException e) {
			throw new BLLException("Echec de la connexion", e);
		}
	}
	
	public List<Plat> selectAll() throws BLLException {
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation des plats", e);
		}
	}
	
	public Plat selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation du plat d'id " + id, e);
		}
	}
	
	public Plat insert(String nom, String description, Float prix, String type, Carte carte) throws BLLException {
		
		BLLException blleException = new BLLException();
		
		if (nom.length() < 2) {
			blleException.ajouterErreur("Le nom du plat doit faire au moins 2 caractères");
		}
		
		if (nom.length() > 30) {
			blleException.ajouterErreur("Le nom du plat doit faire au maximum 30 caractères");
		}
		
		if (description.length() < 2) {
			blleException.ajouterErreur("La description du plat doit faire au moins 2 caractères");
		}
		
		if (description.length() > 150) {
			blleException.ajouterErreur("La description du plat doit faire au maximum 150 caractères");
		}
		
		if (type.length() < 2) {
			blleException.ajouterErreur("La type doit du plat faire au moins 2 caractères");
		}
		
		if (type.length() > 15) {
			blleException.ajouterErreur("La type du plat doit faire au maximum 15 caractères");
		}
		
		if (blleException.getErreurs().size() > 0) {
			throw blleException;
		}
		
		Plat plat = new Plat(nom, description, prix, type, carte);
		
		try {
			dao.insert(plat);
		} catch (DALException e) {
			throw new BLLException("Echec de l'insertion", e);
		}
		return plat;
	}
	
	public void update(Plat plat) throws BLLException {
		
		try {
			dao.update(plat);
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
