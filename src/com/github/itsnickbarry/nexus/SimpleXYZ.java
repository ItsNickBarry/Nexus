package com.github.itsnickbarry.nexus;

import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class SimpleXYZ {
    
    private final int x, y, z;
    
    public SimpleXYZ(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public SimpleXYZ(Block block) {
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
    }
    
    public SimpleXYZ(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
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
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof SimpleXYZ) {
            SimpleXYZ xyz = (SimpleXYZ) o;
            return xyz.x == x && xyz.y == y && xyz.z == z;
        }
        return false;
    }

}
