package com.Cultivation.xuanhuancraft.client.GUI;

import com.Cultivation.xuanhuancraft.client.GUI.widgets.HexagonWidget;
import com.Cultivation.xuanhuancraft.client.GUI.widgets.TransMitterWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class HexagonScreen extends Screen
{
    private HexagonWidget hexagonWidget;
    public HexagonScreen(Text title)
    {
        super(title);
    }
    int hexRadius = 1;
    int Fsize = 36;
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
            clearChildren();
            init();
        }).dimensions(centerx - 205, 20, 200, 20).build();
        DecreaseButton = ButtonWidget.builder(Text.literal("Decrease Radius"), button -> {
            this.hexRadius -= 1;
            clearChildren();
            init();
        }).dimensions(centerx+5, 20, 200, 20).build();
        for (int q = -this.hexRadius; q <= this.hexRadius; q++) {
            int r1 = Math.max(-this.hexRadius, -q - this.hexRadius);
            int r2 = Math.min(this.hexRadius, -q + this.hexRadius);
            for (int r = r1; r <= r2; r++) {
                float x = Fsize * (3f/4f * q);
                float y = Fsize * (r + q / 2f);
                int LocationX = (int)x+centerx-size/2;
                int LocationY = (int)y+centery-size/2;
                // shitcode fix later #laternevercomes, this "estimates" the rough position of the hexagon by assuming its a cube
                // then chooses a differnt texture if it is within those bounds
                // shitcode fixed (widgets exist)
                hexagonWidget = new HexagonWidget(LocationX,LocationY,size, size);
                this.addDrawableChild(hexagonWidget);
            }
        }
        addDrawableChild(IncreaseButton);
        addDrawableChild(DecreaseButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        super.render(context,mouseX,mouseY,delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void DrawArrows(int x, int y, int width, int height)
    {
        TransMitterWidget transMitterWidget = new TransMitterWidget(x,y,width, height);
        this.addDrawableChild(transMitterWidget);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}

