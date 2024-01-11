package controller;

import java.util.List;
import java.util.Scanner;

import bll.BLLException;
import bll.CarteBLL;
import bll.RestaurantBLL;
import bo.Carte;
import bo.Restaurant;

public class TestRestaurants {
	private static Scanner scan;
	private static RestaurantBLL bll;
	private static CarteBLL carteBll;
	
	public static void main(String[] args) {
		System.out.println("Bienvenue dans notre application de gestion des restaurants");
		scan = new Scanner(System.in);
		try {
			bll = new RestaurantBLL();
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
				creerRestaurant();
				break;
			case 2:
				listerRestaurant();
				break;
			case 3:
				supprimerRestaurant();
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
	
	private static void supprimerRestaurant() {
		try {
			List<Restaurant> restaurants = bll.selectAll();
			if (restaurants.size() == 0) {
				System.out.println("Il n'existe aucun restaurant en base de données");
				return;
			}
		} catch (BLLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Vous avez choisi de supprimer un restaurant");
		System.out.println("Veuillez saisir l'id du restaurant à supprimer");
		int id = scan.nextInt();
		scan.nextLine();
		
		try {
			bll.delete(id);
			System.out.println("Le restaurant a bien été supprimé");
		} catch (BLLException e) {
			System.out.println("L'id saisi n'existe pas en base de données");
		}
	}

	private static void listerRestaurant() {
		try {
			List<Restaurant> restaurants = bll.selectAll();
			for (Restaurant current : restaurants) {
				System.out.println("\t" + current.getId() + ". " + current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}

	private static void creerRestaurant() {
		
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
		
		
			Restaurant restaurantAjoute = bll.insert(nom, adresse, description,carte);
			System.out.println("Restaurant ajouté avec succès " + restaurantAjoute);
		} catch (BLLException e) {
			System.out.println("Une erreur est survenue :");
			for (String erreur : e.getErreurs()) {
				System.out.println("\t" + erreur);
			}
			e.printStackTrace();
		}
		
	}
	
	private static int afficherMenu() {
		System.out.println("1. Créer un restaurant");
		System.out.println("2. Consulter les restaurants");
		System.out.println("3. Supprimer un restaurant");
		System.out.println("4. Quitter");
		int choix = scan.nextInt();
		scan.nextLine();
		return choix;
	}
}
