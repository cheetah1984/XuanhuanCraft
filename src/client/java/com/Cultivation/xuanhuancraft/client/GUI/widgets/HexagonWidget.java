package com.Cultivation.xuanhuancraft.client.GUI.widgets;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.codec.binary.Hex;

public class HexagonWidget extends ClickableWidget
{
    public HexagonWidget(int x, int y, int width, int height)
    {
        super(x,y,width,height, Text.empty());
    }
    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta)
    {
        String textureLocation = "textures/gui/hexagon.png";
        if (isHovered())
        {
            textureLocation = "textures/gui/hoveredhexagon.png";
        }
        Identifier Hexagon = Identifier.of("xuanhuancraft", textureLocation);
        context.drawTexture(RenderLayer::getGuiTextured, Hexagon, getX(),getY(),0,0,getWidth(),getHeight(),getWidth(),getHeight());
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}
