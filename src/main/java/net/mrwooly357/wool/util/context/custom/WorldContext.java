package net.mrwooly357.wool.util.context.custom;

import net.minecraft.world.World;
import net.mrwooly357.wool.util.context.Context;

public class WorldContext implements Context {

    protected final World world;

    public WorldContext(World world) {
        this.world = world;
    }


    public World getWorld() {
        return world;
    }
}
