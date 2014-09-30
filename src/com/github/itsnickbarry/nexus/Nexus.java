package com.github.itsnickbarry.nexus;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Nexus {

    private final int id;
    
    private final int x, y, z;
    private final UUID worldUID;
    
    private int power, spread, radius; // These values need to be stored here and updated regularly
    
    private int powerPoints, spreadPoints;

    public Nexus(Block block, boolean real) {
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.worldUID = block.getWorld().getUID();
        this.power = NexusUtil.basePower;
        this.spread = NexusUtil.baseSpread;
        if (real) {
            this.id = NexusUtil.nexusCurrentId.incrementAndGet();
            this.calculateRadius();
        } else {
            this.id = 0;
            this.radius = 0;
        }
    }

    public int getRadius() {
        return this.radius;
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
    
    public boolean allowsPlayerBlockEdit(Player p) {
        //TODO
        return false;
    }

    private void calculateRadius() {
        this.radius = (int) Math.sqrt(-2 * Math.pow((double)this.spread, 2) * Math.log(((double)NexusUtil.minPower * (double)this.spread) / (double)this.power));
    }
    
    private void calculatePower() {
        //TODO sqrt function
        this.power = (int) Math.sqrt((double)NexusUtil.powerFactor * (double)this.powerPoints);
    }
    
    private void calculateSpread() {
        double normalizedSpread = 1; //function of power
        //y = V * 2 * arctan (C * spreadPoints) / pi
        //0 < C < 1
        //V = Power / normalized Spread
        this.spread = (int) (normalizedSpread + (normalizedSpread *(double)this.power * 2 * Math.atan(NexusUtil.spreadFactor *(double)this.spreadPoints)) / ((double)NexusUtil.baseSpread * Math.PI));
        
    }
    
    public double powerAt(Block block) {
        int x = block.getX();
        int y = (NexusUtil.useSpheres ? block.getY() : this.y);
        int z = block.getZ();
        double distance = Math.sqrt(Math.pow(x - this.getX(), 2) + Math.pow(y - this.getY(), 2) + Math.pow(z - this.getZ(), 2));
        return NexusUtil.formula(distance, this.power, this.spread);
    }
    
    public void update() {
        this.calculatePower();
        this.calculateSpread();
        this.calculateRadius();
    }
}