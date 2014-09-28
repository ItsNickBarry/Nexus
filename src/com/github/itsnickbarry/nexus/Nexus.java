package com.github.itsnickbarry.nexus;

public class Nexus{
	
	static int count = 0;
	
	int id;
	int power;
	int spread;
	int x;
	int y;
	int z;

	int effectiveRadius; //This value needs to be stored here and updated regularly
	
	public Nexus(int power, int spread, int x, int y, int z){
		this.id = count++;
		this.power = power;
		this.spread = spread;
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.calculateEffectiveRadius();
	}
	
	public int getEffectiveRadius(){
		return this.effectiveRadius;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getPower(){
		return this.power;
	}
	
	public int getSpread(){
		return this.spread;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getZ(){
		return this.z;
	}
	
	public void calculateEffectiveRadius(){
		//TODO calculate how many blocks away the nexus still has a power >= minPower
		int radius = 0;
		
		while (NexusUtil.formula(radius, this.power, this.spread) >= NexusUtil.minPower){
			radius++;
		}
		this.effectiveRadius = radius;
	}
	
	public double powerAt(int x, int y, int z){
		
		if (!NexusUtil.useSpheres){
			y = this.y;
		}
		
		double distance = Math.sqrt(Math.pow(x - this.getX(), 2) + Math.pow(y - this.getY(), 2) + Math.pow(z - this.getZ(), 2));
		
		return NexusUtil.formula(distance, this.power, this.spread);
	}
}