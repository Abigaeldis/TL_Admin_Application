package controller;
import java.util.Scanner;

import bll.BLLException;
import bll.CarteBLL;
import bll.PlatBLL;
import bll.RestaurantBLL;
import bll.TableBLL;
import bo.Carte;
import bo.Restaurant;

public class ApplicationConsole {
	private static Scanner scan;
	private static TableBLL tableBll;
	private static RestaurantBLL restaurantBll;
	private static PlatBLL platBll;
	private static CarteBLL carteBll;

	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre application d'administration.\n");
		scan = new Scanner(System.in);
		try {
			tableBll = new TableBLL();
			restaurantBll = new RestaurantBLL();
			platBll = new PlatBLL();
			carteBll = new CarteBLL();

		} catch (BLLException e) {
			e.printStackTrace();
		}

		System.out.println("Veuillez choisir l'action à réaliser");

		int choix;
		do {
			choix = afficherMenu();

			switch (choix) {
			case 1: 
				System.out.println("Veuillez choisir l'action à réaliser");
				System.out.println("1.Saisie de restaurant manuelle");
				System.out.println("2.Saisie de restaurant automatique");
				int decision = scan.nextInt();
				scan.nextLine();
				if (decision == 1) {
					creerRestaurantManuel();
				}
				else if (decision == 2) {
					creerRestaurantAuto();
				}
				
				//Ajouter un restaurant
				break;
			case 2:
				//Modifier un restaurant existant
				break;
			case 3:
				//Supprimer un restaurant existant
				break;
			case 4:
				//Créer une carte
				break;
			case 5:
				//Modifier une carte
				break;
			case 6 :
				System.out.println("Vous avez quitté l'application.");
				break;
			default:
				System.out.println("Veuillez saisir un chiffre entre 1 et 6.");
				break;
			}
		} while (choix != 6);


		scan.close();

	}

	private static int afficherMenu() {
		System.out.println("1. Ajouter un restaurant");
		System.out.println("2. Modifier un restaurant existant");
		System.out.println("3. Supprimer un restaurant existant");
		System.out.println("4. Créer une carte");
		System.out.println("5. Modifier une carte");
		System.out.println("6. Quitter l'application");
		int choix = scan.nextInt();
		scan.nextLine();
		return choix;
	}

	private static void creerRestaurantManuel() {
		Carte carte = null;
		try {
		System.out.println("Vous avez choisi d'ajouter un restaurant");

		System.out.println("Veuillez saisir le nom du restaurant");
		String nom = scan.nextLine();

		System.out.println("Veuillez saisir l'adresse du restaurant");
		String adresse = scan.nextLine();

		System.out.println("Veuillez saisir une description pour votre restaurant");
		String description = scan.nextLine();
		System.out.println("Qu'elle carte voulez vous attribuer au restaurant");
		System.out.println("Liste des cartes");
		int carteSelectionner = scan.nextInt();
		scan.nextLine();
		
		carte = carteBll.selectById(carteSelectionner);
		
		
			Restaurant restaurantAjoute = restaurantBll.insert(nom, adresse, description,carte);
			System.out.println("Restaurant ajouté avec succès " + restaurantAjoute);
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue :");
			for (String erreur : e.getErreurs()) {
				System.out.println("\t" + erreur);
			}
			e.printStackTrace();
		}
	}
	
	private static void creerRestaurantAuto() {
		
	}
}
