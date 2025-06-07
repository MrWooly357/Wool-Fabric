package net.mrwooly357.wool;

/**
 * This interface is used for mod initializer classes of mods that use Wool. You should implement it in your mod initializer class.
 */
public interface WoolInitializer {


    /**
     * Called when Wool is initializing.
     */
    void onWoolInitialize();
}
