package com.aronkatona.server;

public class SessionLoader {
	
	private static SessionLoader instance = null;
	
	public static boolean getInstance(){
			if(instance == null){
				System.out.println("isNUll");
				instance = new SessionLoader();
				return true;
			}
			System.out.println("notNUll");
			return false;
	}
}
