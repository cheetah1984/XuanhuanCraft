package com.Cultivation.xuanhuancraft.client.Gui;

import com.Cultivation.xuanhuancraft.Data.DataHandling;
import com.Cultivation.xuanhuancraft.Xuanhuancraft;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class RenderingHUD {
    // OH GOD THIS NEEDS TO BE REWORKED LMAOOOO
    // Utility first, Looking good Later - Some smart person idk...

    // Should change the identifier??? will keep it like this for now
    private static final Identifier EXAMPLE_LAYER = Identifier.of(Xuanhuancraft.MOD_ID, "hud-example-layer");

    private static final Identifier outline = Identifier.of("xuanhuancraft", "textures/gui/hud_outline.png");

    public static void RegisterHud() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, EXAMPLE_LAYER, RenderingHUD::render));
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        // THIS HAS NO SCALING IT WILL LOOK TERRIBLE ON ANY OTHER SCREEN OTHER THAN 1980x1020p
        // (Specifically on GUI Scale 3)
        context.drawTexture(RenderLayer::getGuiTextured, outline, 0, 0, 0, 0, 100, 20, 100, 20);
        context.drawText(client.textRenderer, "Qi: "+ DataHandling.DataList.get(client.player).Qi + "/" +  DataHandling.DataList.get(client.player).QiCostToNextRealm, 5, 6, 0xFFFFFFFF, false);
    }
}
