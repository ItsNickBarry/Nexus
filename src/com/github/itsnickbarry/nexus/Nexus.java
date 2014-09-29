package com.github.itsnickbarry.nexus;

import java.util.UUID;

import org.bukkit.block.Block;

public class Nexus {

    private final int id;
    
    private final int x, y, z;
    private final UUID worldUID;
    
    private int power, spread; //these must not be allowed to = 0

    private int effectiveRadius; // This value needs to be stored here and updated regularly

    public Nexus(Block block, int power, int spread, boolean real) {
        if (real)
            this.id = NexusUtil.nexusCurrentId.incrementAndGet();
        else
            this.id = 0;
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.worldUID = block.getWorld().getUID();
        this.power = power;
        this.spread = spread;
        this.calculateEffectiveRadius();
    }

    public int getEffectiveRadius() {
        return this.effectiveRadius;
    }

    public int getId() {
        return this.id;
    }

    public int getPower() {
        return this.power;
    }

    public int getSpread() {
        return this.spread;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }
    
    public UUID getWorldUID() {
        return this.worldUID;
    }

    public void calculateEffectiveRadius(){
        this.effectiveRadius = (int) Math.sqrt(-2 * Math.pow(this.spread, 2) * Math.log((NexusUtil.minPower * this.spread) / this.power));
    }

    public double powerAt(int x, int y, int z) {

        if (!NexusUtil.useSpheres) {
            y = this.y;
        }

        double distance = Math.sqrt(Math.pow(x - this.getX(), 2) + Math.pow(y - this.getY(), 2) + Math.pow(z - this.getZ(), 2));

        return NexusUtil.formula(distance, this.power, this.spread);
    }
    
    public double powerAt(Block block) {
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        if (!NexusUtil.useSpheres)
            y = this.y;
        double distance = Math.sqrt(Math.pow(x - this.getX(), 2) + Math.pow(y - this.getY(), 2) + Math.pow(z - this.getZ(), 2));
        return NexusUtil.formula(distance, this.power, this.spread);
    }
}