package bo;

public class Restaurant {
	private int id;
	private String nom;
	private String adresse;
	private String description;
	private Carte carte;
	
	/*
	 * Constructeurs
	 */
	
	public Restaurant() {
	}

	public Restaurant(int id, String nom, String adresse, String description) {
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.description = description;
	}
	
	public Restaurant(String nom, String adresse, String description, Carte carte) {
		this.nom = nom;
		this.adresse = adresse;
		this.description = description;
		this.carte = carte;
	}

	/*
	 * Getters et Setters
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", nom=" + nom + ", adresse=" + adresse + ", description=" + description
				+ ", carte=" + carte + "]";
	}
	
	
	
}
