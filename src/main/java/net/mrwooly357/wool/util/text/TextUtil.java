package net.mrwooly357.wool.util.text;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.mrwooly357.wool.Wool;

public final class TextUtil {


    public static String translatableKey(TextKey key, String namespace, String name) {
        return key.getSuffix() + "." + namespace + "." + name;
    }

    public static String woolTranslatableKey(TextKey key, String name) {
        return translatableKey(key, Wool.MOD_ID, name);
    }

    public static MutableText translatable(TextKey key, String namespace, String name) {
        return Text.translatable(translatableKey(key, namespace, name));
    }

    public static MutableText translatable(TextKey key, String namespace, String name, Object... arguments) {
        return Text.translatable(translatableKey(key, namespace, name), arguments);
    }

    public static MutableText woolTranslatable(TextKey key, String name) {
        return translatable(key, Wool.MOD_ID, name);
    }

    public static MutableText woolTranslatable(TextKey key, String name, Object... arguments) {
        return translatable(key, Wool.MOD_ID, name, arguments);
    }
}
