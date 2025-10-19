package com.Cultivation.xuanhuancraft.DataStructures;

public class AxialCordinate {
    private int x;
    private int y;
    private CultivationTile tile;

    public AxialCordinate(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public AxialCordinate(int x, int y, CultivationTile tile)
    {
        this.x = x;
        this.y = y;
        this.tile = tile;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public CultivationTile getTile()
    {
        return this.tile;
    }
}
