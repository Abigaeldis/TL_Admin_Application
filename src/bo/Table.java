package bo;

public class Table {
	private int id;
	private int numTable;
	private int capaciteTable;
	private String etat;
	private int idRestaurant;
		
	public Table() {
		super();
	}

	public Table(int numTable, int capaciteTable, String etat, int idRestaurant) {
		this.numTable = numTable;
		this.capaciteTable = capaciteTable;
		this.etat = etat;
		this.idRestaurant = idRestaurant;
	}

	public Table(int id, int numTable, int capaciteTable, String etat, int idRestaurant) {
		this.id = id;
		this.numTable = numTable;
		this.capaciteTable = capaciteTable;
		this.etat = etat;
		this.idRestaurant = idRestaurant;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumTable() {
		return numTable;
	}

	public void setNumTable(int numTable) {
		this.numTable = numTable;
	}

	public int getCapaciteTable() {
		return capaciteTable;
	}

	public void setCapaciteTable(int capaciteTable) {
		this.capaciteTable = capaciteTable;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public int getIdRestaurant() {
		return idRestaurant;
	}

	public void setIdRestaurant(int idRestaurant) {
		this.idRestaurant = idRestaurant;
	}

	@Override
	public String toString() {
		return "Table [id=" + id + ", numTable=" + numTable + ", capaciteTable=" + capaciteTable + ", etat=" + etat
				+ ", idRestaurant=" + idRestaurant + "]";
	}
	
	
}
