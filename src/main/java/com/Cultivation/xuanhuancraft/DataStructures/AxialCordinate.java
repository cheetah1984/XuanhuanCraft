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

    public void setTile(CultivationTile tile)
    {
        this.tile = tile;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AxialCordinate that = (AxialCordinate) obj;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode()
    {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
