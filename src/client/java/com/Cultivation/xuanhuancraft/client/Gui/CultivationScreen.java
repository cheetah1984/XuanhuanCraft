package com.Cultivation.xuanhuancraft.client.Gui;

import com.Cultivation.xuanhuancraft.client.Gui.Components.HexGrid;
import com.Cultivation.xuanhuancraft.client.Gui.Components.PlayerInfoBar;
import com.Cultivation.xuanhuancraft.client.Gui.Components.TileBar;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.slf4j.Logger;

public class CultivationScreen extends Screen {
    private HexGrid hexGrid;
    private PlayerInfoBar playerInfoBar;
    private TileBar tileBar;
    private static final Logger LOGGER = LogUtils.getLogger();
    public CultivationScreen() {
        super(Text.literal("Hex Screen"));
    }

    @Override
    protected void init()
    {
        int screenW = this.width;
        int screenH = this.height;

        hexGrid = new HexGrid(0, 0, screenW * 3/4, screenH * 3/4);
        tileBar = new TileBar(0, screenH * 3/4, screenW * 3/4, (int) (screenH * 0.25));
        playerInfoBar = new PlayerInfoBar(screenW * 3/4, 0, (int) (screenW * 0.25), screenH);
        LOGGER.info("Screen size: ({}, {})", screenW, screenH);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        hexGrid.render(context, deltaTicks);
        tileBar.render(context, deltaTicks);
        playerInfoBar.render(context, deltaTicks);
        renderBackground(context, mouseX, mouseY, deltaTicks);
        super.render(context, mouseX, mouseY, deltaTicks);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (hexGrid.isInBounds(mouseX,mouseY)) return hexGrid.mouseClicked(mouseX, mouseY, button);
        if (playerInfoBar.isInBounds(mouseX,mouseY)) return playerInfoBar.mouseClicked(mouseX, mouseY, button);
        if (tileBar.isInBounds(mouseX,mouseY)) return tileBar.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
