package bo;

import java.time.LocalDate;

public class Composant {
	private int id;
	private String nom;
	private String nature;
	private LocalDate dateSortie;
	
	public Composant() {
	}

	public Composant(String nom, String nature, LocalDate dateSortie) {
		this.nom = nom;
		this.nature = nature;
		this.dateSortie = dateSortie;
	}

	public Composant(int id, String nom, String nature, LocalDate dateSortie) {
		this.id = id;
		this.nom = nom;
		this.nature = nature;
		this.dateSortie = dateSortie;
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

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public LocalDate getDateSortie() {
		return dateSortie;
	}

	public void setDateSortie(LocalDate dateSortie) {
		this.dateSortie = dateSortie;
	}

	@Override
	public String toString() {
		return "Composant [id=" + id + ", nom=" + nom + ", nature=" + nature + ", dateSortie=" + dateSortie + "]";
	}
}
