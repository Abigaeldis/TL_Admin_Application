package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.ComposantBLL;
import bo.Composant;

public class TestComposants {
	private static Scanner scan;
	private static ComposantBLL bll;
	
	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre application de gestion des composants");
		scan = new Scanner(System.in);
		try {
			bll = new ComposantBLL();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Veuillez choisir l'action à réaliser");
		
		int choix;
		do {
			choix = afficherMenu();
			
			switch (choix) {
			case 1:
				creerComposant();
				break;
			case 2:
				listerComposant();
				break;
			case 3:
				supprimerComposant();
				break;
			case 4:
				System.out.println("Byebye");
				break;
			default:
				System.out.println("Saisie non valide");
				break;
			}
		} while (choix != 4);
		
		
		scan.close();
		
	}
	
	private static void supprimerComposant() {
		try {
			List<Composant> composants = bll.selectAll();
			if (composants.size() == 0) {
				System.out.println("Il n'existe aucun composant en base");
				return;
			}
		} catch (BLLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Vous avez choisi de supprimer un composant");
		System.out.println("Veuillez saisir l'id du composant à supprimer");
		int id = scan.nextInt();
		scan.nextLine();
		
		try {
			bll.delete(id);
			System.out.println("Le composant a bien été supprimé");
		} catch (BLLException e) {
			System.out.println("L'id saisi n'existe pas en base de données");
		}
	}

	private static void listerComposant() {
		try {
			List<Composant> composants = bll.selectAll();
			for (Composant current : composants) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private static void creerComposant() {
		System.out.println("Vous avez choisi d'ajouter un composant");
		
		System.out.println("Veuillez saisir son nom");
		String nom = scan.nextLine();
		
		System.out.println("Veuillez saisir la nature du composant (RAM, DD, CPU, GPU, ALIM)");
		String nature = scan.nextLine();
		
		System.out.println("Veuillez saisir la date de sortie du composant (jj/mm/aaaa)");
		String dateSortieStr = scan.nextLine();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dateSortie = LocalDate.parse(dateSortieStr, dtf);
		
		try {
			Composant composantAjoute = bll.insert(nom, nature, dateSortie);
			System.out.println("Composant ajouté " + composantAjoute);
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue :");
			for (String erreur : e.getErreurs()) {
				System.out.println("\t" + erreur);
			}
			e.printStackTrace();
		}
		
	}
	
	private static int afficherMenu() {
		System.out.println("1. Créer un composant");
		System.out.println("2. Consulter les composants");
		System.out.println("3. Supprimer un composant");
		System.out.println("4. Quitter");
		int choix = scan.nextInt();
		scan.nextLine();
		return choix;
	}
}
