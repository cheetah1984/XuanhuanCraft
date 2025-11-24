package com.Cultivation.xuanhuancraft.DataStructures;

import net.minecraft.util.Identifier;

import static com.Cultivation.xuanhuancraft.Xuanhuancraft.MOD_ID;

public class CultivationTile
{
    int orientation = 0;
    Identifier texture = Identifier.of(MOD_ID, "textures/gui/hexagon.png");
    int qiValue = 0;
    String Name;



    public CultivationTile() {}

    public CultivationTile(String texture, int qiValue, boolean Active, String name)
    {
        this.texture = Identifier.of(texture);
        this.qiValue = qiValue;
        this.Name = name;
    }

    public CultivationTile(int orientation)
    {
        this.orientation = orientation;
    }

}
