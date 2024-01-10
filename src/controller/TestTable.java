package controller;

import java.util.List;

import bll.BLLException;
import bll.RestaurantBLL;
import bll.TableBLL;
import bo.Restaurant;
import bo.Table;

public class TestTable {
	private static TableBLL tableBll;
	private static RestaurantBLL restaurantBll;

	
	public static void main(String[] args) {
		try {
			tableBll = new TableBLL();
			restaurantBll = new RestaurantBLL();
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du selectAll");
		System.out.println("Table");
		System.out.println("*******************");
		List<Table> tables;
		try {
			tables = tableBll.selectAll();
			for (Table current : tables) {
				System.out.println(current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Restaurant");
		System.out.println("*******************");
		List<Restaurant> restaurants;
		try {
			restaurants = restaurantBll.selectAll();
			for (Restaurant current : restaurants) {
				System.out.println(current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du selectById");
		System.out.println("*******************");
		try {
			System.out.println(tableBll.selectById(2));
		} catch (BLLException e) {
			e.printStackTrace();
		}
		Restaurant restaurant1 = null;
		try {
			restaurant1 = restaurantBll.selectById(1);
			System.out.println(restaurant1);
		} catch (BLLException e) {
			e.printStackTrace();
		}
		System.out.println("*******************");
		System.out.println("Test du insert");
		System.out.println("*******************");
		try {
			System.out.println(tableBll.insert(6,4,"Libre",restaurant1));
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du update");
		System.out.println("*******************");
		try {
			Table table = tableBll.selectById(3);
			table.setRestaurant(restaurant1);
			tableBll.update(table);
			System.out.println(tableBll.selectById(3));
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du selectAll");
		System.out.println("*******************");
		try {
			tables = tableBll.selectAll();
			for (Table current : tables) {
				System.out.println(current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du delete");
		System.out.println("*******************");
		try {
			tableBll.delete(7);
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du selectAll");
		System.out.println("*******************");
		try {
			tables = tableBll.selectAll();
			for (Table current : tables) {
				System.out.println(current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
}
