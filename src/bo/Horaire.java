package bo;

import java.time.LocalTime;

public class Horaire {
	private int id;
	private String jour;
	private LocalTime heureDeDebut;
	private LocalTime heureDeFin;
	private String creneau;
	private Restaurant restaurant;
	
	public Horaire() {
	}

	public Horaire(String jour, LocalTime heureDeDebut, LocalTime heureDeFin,String creneau,Restaurant restaurant) {
		this.jour = jour;
		this.heureDeDebut = heureDeDebut;
		this.heureDeFin = heureDeFin;
		this.creneau = creneau;
		this.restaurant = restaurant;
	}

	public Horaire(int id,String jour, LocalTime heureDeDebut, LocalTime heureDeFin,String creneau,Restaurant restaurant) {
		this.id = id;
		this.jour = jour;
		this.heureDeDebut = heureDeDebut;
		this.heureDeFin = heureDeFin;
		this.creneau = creneau;
		this.restaurant = restaurant;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public LocalTime getHeureDeDebut() {
		return heureDeDebut;
	}

	public void setHeureDeDebut(LocalTime heureDeDebut) {
		this.heureDeDebut = heureDeDebut;
	}

	public LocalTime getHeureDeFin() {
		return heureDeFin;
	}

	public void setHeureDeFin(LocalTime heureDeFin) {
		this.heureDeFin = heureDeFin;
	}

	public String getCreneau() {
		return creneau;
	}

	public void setCreneau(String creneau) {
		this.creneau = creneau;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return "Horaire [id=" + id + ", jour=" + jour + ", heureDeDebut=" + heureDeDebut + ", heureDeFin=" + heureDeFin
				+ ", creneau=" + creneau + ", restaurant=" + restaurant + "]";
	}

	

	
	

	
}
