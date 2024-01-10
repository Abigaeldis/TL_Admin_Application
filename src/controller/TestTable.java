package controller;

import java.util.List;

import bll.BLLException;
import bll.TableBLL;
import bo.Table;

public class TestTable {
	private static TableBLL bll;
	
	public static void main(String[] args) {
		try {
			bll = new TableBLL();
		} catch (BLLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du selectAll");
		System.out.println("*******************");
		List<Table> tables;
		try {
			tables = bll.selectAll();
			for (Table current : tables) {
				System.out.println(current);
			}
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du selectById");
		System.out.println("*******************");
		try {
			System.out.println(bll.selectById(2));
		} catch (BLLException e) {
			e.printStackTrace();
		}

		System.out.println("*******************");
		System.out.println("Test du insert");
		System.out.println("*******************");
		try {
			System.out.println(bll.insert(6,4,"Libre",1));
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du update");
		System.out.println("*******************");
		try {
			Table table = bll.selectById(3);
			table.setIdRestaurant(2);
			bll.update(table);
			System.out.println(bll.selectById(3));
		} catch (BLLException e) {
			e.printStackTrace();
		}
		
		System.out.println("*******************");
		System.out.println("Test du selectAll");
		System.out.println("*******************");
		try {
			tables = bll.selectAll();
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
			bll.delete(5);
		} catch (BLLException e) {
			e.printStackTrace();
		}
	}
}
