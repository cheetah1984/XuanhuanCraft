package com.Cultivation.xuanhuancraft.client;

import com.Cultivation.xuanhuancraft.client.Gui.CultivationScreen;
import com.Cultivation.xuanhuancraft.client.Gui.RenderingHUD;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class XuanhuancraftClient implements ClientModInitializer {
    private static KeyBinding Cultivationscreen;
    @Override
    public void onInitializeClient() {
        Cultivationscreen = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.xuanhuancraft.hexgrid",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.xuanhuancraft.test"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (Cultivationscreen.wasPressed() && client.world != null && client.currentScreen == null)
                {
                    client.setScreen(new CultivationScreen());
                }
            else if (Cultivationscreen.wasPressed() && client.world != null && client.currentScreen instanceof CultivationScreen)
                {
                    client.setScreen(null);
                }
        });
        // Register Stuff
        RenderingHUD.RegisterHud();
    }
}
