package net.mrwooly357.wool.util.context.custom;

import net.minecraft.world.World;
import net.mrwooly357.wool.util.context.Context;

/**
 * A {@link Context context} with a {@link World world}.
 *
 * @see Context
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class WorldContext implements Context {

    /**
     * The {@link World world}.
     */
    protected final World world;

    /**
     * Creates a new {@link WorldContext} using a {@link World world}.
     *
     * @param world the {@link World}.
     */
    public WorldContext(World world) {
        this.world = world;
    }


    /**
     * Gets the {@link World world}.
     *
     * @return the {@link World world}.
     */
    public World getWorld() {
        return world;
    }
}
