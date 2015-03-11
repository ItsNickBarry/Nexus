package com.github.itsnickbarry.nexus;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Nexus {

    private final int id;
    private int ownerId;
    
    //private final int x, y, z;
    private final SimpleXYZ xyz;
    private final SimpleXZ chunkCoordinates;
    private final UUID worldUID;
    
    private int power, spread, radius; // These values need to be stored here and updated regularly
    
    private double powerPoints, spreadPoints;
    private long lastDecayTime;

    public Nexus(Block block, Player owner, boolean real) {
        //this.x = block.getX();
        //this.y = block.getY();
        //this.z = block.getZ();
        this.xyz = new SimpleXYZ(block);
        this.chunkCoordinates = new SimpleXZ(block.getChunk());
        this.worldUID = block.getWorld().getUID();
        this.powerPoints = NexusUtil.powerPointsBase;
        this.lastDecayTime = System.currentTimeMillis();
        if (real) {
            this.id = NexusUtil.nexusCurrentId.incrementAndGet();
            this.setOwner(owner);
            NexusUtil.refreshSets();
        } else {
            this.id = 0;
            this.ownerId = 0;
            this.radius = 0;
        }
    }
    
    public Object getOwner() {
    	return NexusUtil.getNexusOwner(this.ownerId);
    }

    public int getOwnerId() {
    	return this.ownerId;
    }
    
    // TODO Improve
    public void setOwner(Object owner) {
        if (owner == null) {
            this.ownerId = 0;
        } else if (owner instanceof Player) {
            this.ownerId = NexusUtil.absoluteGetPlayerId(((Player) owner).getUniqueId());
        } else if (owner instanceof NexusGroup) {
            
        }
    	//this.ownerId = owner.getId();
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
    
    public int getRadius() {
        return this.radius;
    }
    
    public double getPowerPoints() {
    	return this.powerPoints;
    }

    public int getX() {
        return this.xyz.getX();
    }

    public int getY() {
        return this.xyz.getY();
    }

    public int getZ() {
        return this.xyz.getZ();
    }
    
    public SimpleXYZ getXYZ() {
        return this.xyz;
    }
    
    public SimpleXZ getChunkCoordinates() {
        return this.chunkCoordinates;
    }
    
    public UUID getWorldUID() {
        return this.worldUID;
    }
    
    public boolean allowsPlayerBlockEdit(Player p) {
        if (this.getOwner().equals(p.getUniqueId()))
        	return true;
        if (this.getOwner() instanceof NexusGroup) {
        	NexusGroup owningGroup = (NexusGroup) this.getOwner();
        	if (owningGroup.getRole(p.getUniqueId()) != NexusGroup.Role.NONE)
        		return true;
        }
        return false;
    }
    
    public double influenceAt(Block block) {
        //function is not continuous when spread == 0; in this case, return 0 (the only situation in which spread == 0 should be when power == 0)
        int x = block.getX();
        int y = (NexusUtil.useSpheres ? block.getY() : this.getY());
        int z = block.getZ();
        double distance = Math.sqrt(Math.pow(x - this.getX(), 2) + Math.pow(y - this.getY(), 2) + Math.pow(z - this.getZ(), 2));
        return this.spread == 0 ? 0 :
            (this.power / this.spread)
                * Math.exp(-1
                        * ((Math.pow(distance, 2)) / (2 * Math.pow(this.spread, 2))));
    }
    
    /**
     * 
     * @return true if Nexus still has enough power points to remain active
     */
    public boolean update() {
        //decayPoints() could potentially be run at a different time, but will be run here for now
        if (!this.decayPoints()){
            return false;
        }
        this.calculatePower();
        this.calculateSpread();
        this.calculateRadius();
        
        return true;
    }
    
    private void calculatePower() {
        //TODO this increases too quickly at the beginning; maybe a linear function would work, in combination with half-life or exponential decay of powerPoints
        this.power =  (int) (Math.sqrt((double)NexusUtil.powerLevelFactor * (double)this.powerPoints));
    }

    private void calculateRadius() {
        //function is not continuous when power < spread; in this case, set radius to 0
        this.radius = this.power < this.spread ?  0 : (int) Math.sqrt(-2 * Math.pow((double)this.spread, 2) * Math.log((NexusUtil.influenceMin * (double)this.spread) / (double)this.power));
    }
    
    private void calculateSpread() {
        //currently limited to normalizedSpread +- normalizedSpread
        double normalizedSpread = calculateSpreadNormalized();
        
        this.spread =(int) (normalizedSpread + ((2 / Math.PI) * normalizedSpread * NexusUtil.spreadLevelVariability * Math.atan(NexusUtil.spreadLevelFactor * (double)this.spreadPoints)));
    }
    
    /**
     * 
     * @return the spread level of a Nexus at a given power level, before accounting for spread points
     */
    private double calculateSpreadNormalized() {
        //This formula is only partially arbitrary.  I think I could have gotten to it logically if I knew how to do math.
        return Math.PI * Math.sqrt((double)this.power / Math.PI);
    }
    
    /**
     * 
     * @return true if Nexus still has enough power points to remain active
     */
    private boolean decayPoints() {
        if (NexusUtil.powerPointsHalfLife != 0)
            this.powerPoints = (this.powerPoints * Math.pow(.5, (double)(System.currentTimeMillis() - this.lastDecayTime) / NexusUtil.powerPointsHalfLife));
        if (NexusUtil.spreadPointsHalfLife != 0)
            this.spreadPoints = (this.spreadPoints * Math.pow(.5, (double)(System.currentTimeMillis() - this.lastDecayTime) / NexusUtil.spreadPointsHalfLife));
        this.lastDecayTime = System.currentTimeMillis();
        return this.powerPoints > NexusUtil.powerPointsMin;
    }
}