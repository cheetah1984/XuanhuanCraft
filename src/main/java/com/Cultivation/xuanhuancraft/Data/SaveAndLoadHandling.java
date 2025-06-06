package com.Cultivation.xuanhuancraft.Data;

import net.minecraft.entity.player.PlayerEntity;

import java.math.BigInteger;

public class SaveAndLoadHandling {
    // READ IF YOU WANT TO ADD A VARIABLE (MAKE SURE SAID VARIABLE IS STORED IN THE Cultivation CLASS)
    // To add a variable to be stored you need to add said variable to the string builder followed by a ','
    // Like 'result.append(DataHandling.DataList.get(player).[Variable you want to store]).append(",");'
    // And also turn it back into data using the StringToStats Method
    // REMEMBER THE ORDER!!
    // Qi = 0
    // CurrentRealm = 1
    // And so on.. if you were to add a variable after BaseCost it would be 5
    // One last thing, there is no safe case for when data is changed yet, so if you add a variable, your last save will crash
    // Because of it having incomplete data, So make sure to delete it

    // Turns a PlayerEntity Data Hasmap into a string for saving (its more convenient this way)
    public static String GetDataOnString(PlayerEntity player) {
        StringBuilder result = new StringBuilder();
        result.append(DataHandling.DataList.get(player).Qi).append(",");
        result.append(DataHandling.DataList.get(player).CurrentRealm).append(",");
        result.append(DataHandling.DataList.get(player).CurrentReealmID).append(",");
        result.append(DataHandling.DataList.get(player).QiCostToNextRealm).append(",");
        result.append(DataHandling.DataList.get(player).BaseCost).append(",");
        return result.toString();
    }
    // Turns a String back into Data
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
