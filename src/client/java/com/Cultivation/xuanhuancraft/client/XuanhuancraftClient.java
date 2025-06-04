package com.Cultivation.xuanhuancraft.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class XuanhuancraftClient implements ClientModInitializer {
    private static KeyBinding keyBinding;
    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.xuanhuancraft.spook", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.xuanhuancraft.test"
        ));
    }
}
