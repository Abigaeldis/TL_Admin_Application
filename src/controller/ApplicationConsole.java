package controller;
import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.PlatBLL;
import bll.RestaurantBLL;
import bll.TableBLL;
import bo.Restaurant;
import dal.DALException;

public class ApplicationConsole {
	private static Scanner scan;
	private static TableBLL tableBll;
	private static RestaurantBLL restaurantBll;
	private static PlatBLL platBll;

	public static void main(String[] args) throws BLLException {
		System.out.println("Bienvenue dans notre application d'administration.\n");
		scan = new Scanner(System.in);
		try {
			tableBll = new TableBLL();
			restaurantBll = new RestaurantBLL();
			platBll = new PlatBLL();

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
				modifierRestaurant();
				break;
			case 3:
				supprimerRestaurant();
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
		System.out.println("Vous avez choisi d'ajouter un restaurant");

		System.out.println("Veuillez saisir le nom du restaurant");
		String nom = scan.nextLine();

		System.out.println("Veuillez saisir l'adresse du restaurant");
		String adresse = scan.nextLine();

		System.out.println("Veuillez saisir une description pour votre restaurant");
		String description = scan.nextLine();
<<<<<<< Updated upstream

		try {
			Restaurant restaurantAjoute = restaurantBll.insert(nom, adresse, description);
=======
		System.out.println("Quelle carte voulez vous attribuer au restaurant");
		System.out.println("Liste des cartes");
		int carteSelectionner = scan.nextInt();
		scan.nextLine();
		
		carte = carteBll.selectById(carteSelectionner);
			Restaurant restaurantAjoute = restaurantBll.insert(nom, adresse, description,carte);
>>>>>>> Stashed changes
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
	
	private static void modifierRestaurant() throws BLLException { 
		
		System.out.println("Vous avez choisi de modifier un restaurant existant");
		listerRestaurant();
		
		System.out.println("Veuillez sélectionner l'id du restaurant à modifier");
		//Demande l'id du restaurant et execute un selectById       
        int restaurantId = scan.nextInt();
        Restaurant restaurantAModifier = restaurantBll.selectById(restaurantId);
        scan.nextLine();
        try {
			restaurantBll.selectById(restaurantId);
		} catch (BLLException e) {
			throw new BLLException("Echec de la recuperation du restaurant d'id " + restaurantId, e);
		}

        // Demander à l'utilisateur le nouveau nom du restaurant
        System.out.print("Entrez le nouveau nom du restaurant : ");
        String nouveauNom = scan.nextLine();
        restaurantAModifier.setNom(nouveauNom);

        // Demander à l'utilisateur la nouvelle adresse du restaurant
        System.out.print("Entrez la nouvelle adresse du restaurant : ");
        String nouvelleAdresse = scan.nextLine();
        restaurantAModifier.setAdresse(nouvelleAdresse);

        // Demander à l'utilisateur la nouvelle description du restaurant
        System.out.print("Entrez la nouvelle description du restaurant : ");
        String nouvelleDescription = scan.nextLine();
        restaurantAModifier.setDescription(nouvelleDescription);
        
        // Demander à l'utilisateur la carte qu'il souhaite rattacher
        System.out.print("Entrez la nouvelle carte : ");
        int idCarte = scan.nextInt();
        scan.nextLine();
        restaurantAModifier.setCarte(carteBll.selectById(idCarte));

        
        // Update pour modifier le restaurant dans la base de données
        try {
             restaurantBll.update(restaurantAModifier);
            System.out.println("Restaurant mis à jour avec succès !");
        } catch (BLLException e) {
            System.err.println("Erreur lors de la mise à jour du restaurant : " + e.getMessage());
        }
	}
	
	private static void supprimerRestaurant() throws BLLException {
		System.out.println("Vous avez choisi de supprimer un restaurant");
		System.out.println("Veuillez saisir l'id du restaurant à supprimer");
		listerRestaurant();
		
		int restaurantId = scan.nextInt();
        scan.nextLine();
        try {
			restaurantBll.delete(restaurantId);
		} catch (BLLException e) {
			throw new BLLException("Echec de la suppression du restaurant d'id " + restaurantId, e);
		}
	}
	
	/*
	 * 
	 */
	private static void listerRestaurant() {
		try {
			List<Restaurant> restaurants = restaurantBll.selectAll();
			for (Restaurant current : restaurants) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
}
