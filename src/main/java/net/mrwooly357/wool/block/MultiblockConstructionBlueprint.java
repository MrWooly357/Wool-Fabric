package net.mrwooly357.wool.block;

import net.minecraft.block.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiblockConstructionBlueprint {

    private List<Layer> layers = new ArrayList<>();

    public MultiblockConstructionBlueprint() {}


    public int getSizeInLayers() {
        return layers.size();
    }

    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    public Layer getLayer(int index) {
        return layers.get(index);
    }


    public class Layer {

        private List<String> patterns = new ArrayList<>();
        private Map<Character, @Nullable BlockState> definitions = new HashMap<>();

        public Layer() {}


        public void addPattern(String pattern) {
            patterns.add(pattern);
        }

        public void addDefinitions(Character character, @Nullable BlockState blockState) {
            definitions.put(character, blockState);
        }

        public List<BlockState> getDefinedPattern(int index) {
            List<BlockState> pattern = new ArrayList<>();
            String raw = patterns.get(index);

            for (int a = 1; a < raw.length(); a++) {
                pattern.add(definitions.get(raw.charAt(a)));
            }

            return pattern;
        }

        public int getSizeInPatterns() {
            return patterns.size();
        }
    }
}
