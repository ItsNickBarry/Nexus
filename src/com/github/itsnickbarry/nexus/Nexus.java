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
        this.powerPoints = NexusUtil.basePowerPoints;
        if (real) {
            this.id = NexusUtil.nexusCurrentId.incrementAndGet();
            NexusUtil.refreshSets();
        } else {
            this.id = 0;
            this.power = 
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
    
    public double powerAt(Block block) {
        //function is not continuous when spread == 0; in this case, return 0 (the only situation in which spread == 0 should be when power == 0)
        int x = block.getX();
        int y = (NexusUtil.useSpheres ? block.getY() : this.y);
        int z = block.getZ();
        double distance = Math.sqrt(Math.pow(x - this.getX(), 2) + Math.pow(y - this.getY(), 2) + Math.pow(z - this.getZ(), 2));
        return this.spread == 0 ? 0 :
            (this.power / this.spread)
                * Math.exp(-1
                        * ((Math.pow(distance, 2)) / (2 * Math.pow(this.spread, 2))));
    }
    
    public void update() {
        this.calculatePower();
        this.calculateSpread();
        this.calculateRadius();
    }

    private void calculateRadius() {
        //function is not continuous when power < spread; in this case, set radius to 0
        this.radius = this.power < this.spread ?  0 : (int) Math.sqrt(-2 * Math.pow((double)this.spread, 2) * Math.log(((double)NexusUtil.minPower * (double)this.spread) / (double)this.power));
    }
    
    private void calculatePower() {
        //TODO this increases too quickly at the beginning; maybe a linear function would work, in combination with half-life or exponential decay of powerPoints
        //maybe y = sqrt(1000x) * 2atan(.01x) / π
        
        //TODO set a minumum power, so that spread < power always
        this.power = (int) (Math.sqrt((double)NexusUtil.powerFactor * (double)this.powerPoints) + 1);
    }
    
    private void calculateSpread() {
        double normalizedSpread = calculateSpreadNormalized();
        
        this.spread = (int) (normalizedSpread + ((2 / Math.PI) * normalizedSpread * NexusUtil.spreadVariability * Math.atan(NexusUtil.spreadModificationFactor * (double)this.spreadPoints)));
    }
    
    private double calculateSpreadNormalized() {
        //This formula is only partially arbitrary.  I think I could have gotten to it logically if I knew how to do math.
        return Math.PI * Math.sqrt((double)this.power / Math.PI);
    }
}