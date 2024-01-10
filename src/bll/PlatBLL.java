package bll;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
	
	public Plat insert(String nom, String description, Float prix, String type, int idCarte) throws BLLException {
		
		BLLException blleException = new BLLException();
//		if (nom.length() < 2) {
//			blleException.ajouterErreur("Le nom doit faire au moins 2 caractères");
//		}
//		
//		if (nom.length() > 50) {
//			blleException.ajouterErreur("Le nom doit faire maximum 50 caractères");
//		}
//		
//		List<String> valeursValides = Arrays.asList("RAM", "DD", "GPU", "CPU", "ALIM");
//		if (!valeursValides.contains(nature)) {
//			blleException.ajouterErreur("La nature du plat doit valoir RAM, DD, CPU, GPU ou ALIM");
//		}
//		
//		if (dateSortie.isBefore(LocalDate.EPOCH)) {
//			blleException.ajouterErreur("La date de sortie doit être postérieure au 01/01/1970");
//		}
//		
//		if (dateSortie.isAfter(LocalDate.now())) {
//			blleException.ajouterErreur("La date de sortie doit être antérieure à la date du jour");
//		}
//		
//		if (blleException.getErreurs().size() > 0) {
//			throw blleException;
//		}
		
		Plat plat = new Plat(nom, description, prix, type, idCarte);
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
