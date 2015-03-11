package com.github.itsnickbarry.nexus;

import java.util.Objects;

import org.bukkit.Chunk;

public class SimpleXZ {
    
    private final int x, z;
    
    public SimpleXZ(int x, int z) {
        this.x = x;
        this.z = z;
    }
    
    public SimpleXZ(Chunk chunk) {
        this.x = chunk.getX();
        this.z = chunk.getZ();
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getZ() {
        return this.z;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof SimpleXZ) {
            SimpleXZ xz = (SimpleXZ) o;
            return xz.x == x && xz.z == z;
        }
        return false;
    }

}
