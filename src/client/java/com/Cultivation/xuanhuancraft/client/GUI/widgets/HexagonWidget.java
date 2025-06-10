package com.Cultivation.xuanhuancraft.client.GUI.widgets;

import com.Cultivation.xuanhuancraft.client.GUI.HexagonScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.apache.commons.codec.binary.Hex;
import org.lwjgl.glfw.GLFW;

public class HexagonWidget extends ClickableWidget
{
    private boolean isArrow = false;
    private Screen screen = MinecraftClient.getInstance().currentScreen;
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

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && screen.getClass() == HexagonScreen.class && isArrow==false)
        {
            isArrow = true;
            HexagonScreen hexagonScreen = (HexagonScreen) screen;
            hexagonScreen.DrawArrows(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
