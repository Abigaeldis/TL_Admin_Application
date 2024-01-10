package bo;

public class Table {
	private int id;
	private String nom;
	private String prenom;
	private String mail;
	private String motdepasse;
	private String telephone;
	private String adresse;
	private String role;
	private int idRestaurant;
	
	public Table() {
	}
	
	public Table(String nom, String prenom, String mail, String motdepasse, String telephone, String adresse,
			String role, int idRestaurant) {
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.motdepasse = motdepasse;
		this.telephone = telephone;
		this.adresse = adresse;
		this.role = role;
		this.idRestaurant = idRestaurant;
	}

	public Table(int id, String nom, String prenom, String mail, String motdepasse, String telephone, String adresse,
			String role, int idRestaurant) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.motdepasse = motdepasse;
		this.telephone = telephone;
		this.adresse = adresse;
		this.role = role;
		this.idRestaurant = idRestaurant;
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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMotdepasse() {
		return motdepasse;
	}

	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(int idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	@Override
	public String toString() {
		return "Table [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", motdepasse="
				+ motdepasse + ", telephone=" + telephone + ", adresse=" + adresse + ", role=" + role
				+ ", id_restaurant=" + idRestaurant + "]";
	}
}
