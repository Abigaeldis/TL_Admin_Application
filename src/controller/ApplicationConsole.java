package controller;
import java.util.ArrayList;
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
			int carteSelectionne = scan.nextInt();
			scan.nextLine();

			carte = carteBll.selectById(carteSelectionne);

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

	private static void creerRestaurantAuto() {}

	private static void creerCarteManuel() {
		System.out.println("Vous avez choisi de créer une carte manuellement.");
		Carte carteNouvelle = null;
		System.out.println("Veuillez saisir le nom de la nouvelle carte.");
		String nomEntree = scan.nextLine();
		try {
			carteNouvelle = carteBll.insert(nomEntree);
			List<Plat> plats;
			try {
				boolean continuer = true;
				plats = platBll.selectAll();
				do{
					System.out.println("Voulez-vous ajouter des entrées à votre carte ?");
					System.out.println("1. Oui");
					System.out.println("2. Non");
					int saisieUtilisateur = scan.nextInt();
					scan.nextLine();
					if (saisieUtilisateur == 1) {
						System.out.println("Quelle entrée souhaitez-vous ajouter à cette carte  ?");
						System.out.println("\t0 : Je souhaite créer une nouvelle entrée.");
						ajouterPlat(plats, carteNouvelle, "entrée");
					}
					else {
						continuer = false;
					}
				} while(continuer);

				continuer = true;
				do{
					System.out.println("Voulez-vous ajouter des plats à votre carte ?");
					System.out.println("1. Oui");
					System.out.println("2. Non");
					int saisieUtilisateur = scan.nextInt();
					scan.nextLine();
					if (saisieUtilisateur == 1) {
						System.out.println("Quel plat souhaitez-vous ajouter à cette carte  ?");
						System.out.println("\t0 : Je souhaite créer un nouveau plat.");
						ajouterPlat(plats, carteNouvelle, "plat");
					}
					else {
						continuer = false;
					}
				} while(continuer);

				continuer = true;
				do{
					System.out.println("Voulez-vous ajouter des desserts à votre carte ?");
					System.out.println("1. Oui");
					System.out.println("2. Non");
					int saisieUtilisateur = scan.nextInt();
					scan.nextLine();
					if (saisieUtilisateur == 1) {
						System.out.println("Quel dessert souhaitez-vous ajouter à cette carte  ?");
						System.out.println("\t0 : Je souhaite créer un nouveau dessert.");
						ajouterPlat(plats, carteNouvelle, "dessert");
					}
					else {
						continuer = false;
					}
				} while(continuer);

				continuer = true;
				do{
					System.out.println("Voulez-vous ajouter des boissons à votre carte ?");
					System.out.println("1. Oui");
					System.out.println("2. Non");
					int saisieUtilisateur = scan.nextInt();
					scan.nextLine();
					if (saisieUtilisateur == 1) {
						System.out.println("Quelle boisson souhaitez-vous ajouter à cette carte  ?");
						System.out.println("\t0 : Je souhaite créer une nouvelle boisson.");
						ajouterPlat(plats, carteNouvelle, "boisson");
					}
					else {
						continuer = false;
					}
				} while(continuer);
			} catch (BLLException e) {
				e.printStackTrace();
			}
			
			System.out.println("Vous avez créer la carte suivante :");
			afficherCarte(carteNouvelle);
			
			affecterCarteRestaurant(carteNouvelle);

		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	private static void creerCarteAuto() {}

	private static void affecterCarteRestaurant(Carte carteNouvelle) {
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
	}

	private static void ajouterPlat(List<Plat> plats, Carte carteNouvelle, String typePlat) throws BLLException {
		for (Plat current : plats) {
			if (current.getType().equals(typePlat)) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		}
		int saisiePlat= scan.nextInt();
		scan.nextLine();
		if (saisiePlat==0) {
			System.out.println("Vous avez choisi la création d'un nouvel item de type " + typePlat);

			System.out.println("Veuillez saisir son nom");
			String nomPlat = scan.nextLine();

			System.out.println("Veuillez saisir sa description");
			String descriptionPlat = scan.nextLine();

			System.out.println("Veuillez saisir son prix (ex. 12,5)");
			Float prix = scan.nextFloat();
			scan.nextLine();

			Plat platAjoute = platBll.insert(nomPlat, descriptionPlat, prix,typePlat,carteNouvelle);
			System.out.println("L'item suivant a été ajouté : " + platAjoute);

		} else {
			Plat platADupliquer = platBll.selectById(saisiePlat);
			Plat platAjoute = platBll.insert(platADupliquer.getNom(), platADupliquer.getDescription(), platADupliquer.getPrix(),typePlat,carteNouvelle);			
			System.out.println("L'item suivant a été ajoutée : " + platAjoute);
		}
	}
	
	private static void afficherCarte(Carte carteNouvelle) {
		System.out.println("Carte n°"+carteNouvelle.getId() + " : " + carteNouvelle.getNom());
		List<Plat> items;
		List<Plat> entrees = new ArrayList<>();;
		List<Plat> plats = new ArrayList<>();;
		List<Plat> desserts = new ArrayList<>();;
		List<Plat> boissons = new ArrayList<>();;
		try {
			items = platBll.selectAll();
			for (Plat current : items) {
				if (current.getCarte().getId()==carteNouvelle.getId()) {
					switch (current.getType()) {
					case "entrée" :
						entrees.add(current);
						break;
					case "plat" :
						plats.add(current);
						break;
					case "dessert" :
						desserts.add(current);
						break;
					case "boisson" :
						boissons.add(current);
						break;
					default :
						System.out.println(current);
						break;
					}
				}
			}
			System.out.println("Entrées :");
			for (Plat current : entrees) {
				System.out.println(current);
			}
			System.out.println("Plats :");
			for (Plat current : plats) {
				System.out.println(current);
			}
			System.out.println("Desserts :");
			for (Plat current : desserts) {
				System.out.println(current);
			}
			System.out.println("Boissons :");
			for (Plat current : boissons) {
				System.out.println(current);
			}
			
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
}
