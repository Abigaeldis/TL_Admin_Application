package bo;

public class Plat {
	private int id;
	private String nom;
	private String description;
	private Float prix;
	private String type;
	private Carte carte;
	
	public Plat() {
	}

	public Plat(String nom, String description, Float prix, String type, Carte carte) {
		this.nom = nom;
		this.description = description;
		this.prix = prix;
		this.type = type;
		this.carte = carte;
	}

	public Plat(int id,String nom, String description, Float prix, String type, Carte carte) {
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.prix = prix;
		this.type = type;
		this.carte = carte;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrix() {
		return prix;
	}

	public void setPrix(Float prix) {
		this.prix = prix;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Carte getCarte() {
		return carte;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	@Override
	public String toString() {
		return "Plat [id=" + id + ", nom=" + nom + ", description=" + description + ", prix=" + prix + ", type=" + type
				+ ", carte=" + carte + "]";
	}

	

	
}
