package com.Cultivation.xuanhuancraft.DataStructures;

import net.minecraft.util.Identifier;

public class CultivationTile
{
    int orientation;
    Identifier texture;

    public CultivationTile()
    {
        this.orientation = 0;
    }

    public CultivationTile(int orientation)
    {
        this.orientation = orientation;
    }

}
