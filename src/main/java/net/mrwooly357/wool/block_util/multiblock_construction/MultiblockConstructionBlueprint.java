package net.mrwooly357.wool.block_util.multiblock_construction;

import net.minecraft.block.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiblockConstructionBlueprint {

    private final Layer[] layers;

    private MultiblockConstructionBlueprint(Layer[] layers) {
        this.layers = layers;
    }


    public Layer getLayer(int index) {
        return layers[index];
    }

    public int getSizeInLayers() {
        return layers.length;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private final List<Layer> layers = new ArrayList<>();

        private Builder() {}


        public Builder layer(Layer layer) {
            layers.add(layer);

            return this;
        }

        public MultiblockConstructionBlueprint build() {
            return new MultiblockConstructionBlueprint(layers.toArray(Layer[]::new));
        }
    }


    public static final class Layer {

        private final String[] patterns;
        private final Map<Character, List<BlockState>> definitions;

        private Layer(String[] patterns, Map<Character, List<BlockState>> definitions) {
            this.patterns = patterns;
            this.definitions = definitions;
        }


        public List<List<BlockState>> getDefinedPattern(int index) {
            List<List<BlockState>> pattern = new ArrayList<>();
            String raw = patterns[index];

            for (int i = 0; i < raw.length(); i++)
                pattern.add(definitions.get(raw.charAt(i)));

            return pattern;
        }

        public int getSizeInPatterns() {
            return patterns.length;
        }

        public static Builder builder() {
            return new Builder();
        }


        public static final class Builder {

            private final List<String> patterns = new ArrayList<>();
            private final Map<Character, List<BlockState>> definitions = new HashMap<>();

            private Builder() {}


            public Builder pattern(String pattern) {
                patterns.add(pattern);

                return this;
            }

            public Builder definition(char key, List<BlockState> states) {
                definitions.put(key, states);

                return this;
            }

            public Layer build() {
                return new Layer(patterns.toArray(new String[0]), definitions);
            }
        }
    }
}
