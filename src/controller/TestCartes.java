package controller;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.CarteBLL;
import bll.RestaurantBLL;
import bo.Carte;
import bo.Restaurant;

public class TestCartes {
	private static Scanner scan;
	private static CarteBLL carteBll;
	private static RestaurantBLL restaurantBll;
	
	
	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre application de gestion des cartes");
		scan = new Scanner(System.in);
		try {
			carteBll = new CarteBLL();
			restaurantBll = new RestaurantBLL();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Veuillez choisir l'action à réaliser");
		
		int choix;
		do {
			choix = afficherMenu();
			
			switch (choix) {
			case 1:
				creerCarte();
				break;
			case 2:
				listerCartes();
				break;
			case 3:
				supprimerCarte();
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
	
	private static void supprimerCarte() {
		try {
			List<Carte> cartes = carteBll.selectAll();
			if (cartes.size() == 0) {
				System.out.println("Il n'existe aucune carte pour le moment");
				return;
			}
		} catch (BLLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Vous avez choisi de supprimer une carte");
		System.out.println("Veuillez saisir l'id de la carte à supprimer");
		int id = scan.nextInt();
		scan.nextLine();
		
		try {
			carteBll.delete(id);
			System.out.println("La carte a bien été supprimée");
		} catch (BLLException e) {
			System.out.println("L'id saisi n'existe pas en base de données");
		}
	}

	private static void listerCartes() {
		try {
			List<Carte> cartes = carteBll.selectAll();
			for (Carte current : cartes) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private static void creerCarte() {
		System.out.println("Vous avez choisi d'ajouter une carte");
		
		System.out.println("Veuillez saisir le nom de votre nouvelle carte");
		String nom = scan.nextLine();
		
		System.out.println("Veuillez saisir le restaurant auquel ajouter la carte");
		int idRestaurant = scan.nextInt();
		
		try {
			Restaurant restaurant = restaurantBll.selectById(idRestaurant);
			Carte carteAjoutee = carteBll.insert(nom, restaurant);
			System.out.println("Carte ajoutée avec succès " + carteAjoutee);
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue :");
			for (String erreur : e.getErreurs()) {
				System.out.println("\t" + erreur);
			}
			e.printStackTrace();
		}
		
	}
	
	private static int afficherMenu() {
		System.out.println("1. Créer une carte");
		System.out.println("2. Consulter les cartes");
		System.out.println("3. Supprimer une carte");
		System.out.println("4. Quitter");
		int choix = scan.nextInt();
		scan.nextLine();
		return choix;
	}
}
