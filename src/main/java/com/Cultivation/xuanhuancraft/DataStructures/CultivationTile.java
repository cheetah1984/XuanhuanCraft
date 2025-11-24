package com.Cultivation.xuanhuancraft.DataStructures;

import com.Cultivation.xuanhuancraft.Gameplay.BaseElement;
import com.Cultivation.xuanhuancraft.Gameplay.TileType;
import net.minecraft.util.Identifier;

public class CultivationTile
{
    private int orientation;
    private Identifier texture;
    private TileType type;
    private BaseElement focusElement;

    public CultivationTile()
    {
        this(0, TileType.PATH);
    }

    public CultivationTile(int orientation)
    {
        this(orientation, TileType.PATH);
    }

    public CultivationTile(int orientation, TileType type)
    {
        this.orientation = orientation;
        this.type = type;
    }

    public int getOrientation()
    {
        return orientation;
    }

    public void setOrientation(int orientation)
    {
        this.orientation = orientation;
    }

    public Identifier getTexture()
    {
        return texture;
    }

    public void setTexture(Identifier texture)
    {
        this.texture = texture;
    }

    public TileType getType()
    {
        return type == null ? TileType.PATH : type;
    }

    public void setType(TileType type)
    {
        this.type = type;
    }

    public BaseElement getFocusElement()
    {
        return focusElement;
    }

    public void setFocusElement(BaseElement focusElement)
    {
        this.focusElement = focusElement;
    }
}
