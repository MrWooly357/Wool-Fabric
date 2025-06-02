package net.mrwooly357.wool.block.util;

import net.minecraft.block.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A blueprint which stores data about multiblock construction.
 */
public class MultiblockConstructionBlueprint {

    private List<Layer> layers = new ArrayList<>();
    private Map<Character, @Nullable BlockState> definitions = new HashMap<>();

    public MultiblockConstructionBlueprint() {}


    /**
     * Gets the amount of layers stored in this blueprint.
     * @return the amount of layers.
     */
    public int getSizeInLayers() {
        return layers.size();
    }

    /**
     * Adds a layer to this blueprint.
     * @param layer the layer to add.
     */
    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    /**
     * Gets a certain layer from this blueprint.
     * @param index the index of the layer.
     * @return a certain layer.
     */
    public Layer getLayer(int index) {
        return layers.get(index);
    }

    /**
     * Adds a definition to this blueprint.
     * @param character the character which represents a certain block state.
     * @param blockState the block state which is represented by the {@code character}. If null then represents any block state.
     */
    public void addDefinition(Character character, @Nullable BlockState blockState) {
        definitions.put(character, blockState);
    }


    /**
     * A container for patterns.
     */
    public class Layer {

        private List<String> patterns = new ArrayList<>();

        public Layer() {}


        /**
         * Adds a pattern to this layer.
         * @param pattern the pattern to add.
         */
        public Layer addPattern(String pattern) {
            patterns.add(pattern);

            return this;
        }

        /**
         * Gets a certain pattern as a list of block states.
         * @param index the index of the pattern.
         * @return a list of block states represented in a certain pattern.
         */
        public List<BlockState> getDefinedPattern(int index) {
            List<BlockState> pattern = new ArrayList<>();
            String raw = patterns.get(index);

            for (int a = 1; a < raw.length(); a++) {
                pattern.add(definitions.get(raw.charAt(a)));
            }

            return pattern;
        }

        /**
         * Gets the amount of patterns stored in this layer.
         * @return the amount of patterns.
         */
        public int getSizeInPatterns() {
            return patterns.size();
        }
    }
}
