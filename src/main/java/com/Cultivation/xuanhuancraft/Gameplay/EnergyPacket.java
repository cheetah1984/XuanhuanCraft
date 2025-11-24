package com.Cultivation.xuanhuancraft.Gameplay;

import java.util.EnumSet;

public class EnergyPacket
{
    private EnumSet<BaseElement> elements;
    private double purity;
    private Tier tier;

    public EnergyPacket()
    {
        this(EnumSet.allOf(BaseElement.class), 0.0, Tier.BASE);
    }

    public EnergyPacket(EnumSet<BaseElement> elements, double purity, Tier tier)
    {
        this.elements = elements.clone();
        this.purity = clamp(purity);
        this.tier = tier;
        normalizeTier();
    }

    private void normalizeTier()
    {
        if (tier == Tier.EVOLVED)
        {
            return;
        }
        if (elements.size() == 1)
        {
            tier = Tier.BASE;
        }
        else if (elements.size() == 2)
        {
            tier = Tier.FUSED;
        }
        else
        {
            tier = Tier.BASE;
        }
    }

    public EnumSet<BaseElement> getElements()
    {
        return elements;
    }

    public double getPurity()
    {
        return purity;
    }

    public void setPurity(double purity)
    {
        this.purity = clamp(purity);
    }

    public Tier getTier()
    {
        return tier;
    }

    public void setTier(Tier tier)
    {
        this.tier = tier;
    }

    public boolean contains(BaseElement element)
    {
        return elements.contains(element);
    }

    public boolean isSingleElement()
    {
        return elements.size() == 1;
    }

    public boolean isFusedPair()
    {
        return elements.size() == 2 && tier != Tier.EVOLVED;
    }

    public void collapseTo(BaseElement element)
    {
        elements = EnumSet.of(element);
        normalizeTier();
    }

    public void evolve()
    {
        tier = Tier.EVOLVED;
    }

    public static EnergyPacket fused(BaseElement first, BaseElement second, double purity)
    {
        return new EnergyPacket(EnumSet.of(first, second), purity, Tier.FUSED);
    }

    public static EnergyPacket single(BaseElement element, double purity)
    {
        return new EnergyPacket(EnumSet.of(element), purity, Tier.BASE);
    }

    private double clamp(double value)
    {
        return Math.max(0.0, Math.min(100.0, value));
    }
}
