package bll;

import java.time.LocalTime;
import java.util.List;

import bo.Horaire;
import bo.Restaurant;
import dal.DALException;
import dal.GenericDAO;
import dal.HoraireDAOJdbcImpl;

public class HoraireBLL {
	private GenericDAO<Horaire> dao;
	
	public HoraireBLL() throws BLLException {
		try {
			dao = new HoraireDAOJdbcImpl();
		} catch (DALException e) {
			throw new BLLException("Echec de la connexion", e);
		}
	}
	
	public List<Horaire> selectAll() throws BLLException {
		try {
			return dao.selectAll();
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation des plats", e);
		}
	}
	
	public Horaire selectById(int id) throws BLLException {
		try {
			return dao.selectById(id);
		} catch (DALException e) {
			throw new BLLException("Echec de la recuperation du plat d'id " + id, e);
		}
	}
	
	public Horaire insert(String jour, LocalTime heureDeDebut, LocalTime heureDeFin,String creneau,Restaurant restaurant) throws BLLException {
		
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
		
		Horaire horaire = new Horaire(jour, heureDeDebut, heureDeFin, creneau, restaurant);
		try {
			dao.insert(horaire);
		} catch (DALException e) {
			throw new BLLException("Echec de l'insertion", e);
		}
		return horaire;
	}
	
	public void update(Horaire plat) throws BLLException {
		
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
