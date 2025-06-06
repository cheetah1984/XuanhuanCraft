package com.Cultivation.xuanhuancraft;

import com.Cultivation.xuanhuancraft.Data.DataHandling;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class Xuanhuancraft implements ModInitializer {
    public static String MOD_ID = "xuanhuancraft";
    @Override
    public void onInitialize() {
        // TEMPORARY CODE!!!
        // Should move this to their own EVENT Class
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            DataHandling.CreateDataIfMissing(handler.player);
        });
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            DataHandling.Save(handler.player);
        });

    }
}
