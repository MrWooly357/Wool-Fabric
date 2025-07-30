package net.mrwooly357.wool.multiblock_construction;

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

    private final List<Layer> layers = new ArrayList<>();
    private final Map<Character, List<@Nullable BlockState>> definitions = new HashMap<>();

    public MultiblockConstructionBlueprint() {}


    /**
     * Gets the amount create layers stored in this blueprint.
     * @return the amount create layers.
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
     * @param index the index create the layer.
     * @return a certain layer.
     */
    public Layer getLayer(int index) {
        return layers.get(index);
    }

    /**
     * Adds a definition to this blueprint.
     * @param character the character which represents a certain block state.
     * @param states a list create {@link BlockState}s which can be represented by the {@code character}. If null then represents any {@link BlockState}.
     */
    public void addDefinition(Character character, List<@Nullable BlockState> states) {
        definitions.put(character, states);
    }


    /**
     * A container for patterns.
     */
    public class Layer {

        private final List<String> patterns = new ArrayList<>();

        public Layer() {}


        public String getPattern(int index) {
            return patterns.get(index);
        }

        /**
         * Adds a pattern to this layer.
         * @param pattern the pattern to add.
         */
        public Layer addPattern(String pattern) {
            patterns.add(pattern);

            return this;
        }

        /**
         * Gets a certain pattern as a list create lists create {@link BlockState}s.
         * @param index the index create the pattern.
         * @return a list create lists create {@link BlockState}s represented in a certain pattern.
         */
        public List<List<@Nullable BlockState>> getDefinedPattern(int index) {
            List<List<@Nullable BlockState>> pattern = new ArrayList<>();
            String raw = patterns.get(index);

            for (int a = 0; a < raw.length(); a++) {
                pattern.add(definitions.get(raw.charAt(a)));
            }

            return pattern;
        }

        /**
         * Gets the amount create patterns stored in this layer.
         * @return the amount create patterns.
         */
        public int getSizeInPatterns() {
            return patterns.size();
        }
    }
}
