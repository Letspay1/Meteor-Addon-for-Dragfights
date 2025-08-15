package com.example.addon.modules;

import com.example.addon.AddonTemplate;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.utils.player.InvUtils;

public class DetectionSwap extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgRender = this.settings.createGroup("Render");

    /**
     * Example setting.
     * The {@code name} parameter should be in kebab-case.
     * If you want to access the setting from another class, simply make the setting {@code public}, and use
     * {@link meteordevelopment.meteorclient.systems.modules.Modules#get(Class)} to access the {@link Module} object.
     */
    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("The range which entities are detected in.")
        .defaultValue(3)
        .min(0)
        .sliderMax(3)
        .build()
    );

    private final Setting<Integer> bowSlot = sgGeneral.add(new IntSetting.Builder()
        .name("bow-slot")
        .description("The hotbar slot which your bow is in -1.")
        .defaultValue(0)
        .min(0)
        .sliderMax(8)
        .build()
    );

    private final Setting<Integer> swordSlot = sgGeneral.add(new IntSetting.Builder()
        .name("sword-slot")
        .description("The hotbar slot which your sword is in -1.")
        .defaultValue(0)
        .min(0)
        .sliderMax(8)
        .build()
    );

    

    /**
     * The {@code name} parameter should be in kebab-case.
     */
    public DetectionSwap() {
        super(AddonTemplate.CATEGORY, "detection-swap", "An example module that highlights the center of the world.");
    }

    /**
     * Example event handling method.
     * Requires {@link AddonTemplate#getPackage()} to be setup correctly, otherwise the game will crash whenever the module is enabled.
     */
    @EventHandler
    private void onTick(TickEvent.Pre event) {
        // Entity range detection
        for (Entity entity : mc.world.getEntities()) {
            if (isWithinRange(entity)) {
                // Within range, swap to sword
                InvUtils.swap(swordSlot.get(), false);
                return;
            }
        }

        // Not within range, swap to bow
        InvUtils.swap(bowSlot.get(), false);
    }

    private boolean isWithinRange(Entity entity) {
        Box hitbox = entity.getBoundingBox();

        double clampedX = MathHelper.clamp(mc.player.getX(), hitbox.minX, hitbox.maxX);
        double clampedY = MathHelper.clamp(mc.player.getY(), hitbox.minY, hitbox.maxY);
        double clampedZ = MathHelper.clamp(mc.player.getZ(), hitbox.minZ, hitbox.maxZ);

        return PlayerUtils.isWithin(clampedX, clampedY, clampedZ, range.get());
    }
}
