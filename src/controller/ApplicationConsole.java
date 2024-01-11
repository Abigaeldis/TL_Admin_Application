package controller;
import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.CarteBLL;
import bll.PlatBLL;
import bll.RestaurantBLL;
import bll.TableBLL;
import bo.Carte;
import bo.Plat;
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
				System.out.println("Veuillez choisir l'action à réaliser");
				System.out.println("1. Saisie de carte manuelle");
				System.out.println("2. Saisie de carte automatique");
				System.out.println("3. Retour au menu");
				int decision_carte = scan.nextInt();
				scan.nextLine();
				if (decision_carte == 1) {
					creerCarteManuel();;
				}
				else if (decision_carte == 2) {
					creerCarteAuto();
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

	private static void creerCarteAuto() {}

	private static void creerCarteManuel() {
		System.out.println("Vous avez choisi de créer une carte manuellement.");
		Carte carteNouvelle = null;
		System.out.println("Veuillez saisir le nom de la nouvelle carte.");
		String nomEntree = scan.nextLine();
		try {
			carteNouvelle = carteBll.insert(nomEntree);

			List<Plat> plats;
			try {
				plats = platBll.selectAll();
				boolean continuer = true;
				do{
					System.out.println("Voulez-vous ajouter des entrées à votre carte ?");
					System.out.println("1. Oui");
					System.out.println("2. Non");
					int saisieUtilisateur = scan.nextInt();
					scan.nextLine();
					if (saisieUtilisateur == 1) {
						ajouterEntree(plats, carteNouvelle);
					}
					else {
						continuer = false;
					}
				} while(continuer);
			} catch (BLLException e) {
				e.printStackTrace();
			}
			
			System.out.println("A combien de restaurant voulez-vous affectez cette carte ?");
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
						restaurant.setCarte(carteNouvelle);
						restaurantBll.update(restaurant);
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
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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

	private static void ajouterEntree(List<Plat> plats, Carte carteNouvelle) throws BLLException {
		System.out.println("Quelle entrée souhaitez-vous ajouter à cette carte  ?");
		System.out.println("\t0 : Je souhaite créer une nouvelle entrée.");
		int idCarte = carteNouvelle.getId();
		for (Plat current : plats) {
			if (current.getType().equals("entrée")) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		}
		int saisieEntree= scan.nextInt();
		scan.nextLine();
		if (saisieEntree==0) {
			System.out.println("Vous avez choisi d'ajouter une entrée");
			
			System.out.println("Veuillez saisir son nom");
			String nomEntree = scan.nextLine();
			
			System.out.println("Veuillez saisir la description");
			String descriptionEntree = scan.nextLine();
			
			System.out.println("Veuillez saisir le prix (ex. 12,5)");
			Float prix = scan.nextFloat();
			scan.nextLine();

			Plat entreeAjoute = platBll.insert(nomEntree, descriptionEntree, prix,"entrée",carteNouvelle);
			System.out.println("L'entrée suivante a été ajoutée : " + entreeAjoute);
			
		} else {
			Plat entreeADupliquer = platBll.selectById(saisieEntree);
			System.out.println(entreeADupliquer.getNom());
			System.out.println(entreeADupliquer.getDescription());
			System.out.println(entreeADupliquer.getPrix());
			Plat entreeAjoute = platBll.insert(entreeADupliquer.getNom(), entreeADupliquer.getDescription(), entreeADupliquer.getPrix(),"entrée",carteNouvelle);			
			System.out.println("L'entrée suivante a été ajoutée : " + entreeAjoute);
		}
	}
}
