package controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.HoraireBLL;
import bo.Horaire;

public class TestHoraire {
	private static Scanner scan;
	private static HoraireBLL bll;
	
	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre application de gestion des horaires");
		scan = new Scanner(System.in);
		try {
			bll = new HoraireBLL();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Veuillez choisir l'action à réaliser");
		
		int choix;
		do {
			choix = afficherMenu();
			
			switch (choix) {
			case 1:
				creerHoraire();
				break;
			case 2:
				listerHoraire();
				break;
			case 3:
				supprimerHoraire();
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
	
	private static void supprimerHoraire() {
		try {
			List<Horaire> horaires = bll.selectAll();
			if (horaires.size() == 0) {
				System.out.println("Il n'existe aucun horaire en base");
				return;
			}
		} catch (BLLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Vous avez choisi de supprimer un horaire");
		System.out.println("Veuillez saisir l'id du horaire à supprimer");
		int id = scan.nextInt();
		scan.nextLine();
		
		try {
			bll.delete(id);
			System.out.println("Le horaire a bien été supprimé");
		} catch (BLLException e) {
			System.out.println("L'id saisi n'existe pas en base de données");
		}
	}

	private static void listerHoraire() {
		try {
			List<Horaire> horaires = bll.selectAll();
			for (Horaire current : horaires) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private static void creerHoraire() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		
		System.out.println("Vous avez choisi d'ajouter un horaire");
		
		System.out.println("Veuillez saisir un jour");
		String jour = scan.nextLine();
		
		System.out.println("Veuillez saisir l'heure de debut de service");
		String inputHeureDeDebut = scan.nextLine();
		scan.nextLine();
		LocalTime heureDeDebut = LocalTime.parse(inputHeureDeDebut, formatter);
		
		System.out.println("Veuillez saisir l'heure de fin de service");
		String inputHeureDeFin = scan.nextLine();
		scan.nextLine();
		LocalTime heureDeFin = LocalTime.parse(inputHeureDeFin, formatter);
		
		System.out.println("Veuillez saisir le creneau");
		String creneau = scan.nextLine();
		
		System.out.println("Veuillez saisir l'id du restaurant");
		int idRestaurant = scan.nextInt();
		
		try {
			Horaire horaireAjoute = bll.insert(jour, heureDeDebut, heureDeFin,creneau,idRestaurant);
			System.out.println("Horaire ajouté " + horaireAjoute);
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue :");
			for (String erreur : e.getErreurs()) {
				System.out.println("\t" + erreur);
			}
			e.printStackTrace();
		}
		
	}
	
	private static int afficherMenu() {
		System.out.println("1. Créer un horaire");
		System.out.println("2. Consulter les horaires");
		System.out.println("3. Supprimer un horaire");
		System.out.println("4. Quitter");
		int choix = scan.nextInt();
		scan.nextLine();
		return choix;
	}
}
