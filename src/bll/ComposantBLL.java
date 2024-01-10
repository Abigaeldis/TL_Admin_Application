package bll;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import bo.Composant;
import dal.GenericDAO;
import dal.ComposantDAOJdbcImpl;
import dal.DALException;

public class ComposantBLL {
	private GenericDAO<Composant> dao;
	
	public ComposantBLL() throws BLLException {
		try {
			dao = new ComposantDAOJdbcImpl();
		} catch (DALException e) {
			throw new BLLException("Echec de la connexion", e);
		}
	}
	
	public List<Composant> selectAll() throws BLLException {
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation des composants", e);
		}
	}
	
	public Composant selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation du composant d'id " + id, e);
		}
	}
	
	public Composant insert(String nom, String nature, LocalDate dateSortie) throws BLLException {
		
		BLLException blleException = new BLLException();
		if (nom.length() < 2) {
			blleException.ajouterErreur("Le nom doit faire au moins 2 caractères");
		}
		
		if (nom.length() > 50) {
			blleException.ajouterErreur("Le nom doit faire maximum 50 caractères");
		}
		
		List<String> valeursValides = Arrays.asList("RAM", "DD", "GPU", "CPU", "ALIM");
		if (!valeursValides.contains(nature)) {
			blleException.ajouterErreur("La nature du composant doit valoir RAM, DD, CPU, GPU ou ALIM");
		}
		
		if (dateSortie.isBefore(LocalDate.EPOCH)) {
			blleException.ajouterErreur("La date de sortie doit être postérieure au 01/01/1970");
		}
		
		if (dateSortie.isAfter(LocalDate.now())) {
			blleException.ajouterErreur("La date de sortie doit être antérieure à la date du jour");
		}
		
		if (blleException.getErreurs().size() > 0) {
			throw blleException;
		}
		
		Composant composant = new Composant(nom, nature, dateSortie);
		try {
			dao.insert(composant);
		} catch (DALException e) {
			throw new BLLException("Echec de l'insertion", e);
		}
		return composant;
	}
	
	public void update(Composant composant) throws BLLException {
		
		try {
			dao.update(composant);
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
