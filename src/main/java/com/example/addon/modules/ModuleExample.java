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
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

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
        .description("The hotbar slot which your bow is in.")
        .defaultValue(0)
        .min(0)
        .sliderMax(8)
        .build()
    );

    private final Setting<Integer> swordSlot = sgGeneral.add(new IntSetting.Builder()
        .name("sword-slot")
        .description("The hotbar slot which your sword is in.")
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
    private void onRender3d(Render3DEvent event) {
        // Create & stretch the marker object
        Box marker = new Box(BlockPos.ORIGIN);
        marker = marker.stretch(
            scale.get() * marker.getLengthX(),
            scale.get() * marker.getLengthY(),
            scale.get() * marker.getLengthZ()
        );

        // Render the marker based on the color setting
        event.renderer.box(marker, color.get(), color.get(), ShapeMode.Both, 0);
    }
}
