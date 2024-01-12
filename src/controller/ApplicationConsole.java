package controller;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.CarteBLL;
import bll.HoraireBLL;
import bll.PlatBLL;
import bll.RestaurantBLL;
import bll.TableBLL;
import bo.Carte;
import bo.Horaire;
import bo.Plat;
import bo.Restaurant;
import bo.Table;


public class ApplicationConsole {
	private static Scanner scan;
	private static TableBLL tableBll;
	private static RestaurantBLL restaurantBll;
	private static PlatBLL platBll;
	private static CarteBLL carteBll;
	private static HoraireBLL horaireBll;

	public static void main(String[] args) throws BLLException {
		System.out.println("Bienvenue dans notre application d'administration.\n");
		scan = new Scanner(System.in);
		try {
			tableBll = new TableBLL();
			restaurantBll = new RestaurantBLL();
			platBll = new PlatBLL();
			carteBll = new CarteBLL();
			horaireBll = new HoraireBLL();

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
				break;
			case 2:
				modifierRestaurant();
				break;
			case 3:
				supprimerRestaurant();
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
				modifierCarte();
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
		System.out.println("\t 1. Ajouter un restaurant");
		System.out.println("\t 2. Modifier un restaurant existant");
		System.out.println("\t 3. Supprimer un restaurant existant");
		System.out.println("\t 4. Créer une carte");
		System.out.println("\t 5. Modifier une carte");
		System.out.println("\t 6. Quitter l'application");
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

			System.out.println("Quelle carte voulez-vous attribuer au restaurant");
			listeCartes();
			int carteSelectionner = scan.nextInt();
			scan.nextLine();

			carte = carteBll.selectById(carteSelectionner);

			// Create Restaurant
			Restaurant restaurantAjoute = restaurantBll.insert(nom, adresse, description, carte);

			// Create and Insert Horaire with associated Restaurant
			System.out.println("Veuillez ajouter des horaires à votre restaurant.");
			boolean continuer = true;
			do {
				System.out.println("Veuillez saisir un jour (ex. Lundi)");
				String jour = scan.nextLine();

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
				LocalTime heureDeDebut = null;
				LocalTime heureDeFin = null;
				boolean isValidInput = false;

				do {
					try {
						System.out.println("Veuillez saisir l'heure de début de service (ex. 12:30)");
						String inputHeureDeDebut = scan.nextLine();
						heureDeDebut = LocalTime.parse(inputHeureDeDebut, formatter);
						isValidInput = true; // Break the loop if parsing is successful
					} catch (Exception e) {
						System.out.println("Format d'heure incorrect. Veuillez saisir l'heure au format 24 heures (ex. 12:30) n'oubliez pas les :");
					}
				} while (!isValidInput);
				
				isValidInput = false;
				
				do {
					try {
						System.out.println("Veuillez saisir l'heure de fin de service (ex. 14:30)");
						String inputHeureDeFin = scan.nextLine();
						heureDeFin = LocalTime.parse(inputHeureDeFin, formatter);
						isValidInput = true;
						} catch (Exception e) {
							System.out.println("Format d'heure incorrect. Veuillez saisir l'heure au format 24 heures (ex. 12:30) n'oubliez pas les :");
						}
				} while (!isValidInput);

				System.out.println("Veuillez saisir le créneau (MIDI ou SOIR)");
				String creneau = scan.nextLine();

				Horaire horaireAjoute = horaireBll.insert(jour, heureDeDebut, heureDeFin, creneau, restaurantAjoute);
				System.out.println("Horaire du restaurant ajouté avec succès " + horaireAjoute);
				System.out.println("Voulez-vous ajouter un nouvel horaire pour ce restaurant ?");
				System.out.println("1. Oui");
				System.out.println("2. Non");
				int saisieChoix = scan.nextInt();
				scan.nextLine();
				if (saisieChoix == 2){
					continuer = false;
				}
			} while(continuer);

			System.out.println("Veuillez ajouter des tables à votre restaurant.");
			continuer = true;
			do {
				System.out.println("Veuillez saisir le numéro de table");
				int numTable = scan.nextInt();
				scan.nextLine();

				System.out.println("Veuillez saisir la capacité de la table");
				int capaciteTable = scan.nextInt();
				scan.nextLine();

				//		        System.out.println("Veuillez saisir l'état de la table"); // On met par défaut l'état Libre
				//		        String etat = scan.nextLine();

				Table tableAjoutee = tableBll.insert(numTable, capaciteTable, "Libre", restaurantAjoute);
				System.out.println("Table du restaurant ajoutée avec succès " + tableAjoutee);
				System.out.println("Voulez-vous ajouter une nouvelle table pour ce restaurant ?");
				System.out.println("1. Oui");
				System.out.println("2. Non");
				int saisieChoix = scan.nextInt();
				scan.nextLine();
				if (saisieChoix == 2){
					continuer = false;
				}
			} while(continuer);

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
		try {
			System.out.println("Vous avez choisi d'ajouter des restaurants automatiquement");

			System.out.println("Veuillez saisir le chemin du fichier restaurant");
			//	        String filePath = scan.nextLine();
			String filePath= "restaurant_data.csv";

			try (Scanner fileScanner = new Scanner(new File(filePath))) {
				// Skip the header line
				if (fileScanner.hasNext()) {
					fileScanner.nextLine();
				}

				while (fileScanner.hasNext()) {
					String line = fileScanner.nextLine();
					String[] restaurantInfo = line.split(",");

					// Assuming the array contains: [name, address, description, carteId]
					String nom = restaurantInfo[0];
					String addresse = restaurantInfo[1];
					String description = restaurantInfo[2];
					int carteId = Integer.parseInt(restaurantInfo[3].trim());


					Carte carte = carteBll.selectById(carteId);


					Restaurant restaurantAjoute = restaurantBll.insert(nom, addresse, description, carte);
					System.out.println("Restaurant ajouté avec succès: " + restaurantAjoute);
				}
			}
		} catch (FileNotFoundException | BLLException e) {
			System.out.println("Une erreur est survenue :");
			e.printStackTrace();
		}
	}

	private static void modifierRestaurant() throws BLLException {

		System.out.println("Vous avez choisi de modifier un restaurant existant");
		listerRestaurant();

		System.out.println("Veuillez sélectionner l'id du restaurant à modifier");
		//Demande l'id du restaurant et execute un selectById       
		int restaurantId = scan.nextInt();
		Restaurant restaurantAModifier = null;
		try {
			restaurantAModifier = restaurantBll.selectById(restaurantId);
			scan.nextLine();
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
		System.out.println();
		listeCartes();
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

	private static void supprimerRestaurant() {
		try {
			System.out.println("Vous avez choisi de supprimer un restaurant existant");
			listerRestaurant();

			System.out.println("Veuillez sélectionner l'id du restaurant à supprimer");
			int restaurantId = scan.nextInt();
			scan.nextLine();

			// Display the selected restaurant for confirmation
			try {
				Restaurant restaurantToDelete = restaurantBll.selectById(restaurantId);
				System.out.println("Vous avez choisi de supprimer le restaurant suivant :");
				System.out.println(restaurantToDelete);

				// Ask for confirmation
				System.out.println("Voulez-vous vraiment supprimer ce restaurant ? (1. Oui / 2. Non)");
				int confirmation = scan.nextInt();
				scan.nextLine();

				if (confirmation == 1) {
					// Call the BLL method to delete the restaurant
					restaurantBll.delete(restaurantId);
					System.out.println("Restaurant supprimé avec succès !");
				} else {
					System.out.println("Suppression annulée.");
				}

			} catch (BLLException e) {
				System.err.println("Erreur lors de la récupération du restaurant à supprimer : " + e.getMessage());
			}

		} catch (Exception e) {
			System.err.println("Erreur lors de la suppression du restaurant : " + e.getMessage());
			e.printStackTrace();
		}
	}



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

	private static void modifierCarte() {
		System.out.println("Vous avez choisi de modifier une carte existante");
		listeCartes();

		System.out.println("Veuillez sélectionner l'id de la carte à modifier");
		int carteId = scan.nextInt();
		scan.nextLine();

		try {
			Carte carte = carteBll.selectById(carteId);
			if (carte != null) {
				System.out.println("Vous avez choisi de modifier la carte suivante :");
				afficherCarte(carte);
				System.out.println("Que souhaitez-vous modifier sur cette carte ?");
				System.out.println("1. Modification du nom de la carte");
				System.out.println("2. Ajout d'un item à la carte");
				System.out.println("3. Modification d'un item de la carte");
				System.out.println("4. Suppression d'un item de la carte");
				int saisie_utilisateur = scan.nextInt();
				scan.nextLine();
				switch (saisie_utilisateur) {
				case 1 :
					System.out.print("Entrez le nouveau nom de la carte : ");
					String nouveauNomCarte = scan.nextLine();
					carte.setNom(nouveauNomCarte);
					carteBll.update(carte);
					System.out.println("Carte mise à jour avec succès !");
					break;
				case 2 : 
					ajouterItemACarte(carte);
					break;
				case 3 : 
					modifierPlat(carte);
					System.out.println("Plat modifié avec succès !");
					break;
				case 4 : 
					suppressionPlat(carte);
					System.out.println("Plat supprimé avec succès !");
					break;
				default:
					System.out.println("Veuillez saisir un nombre entre 1 et 3.");
					break;
				}
			} else {
				System.out.println("Aucune carte trouvée avec l'id " + carteId);
			}
		} catch (BLLException e) {
			System.err.println("Erreur lors de la mise à jour de la carte : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static void ajouterItemACarte(Carte carte) throws BLLException {
		for (Plat current : platBll.selectAll()) {
			if (current.getCarte().getId() == carte.getId()) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		}
		int saisiePlat= scan.nextInt();
		scan.nextLine();
		if (saisiePlat==0) {
			System.out.println("Vous avez choisi la création d'un nouvel item");

			System.out.println("Veuillez saisir son nom");
			String nomPlat = scan.nextLine();

			System.out.println("Veuillez saisir sa description");
			String descriptionPlat = scan.nextLine();

			System.out.println("Veuillez saisir son prix (ex. 12,5)");
			Float prix = scan.nextFloat();
			scan.nextLine();

			System.out.println("Veuillez saisir son type (entrée, plat, dessert ou boisson)");
			String typePlat = scan.nextLine();

			Plat platAjoute = platBll.insert(nomPlat, descriptionPlat, prix,typePlat,carte);
			System.out.println("L'item suivant a été ajouté : " + platAjoute);

		} else {
			Plat platADupliquer = platBll.selectById(saisiePlat);
			Plat platAjoute = platBll.insert(platADupliquer.getNom(), platADupliquer.getDescription(), platADupliquer.getPrix(),platADupliquer.getType(),carte);			
			System.out.println("L'item suivant a été ajoutée : " + platAjoute);
		}
		System.out.println("Plat ajouté avec succès !");
	}

	private static void listeCartes() {
		try {
			List<Carte> cartes = carteBll.selectAll();
			for (Carte current : cartes) {
				System.out.println("\t" + current.getId() + ". " + current.getNom());
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private static void creerCarteAuto() {
		try {
			System.out.println("Vous avez choisi d'ajouter une carte automatiquement.");

			System.out.println("Veuillez saisir le chemin du fichier carte");
			String filePath = scan.nextLine();

			try (Scanner fileScanner = new Scanner(new File(filePath))) {
				// Skip the header line
				if (fileScanner.hasNext()) {
					fileScanner.nextLine();
				}
				Carte carteAjoute = null;
				if (fileScanner.hasNext()) {
					String nomCarte = fileScanner.nextLine();
					carteAjoute = carteBll.insert(nomCarte);
					System.out.println("Carte ajoutée avec succès: " + carteAjoute.getId());
					fileScanner.nextLine();
				}
				while (fileScanner.hasNext()) {
					String line = fileScanner.nextLine();
					String[] carteInfo = line.split(",");
					String nom = carteInfo[0];
					String description = carteInfo[1];
					float prix = Float.parseFloat(carteInfo[2].trim());
					String type = carteInfo[3];
					platBll.insert(nom,description,prix,type,carteAjoute);
				}
				afficherCarte(carteAjoute);
				affecterCarteRestaurant(carteAjoute);
			}
		} catch (FileNotFoundException | BLLException e) {
			System.out.println("Une erreur est survenue :");
			e.printStackTrace();
		}
	}

	private static void affecterCarteRestaurant(Carte carte) {
		System.out.println("A combien de restaurant voulez-vous affectez cette carte ?");
		int nbAffectation = scan.nextInt();
		scan.nextLine();
		if (nbAffectation > 0) {
			System.out.println("Saisissez successivement les numéros des restaurants auxquels vous voulez affecter cette carte.");
			listerRestaurant();
			for (int i = 0; i < nbAffectation ; i++) {
				System.out.println("Saisie " + (int) (i+1) + " :");
				int idRestaurant = scan.nextInt();
				scan.nextLine();
				try {
					Restaurant restaurant = restaurantBll.selectById(idRestaurant);
					restaurant.setCarte(carte);
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
			System.out.println("********************");
		}
	}

	private static void modifierPlat(Carte carte) throws BLLException {
		System.out.println("Quel item souhaitez-vous modifier parmi les suivants ?");
		List<Plat> plats =  platBll.selectAll();
		for (Plat current : plats) {
			if (current.getCarte().getId() == carte.getId()) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		}
		int saisiePlat= scan.nextInt();
		scan.nextLine();

		Plat platAModifier = platBll.selectById(saisiePlat); 
		System.out.println("Vous avez choisi la modification de l'item suivant :");
		System.out.println(platAModifier);

		System.out.println("Veuillez saisir son nom");
		String nomPlat = scan.nextLine();
		platAModifier.setNom(nomPlat);

		System.out.println("Veuillez saisir sa description");
		String descriptionPlat = scan.nextLine();
		platAModifier.setDescription(descriptionPlat);

		System.out.println("Veuillez saisir son prix (ex. 12,5)");
		Float prix = scan.nextFloat();
		scan.nextLine();
		platAModifier.setPrix(prix);

		platBll.update(platAModifier);
		System.out.println("L'item suivant a été modifié : \n" + platAModifier);
	}

	private static void suppressionPlat(Carte carte) {
		System.out.println("Quel item souhaitez-vous modifier parmi les suivants ?");
		List<Plat> plats;
		try {
			plats = platBll.selectAll();
			for (Plat current : plats) {
				if (current.getCarte().getId() == carte.getId()) {
					System.out.println("\t" + current.getId() + ". " + current);
				}
			}
			int saisiePlat= scan.nextInt();
			scan.nextLine();
			platBll.delete(saisiePlat);
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private static void afficherCarte(Carte carteNouvelle) {
		System.out.println("Carte n°"+carteNouvelle.getId() + " : " + carteNouvelle.getNom());
		List<Plat> items;
		List<Plat> entrees = new ArrayList<>();
		List<Plat> plats = new ArrayList<>();
		List<Plat> desserts = new ArrayList<>();
		List<Plat> boissons = new ArrayList<>();
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
				System.out.println("\t" + current);
			}
			System.out.println("Plats :");
			for (Plat current : plats) {
				System.out.println("\t" + current);
			}
			System.out.println("Desserts :");
			for (Plat current : desserts) {
				System.out.println("\t" + current);
			}
			System.out.println("Boissons :");
			for (Plat current : boissons) {
				System.out.println("\t" + current);
			}
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue :");
			e.printStackTrace();
		}
	}

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
