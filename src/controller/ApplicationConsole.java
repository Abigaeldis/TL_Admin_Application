package controller;
import java.util.List;
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
				//Ajouter un restaurant
				break;
			case 2:
				//Modifier un restaurant existant
				break;
			case 3:
				//Supprimer un restaurant existant
				break;
			case 4:
				System.out.println("Vous avez choisi de créer une nouvelle carte.");
				System.out.println("Souhaitez-vous la créer de manière :");
				System.out.println("1. Manuelle");
				System.out.println("2. Automatique");
				System.out.println("3. Retour au menu principal");
				int choix_carte = scan.nextInt();
				scan.nextLine();
				
				switch (choix_carte) {
				case 1: 
					creerCarteManuel();
					break;
				case 2:
					//Ajouter une carte de manière automatique
					break;
				case 3:
					System.out.println("Retour au menu principal");
					break;
				default:
					System.out.println("Veuillez saisir un chiffre entre 1 et 6.");
					break;
				}
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

	private static void creerCarteManuel() {
		System.out.println("Vous avez choisi d'ajouter une carte");
		
		System.out.println("Veuillez saisir le nom de votre nouvelle carte");
		String nom = scan.nextLine();
		
		System.out.println("Voici vos restaurants : ");
		List<Restaurant> restaurants = null;
		try {
			restaurants = restaurantBll.selectAll();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		for (Restaurant current : restaurants) {
			System.out.println(current.getId() + " : " + current);
		}
		
		System.out.println("A combien de restaurants souhaitez-vous affecter la nouvelle carte ?");
		int nbAffectation = scan.nextInt();
		scan.nextLine();
		
		if (nbAffectation > 0) {
			System.out.println("Saisissez successivement les numéros des restaurants auxquels vous voulez affecter cette carte.");
			for (int i = 0; i < nbAffectation ; i++) {
				System.out.println("Saisie " + (int) (i+1) + " :");
				int idRestaurant = scan.nextInt();
				scan.nextLine();
				try {
					Restaurant restaurant = restaurantBll.selectById(idRestaurant);
					carteBll.insert(nom, restaurant);
					System.out.println("Carte ajoutée avec succès dans le restaurant " + restaurant.getId());
				} catch (BLLException e) {
					System.out.println("Une erreur est survenue :");
					for (String erreur : e.getErreurs()) {
						System.out.println("\t" + erreur);
					}
					e.printStackTrace();
				}
			}
		}
		
		
	}
}
