package bo;

public class Restaurant {
	private int id;
	private String nom;
	private String adresse;
	private String description;
	
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
	
	public Restaurant(String nom, String adresse, String description) {
		this.nom = nom;
		this.adresse = adresse;
		this.description = description;
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
	
	/*
	 * toString
	 */
	
	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", nom=" + nom + ", adresse=" + adresse + ", description=" + description + "]";
	}
	
	
}
