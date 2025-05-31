package net.mrwooly357.wool.animation;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.animation.interpolation.Interpolation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public record Animation(Identifier id, int length, boolean loop, Map<String, List<Keyframe>> keyframes) {

    public record Keyframe(float time, @Nullable Vector3f translation, @Nullable Vector3f rotation, @Nullable Vector3f scale, Interpolation interpolation) {}
}
