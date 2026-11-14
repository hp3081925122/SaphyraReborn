package com.hp.saphyrareborn.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;

/**
 * Plays the keyframe animations in assets/saphyra_reborn/player_animations on players.
 * Replaces the Player Animator dependency; reads the same GeckoLib-style JSON and
 * applies it the same way (part poses after setupAnim, "body" bone on the pose stack).
 */
public final class PlayerAnimations {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<String, float[]> DEFAULT_POS = Map.of(
            "right_arm", new float[]{-5, 2, 0},
            "left_arm", new float[]{5, 2, 0},
            "right_leg", new float[]{-1.9f, 12, 0.1f},
            "left_leg", new float[]{1.9f, 12, 0.1f});
    private static final float[] ZERO = {0, 0, 0};
    private static final Map<Integer, Playing> PLAYING = new HashMap<>();
    private static Map<String, Anim> animations;
    private static Object lastLevel;

    private PlayerAnimations() {
    }

    private record Playing(Anim anim, float start) {
    }

    /** Track per axis: 0-2 position, 3-5 rotation (radians). Values are absolute. */
    private record Anim(float length, Map<String, Track[]> bones) {
    }

    public static void play(int entityId, String name, boolean override) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;
        Entity entity = mc.level.getEntity(entityId);
        if (!(entity instanceof Player)) return;
        Anim anim = animations().get(name);
        if (anim == null) {
            LOGGER.warn("Unknown player animation {}", name);
            return;
        }
        Playing current = PLAYING.get(entityId);
        if (!override && current != null && entity.tickCount - current.start < current.anim.length) return;
        if (mc.level != lastLevel) {
            PLAYING.clear();
            lastLevel = mc.level;
        }
        PLAYING.put(entityId, new Playing(anim, entity.tickCount));
    }

    private static Playing active(int id, float age) {
        if (PLAYING.isEmpty()) return null;
        Object level = Minecraft.getInstance().level;
        if (level != lastLevel) {
            // Entity ids are per world; drop everything when the client world changes.
            PLAYING.clear();
            lastLevel = level;
            return null;
        }
        Playing p = PLAYING.get(id);
        if (p == null) return null;
        float t = age - p.start;
        if (t >= p.anim.length) {
            PLAYING.remove(id);
            return null;
        }
        // Negative time: first-person hand render passes age 0, or tickCount was reset.
        return t < 0 ? null : p;
    }

    /** Called after the model's setupAnim. Returns true if the pose was changed. */
    public static boolean applyModel(int id, float age, HumanoidModel<?> model) {
        Playing p = active(id, age);
        if (p == null) return false;
        float t = age - p.start;
        applyPart(p.anim, "head", model.head, t);
        applyPart(p.anim, "torso", model.body, t);
        applyPart(p.anim, "right_arm", model.rightArm, t);
        applyPart(p.anim, "left_arm", model.leftArm, t);
        applyPart(p.anim, "right_leg", model.rightLeg, t);
        applyPart(p.anim, "left_leg", model.leftLeg, t);
        return true;
    }

    /** Called at the end of the renderer's setupRotations. */
    public static void applyBody(int id, float age, PoseStack poseStack) {
        Playing p = active(id, age);
        if (p == null) return;
        Track[] tr = p.anim.bones.get("body");
        if (tr == null) return;
        float t = age - p.start;
        float end = p.anim.length;
        poseStack.translate(sample(tr[0], t, end, 0), sample(tr[1], t, end, 0) + 0.7f, sample(tr[2], t, end, 0));
        poseStack.mulPose(Axis.ZP.rotation(sample(tr[5], t, end, 0)));
        poseStack.mulPose(Axis.YP.rotation(sample(tr[4], t, end, 0)));
        poseStack.mulPose(Axis.XP.rotation(sample(tr[3], t, end, 0)));
        poseStack.translate(0, -0.7f, 0);
    }

    private static void applyPart(Anim anim, String bone, ModelPart part, float t) {
        Track[] tr = anim.bones.get(bone);
        if (tr == null) return;
        float end = anim.length;
        part.x = sample(tr[0], t, end, part.x);
        part.y = sample(tr[1], t, end, part.y);
        part.z = sample(tr[2], t, end, part.z);
        part.xRot = sample(tr[3], t, end, part.xRot);
        part.yRot = sample(tr[4], t, end, part.yRot);
        part.zRot = sample(tr[5], t, end, part.zRot);
    }

    private static float sample(Track track, float t, float end, float current) {
        return track == null ? current : track.sample(t, end, current);
    }

    private static Map<String, Anim> animations() {
        if (animations == null) {
            animations = new HashMap<>();
            Map<ResourceLocation, Resource> files = Minecraft.getInstance().getResourceManager()
                    .listResources("player_animations", id -> id.getPath().endsWith(".json"));
            for (Map.Entry<ResourceLocation, Resource> file : files.entrySet()) {
                if (!file.getKey().getNamespace().equals("saphyra_reborn")) continue;
                try (Reader reader = file.getValue().openAsReader()) {
                    JsonObject anims = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonObject("animations");
                    for (Map.Entry<String, JsonElement> a : anims.entrySet()) {
                        animations.put(a.getKey(), parseAnim(a.getValue().getAsJsonObject()));
                    }
                } catch (Exception e) {
                    LOGGER.error("Failed to load player animations {}", file.getKey(), e);
                }
            }
        }
        return animations;
    }

    private static Anim parseAnim(JsonObject node) {
        float length = node.has("animation_length") ? node.get("animation_length").getAsFloat() * 20 : 1;
        Map<String, Track[]> bones = new HashMap<>();
        for (Map.Entry<String, JsonElement> bone : node.getAsJsonObject("bones").entrySet()) {
            String name = bone.getKey();
            if (name.endsWith("_bend")) continue;
            JsonObject data = bone.getValue().getAsJsonObject();
            boolean whole = name.equals("body");
            float[] def = DEFAULT_POS.getOrDefault(name, ZERO);
            Track[] tracks = new Track[6];
            readChannel(data.get("position"), tracks, 0, whole, def);
            readChannel(data.get("rotation"), tracks, 3, whole, def);
            bones.put(name, tracks);
        }
        return new Anim(length, bones);
    }

    private record Key(float tick, float[] value, DoubleUnaryOperator ease) {
    }

    private static void readChannel(JsonElement el, Track[] out, int offset, boolean whole, float[] def) {
        if (el == null) return;
        List<Key> keys = new ArrayList<>();
        if (el.isJsonArray()) {
            keys.add(new Key(0, vec(el.getAsJsonArray()), Ease.LINEAR));
        } else if (el.getAsJsonObject().has("vector")) {
            keys.add(new Key(0, vec(el.getAsJsonObject().getAsJsonArray("vector")), Ease.LINEAR));
        } else {
            for (Map.Entry<String, JsonElement> e : el.getAsJsonObject().entrySet()) {
                if (e.getKey().equals("easing") || e.getKey().equals("lerp_mode")) continue;
                float tick = Float.parseFloat(e.getKey()) * 20;
                JsonElement v = e.getValue();
                if (v.isJsonArray()) {
                    keys.add(new Key(tick, vec(v.getAsJsonArray()), Ease.LINEAR));
                    continue;
                }
                JsonObject o = v.getAsJsonObject();
                JsonElement vector = o.has("vector") ? o.get("vector") : o.has("post") ? o.get("post") : o.get("pre");
                if (vector == null) continue;
                JsonArray arr = vector.isJsonArray() ? vector.getAsJsonArray() : vector.getAsJsonObject().getAsJsonArray("vector");
                keys.add(new Key(tick, vec(arr), Ease.get(o.has("easing") ? o.get("easing").getAsString() : "linear")));
            }
        }
        if (keys.isEmpty()) return;
        keys.sort((a, b) -> Float.compare(a.tick, b.tick));
        boolean rotation = offset == 3;
        for (int axis = 0; axis < 3; axis++) {
            int n = keys.size();
            float[] ticks = new float[n];
            float[] values = new float[n];
            DoubleUnaryOperator[] eases = new DoubleUnaryOperator[n];
            for (int i = 0; i < n; i++) {
                Key k = keys.get(i);
                ticks[i] = k.tick;
                eases[i] = k.ease;
                values[i] = convert(k.value[axis], axis, rotation, whole, def);
            }
            out[offset + axis] = new Track(ticks, values, eases);
        }
    }

    /** Same axis conventions as Player Animator's GeckoLib reader. */
    private static float convert(float v, int axis, boolean rotation, boolean whole, float[] def) {
        if (rotation) {
            if (whole && axis != 2) v = -v;
            return v * Mth.DEG_TO_RAD;
        }
        if (whole) {
            v /= 16f;
            return axis == 0 ? -v : v;
        }
        return (axis == 1 ? -v : v) + def[axis];
    }

    private static float[] vec(JsonArray a) {
        return new float[]{a.get(0).getAsFloat(), a.get(1).getAsFloat(), a.get(2).getAsFloat()};
    }

    private record Track(float[] ticks, float[] values, DoubleUnaryOperator[] eases) {
        float sample(float t, float end, float current) {
            int n = ticks.length;
            if (t <= ticks[0]) {
                return ticks[0] <= 0 ? values[0] : Mth.lerp(t / ticks[0], current, values[0]);
            }
            for (int i = 0; i < n - 1; i++) {
                if (t < ticks[i + 1]) {
                    float p = (t - ticks[i]) / (ticks[i + 1] - ticks[i]);
                    return Mth.lerp((float) eases[i + 1].applyAsDouble(p), values[i], values[i + 1]);
                }
            }
            float last = ticks[n - 1];
            if (end <= last) return values[n - 1];
            // Ease back to the vanilla pose after the last keyframe, as Player Animator does.
            return Mth.lerp(Math.min(1f, (t - last) / (end - last)), values[n - 1], current);
        }
    }

    private static final class Ease {
        static final DoubleUnaryOperator LINEAR = x -> x;
        private static final double C1 = 1.70158;
        private static final double C2 = C1 * 1.525;

        static DoubleUnaryOperator get(String name) {
            return switch (name) {
                case "easeInSine" -> x -> 1 - Math.cos(x * Math.PI / 2);
                case "easeOutSine" -> x -> Math.sin(x * Math.PI / 2);
                case "easeInOutSine" -> x -> -(Math.cos(Math.PI * x) - 1) / 2;
                case "easeInQuad" -> x -> x * x;
                case "easeOutQuad" -> x -> 1 - (1 - x) * (1 - x);
                case "easeInOutQuad" -> x -> x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2;
                case "easeInOutCubic" -> x -> x < 0.5 ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2;
                case "easeInOutBack" -> x -> x < 0.5
                        ? (Math.pow(2 * x, 2) * ((C2 + 1) * 2 * x - C2)) / 2
                        : (Math.pow(2 * x - 2, 2) * ((C2 + 1) * (x * 2 - 2) + C2) + 2) / 2;
                case "easeOutBounce" -> Ease::bounceOut;
                case "easeInOutBounce" -> x -> x < 0.5 ? (1 - bounceOut(1 - 2 * x)) / 2 : (1 + bounceOut(2 * x - 1)) / 2;
                case "step" -> x -> x < 1 ? 0 : 1;
                default -> LINEAR;
            };
        }

        private static double bounceOut(double x) {
            double n1 = 7.5625, d1 = 2.75;
            if (x < 1 / d1) return n1 * x * x;
            if (x < 2 / d1) return n1 * (x -= 1.5 / d1) * x + 0.75;
            if (x < 2.5 / d1) return n1 * (x -= 2.25 / d1) * x + 0.9375;
            return n1 * (x -= 2.625 / d1) * x + 0.984375;
        }
    }
}
