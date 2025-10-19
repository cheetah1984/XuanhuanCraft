package com.Cultivation.xuanhuancraft.Data;

import com.Cultivation.xuanhuancraft.DataStructures.AxialCordinate;
import com.Cultivation.xuanhuancraft.DataStructures.Cultivation;
import com.mojang.logging.LogUtils;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;

public class SaveAndLoadHandling {
    public static final Logger LOGGER = LogUtils.getLogger();
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
        Cultivation cultivation = DataHandling.DataList.get(player);
        result.append(cultivation.Qi).append(",");
        for (int i = 0; i < cultivation.Grid.size(); i++)
        {
            result.append(cultivation.Grid.get(i).getX()).append("_");
            result.append(cultivation.Grid.get(i).getY()).append("~");
        }
        return result.toString();
    }
    // Turns a String back into Data
    public static Cultivation StringToStats(String data) {
        Cultivation cult = new Cultivation();
        String[] SplitData = data.split(",");
        cult.Qi = Double.valueOf(SplitData[0]);
        String[] splitGrid = SplitData[1].split("~");
        for (String SplitGrid : splitGrid) {
            LOGGER.info("{}", SplitGrid);
            String[] cords = SplitGrid.split("_");
            int x = Integer.parseInt(cords[0]);
            int y = Integer.parseInt(cords[1]);
            AxialCordinate axiom = new AxialCordinate(x, y);
            cult.Grid.add(axiom);
            LOGGER.info("({}, {})", x, y);
        }
        return cult;
    }
}
