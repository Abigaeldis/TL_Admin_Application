package bll;

import java.time.LocalTime;
import java.util.List;
import java.util.regex.Pattern;

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

		Horaire horaire = new Horaire(jour, heureDeDebut, heureDeFin, creneau, restaurant);
		try {
			validateHoraire(jour, heureDeDebut, heureDeFin, creneau, restaurant);
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

	private void validateHoraire(String jour, LocalTime heureDeDebut, LocalTime heureDeFin, String creneau, Restaurant restaurant) throws BLLException {
		// Validate jour using regex (case-insensitive)
		if (jour == null || !Pattern.matches("^(?i)(LUNDI|MARDI|MERCREDI|JEUDI|VENDREDI|SAMEDI|DIMANCHE)$", jour)) {
			throw new BLLException("Le jour doit être un jour de la semaine (LUNDI, MARDI, MERCREDI, JEUDI, VENDREDI, SAMEDI, DIMANCHE)");
		}

		if (creneau==null||!Pattern.matches("^(?i)(MIDI|SOIR)$", creneau)) {
			throw new BLLException("Le creneau doit être MIDI ou SOIR");
		}


	}
}
