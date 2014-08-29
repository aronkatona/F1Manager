package com.aronkatona.server;

public class TeamPoints {

	private int position;
	
	private String driverName;	
	
	public TeamPoints(String driverName,int position){
		this.driverName = driverName; 
		this.position = position;
	}
	
	public void setName(String driverName){
		this.driverName = driverName;
	}
	
	public String getName(){
		return driverName;
	}
	
	public int getPoint(){

		switch(this.position){
			case 1: return 25; 
			case 2: return 18; 
			case 3: return 15; 
			case 4: return 12; 
			case 5: return 10; 
			case 6: return 8; 
			case 7: return 6; 
			case 8: return 4; 
			case 9: return 2; 
			case 10: return 1;  
		}
		
		return 0;
	}
	
	


}
