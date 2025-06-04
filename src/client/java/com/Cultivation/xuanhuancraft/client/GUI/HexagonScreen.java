package com.Cultivation.xuanhuancraft.client.GUI;

import com.Cultivation.xuanhuancraft.Xuanhuancraft;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class HexagonScreen extends Screen
{
    public HexagonScreen(Text title)
    {
        super(title);
    }
    int hexRadius = 1;
    int Fsize = 32;
    int size = 30;
    public ButtonWidget IncreaseButton;
    public ButtonWidget DecreaseButton;
    @Override
    protected void init()
    {
        int centerx = width/2;
        int centery = height/2;
        IncreaseButton = ButtonWidget.builder(Text.literal("Increase Radius"), button -> {
            this.hexRadius += 1;
        }).dimensions(centerx - 205, 20, 200, 20).build();
        DecreaseButton = ButtonWidget.builder(Text.literal("Increase Radius"), button -> {
            this.hexRadius -= 1;
        }).dimensions(centerx+5, 20, 200, 20).build();

        addDrawableChild(IncreaseButton);
        addDrawableChild(DecreaseButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        Identifier texture = Identifier.of("xuanhuancraft", "textures/gui/hexagon.png");
        int centerx = width/2;
        int centery = height/2;
        for (int q = -hexRadius; q <= hexRadius; q++) {
            int r1 = Math.max(-hexRadius, -q - hexRadius);
            int r2 = Math.min(hexRadius, -q + hexRadius);
            for (int r = r1; r <= r2; r++) {
                // Convert axial to pixel
                float x = Fsize * (3f/4f * q);
                float y = Fsize * (r + q / 2f); // or use proper y-spacing formula for flat tops
                context.drawTexture(RenderLayer::getGuiTextured, texture, (int)x+centerx-size/2, (int)y+centery-size/2, 0, 0, size, size, size, size);

            }
        }
    }
}
