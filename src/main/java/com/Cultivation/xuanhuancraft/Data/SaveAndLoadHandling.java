package com.Cultivation.xuanhuancraft.Data;

import net.minecraft.entity.player.PlayerEntity;

import java.math.BigInteger;

public class SaveAndLoadHandling {
    public static String GetDataOnString(PlayerEntity player) {
        StringBuilder result = new StringBuilder();
        result.append(DataHandling.DataList.get(player).Qi).append(",");
        result.append(DataHandling.DataList.get(player).CurrentRealm).append(",");
        result.append(DataHandling.DataList.get(player).CurrentReealmID).append(",");
        result.append(DataHandling.DataList.get(player).QiCostToNextRealm).append(",");
        result.append(DataHandling.DataList.get(player).BaseCost).append(",");
        return result.toString();
    }
    public static Cultivation StringToStats(String data) {
        Cultivation cult = new Cultivation();
        String[] SplitData = data.split(",");
        cult.Qi = Double.valueOf(SplitData[0]);
        cult.CurrentRealm = SplitData[1];
        cult.CurrentReealmID = Integer.parseInt(SplitData[2]);
        cult.QiCostToNextRealm = Double.valueOf(SplitData[3]);
        cult.BaseCost = Integer.parseInt(SplitData[4]);

        return cult;
    }
}
