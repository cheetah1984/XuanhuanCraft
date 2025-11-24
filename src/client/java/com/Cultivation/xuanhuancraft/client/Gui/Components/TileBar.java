package com.Cultivation.xuanhuancraft.client.Gui.Components;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.DrawContext;
import org.slf4j.Logger;

public class TileBar {
    private int x, y, width, height;
    private float cameraX = 0, cameraY = 0;
    private float zoom = 1.0f;
    private static final Logger LOGGER = LogUtils.getLogger();

    public TileBar(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void render(DrawContext context, float delta)
    {
        context.fill(x, y, x + width, y+height, 0xFF444444);
    }

    public boolean isInBounds(double mouseX, double mouseY)
    {
        return mouseX>=this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        LOGGER.info("Mouse has clicked on the TileBar");
        return true;
    }

}
