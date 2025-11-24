package com.Cultivation.xuanhuancraft.client.Gui.Components;

import com.Cultivation.xuanhuancraft.Data.DataHandling;
import com.Cultivation.xuanhuancraft.DataStructures.AxialCordinate;
import com.Cultivation.xuanhuancraft.DataStructures.Cultivation;
import com.Cultivation.xuanhuancraft.client.Gui.CultivationScreen;
import com.mojang.logging.LogUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import static com.Cultivation.xuanhuancraft.Data.DataHandling.ConvertToHex;
import static com.Cultivation.xuanhuancraft.Xuanhuancraft.MOD_ID;

public class HexGrid {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final int cameraX = 0;
    private final int cameraY = 0;
    private final float zoom = 1.0f;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static PlayerEntity player = MinecraftClient.getInstance().player;
    private Cultivation cultivation = DataHandling.DataList.get(player);

    public HexGrid(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }



    public void render(DrawContext context, float delta)
    {
        Identifier hexagon = Identifier.of(MOD_ID, "textures/gui/hexagon.png");
        int xcenter = this.x + width/2;
        int ycenter = this.y + height/2;
        int size = 16;
        for (AxialCordinate axialCordinate : cultivation.Grid)
        {

            float zoom = Math.max(1, this.zoom);
            int xcord = (int) (axialCordinate.ConvertToCartesian()[0] * 10);
            int ycord = (int) (axialCordinate.ConvertToCartesian()[1] * 10);
            int drawX = (int)(xcord / zoom + xcenter - (size / (2 * zoom)));
            int drawY = (int)(ycord / zoom + ycenter - (size / (2 * zoom)));
            context.drawTexture(RenderLayer::getGuiTextured, hexagon,
                    drawX,
                    drawY,
                    0,
                    0,
                    (int) (16/zoom),
                    (int) (16/zoom),
                    16,
                    16);
        }

    }

    public boolean isInBounds(double mouseX, double mouseY)
    {
        return mouseX >= this.x
                && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT)
        {
            double xpos = (mouseX - (this.x + width/2.0))/8;
            double ypos = (mouseY - (this.y + height/2.0))/8;
            int[] cords = ConvertToHex(xpos, ypos);
            LOGGER.info("Clicked on HEX pos ({}, {}, {})", cords[0], cords[1], cords[2]);

        }
        LOGGER.info("Mouse has clicked on the hexgrid");
        return true;
    }
}
