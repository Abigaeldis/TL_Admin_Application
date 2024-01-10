package bo;

public class Carte {
	private int id;
	private String nom;
	private Restaurant restaurant;
	
	/*
	 * Constructeurs
	 */
	
	public Carte() {
	}

	public Carte(int id, String nom, Restaurant restaurant) {
		this.id = id;
		this.nom = nom;
		this.restaurant = restaurant;
	}
	
	public Carte(String nom, Restaurant restaurant) {
		this.nom = nom;
		this.restaurant = restaurant;
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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return "Carte [id=" + id + ", nom=" + nom + ", restaurant=" + restaurant + "]";
	}
	
	
	
	

	
	
	
}
