package com.Cultivation.xuanhuancraft.DataStructures;

public class AxialCordinate {
    private int a;
    private int r;
    private int c;
    private String texture = "textures/gui/hexagon.png";

    public AxialCordinate(int a, int r, int c)
    {
        this.a = a;
        this.r = r;
        this.c = c;
    }

    public AxialCordinate(int a, int r, int c, String texture)
    {
        this.a = a;
        this.r = r;
        this.c = c;
        this.texture = texture;
    }

    public int getA()
    {
        return this.a;
    }

    public int getR()
    {
        return this.r;
    }

    public int getC()
    {
        return this.c;
    }

    public double[] ConvertToCartesian()
    {
        double x = 1.5 * this.a;
        double y = Math.sqrt(3)/2 * this.a + Math.sqrt(3) * this.r;
        return new double[]{x, y};
    }
}
