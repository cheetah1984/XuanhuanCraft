package com.Cultivation.xuanhuancraft.Data;

import com.Cultivation.xuanhuancraft.DataStructures.AxialCordinate;
import com.Cultivation.xuanhuancraft.DataStructures.Cultivation;
import com.Cultivation.xuanhuancraft.DataStructures.CultivationTile;
import com.Cultivation.xuanhuancraft.Gameplay.BaseElement;
import com.Cultivation.xuanhuancraft.Gameplay.TileType;
import com.mojang.logging.LogUtils;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;

import java.util.StringJoiner;

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

        StringJoiner gridJoiner = new StringJoiner("~");
        for (AxialCordinate coordinate : cultivation.Grid)
        {
            StringBuilder entry = new StringBuilder();
            entry.append(coordinate.getX()).append("_").append(coordinate.getY());
            CultivationTile tile = coordinate.getTile();
            if (tile != null)
            {
                entry.append(":")
                        .append(tile.getType().name()).append("|")
                        .append(tile.getOrientation()).append("|")
                        .append(tile.getFocusElement() == null ? "NONE" : tile.getFocusElement().name());
            }
            gridJoiner.add(entry.toString());
        }
        result.append(gridJoiner);
        return result.toString();
    }
    // Turns a String back into Data
    public static Cultivation StringToStats(String data) {
        Cultivation cult = new Cultivation();
        String[] SplitData = data.split(",");
        cult.Qi = Double.valueOf(SplitData[0]);
        if (SplitData.length > 1 && !SplitData[1].isEmpty())
        {
            String[] splitGrid = SplitData[1].split("~");
            for (String SplitGrid : splitGrid)
            {
                if (SplitGrid.isEmpty())
                {
                    continue;
                }
                String[] coordinateAndTile = SplitGrid.split(":");
                String[] cords = coordinateAndTile[0].split("_");
                if (cords.length < 2)
                {
                    continue;
                }
                int x = Integer.parseInt(cords[0]);
                int y = Integer.parseInt(cords[1]);
                CultivationTile tile = null;
                if (coordinateAndTile.length > 1 && !coordinateAndTile[1].isEmpty())
                {
                    String[] tileData = coordinateAndTile[1].split("\\|");
                    if (tileData.length > 0 && !tileData[0].isEmpty())
                    {
                        TileType type = TileType.valueOf(tileData[0]);
                        int orientation = tileData.length > 1 ? Integer.parseInt(tileData[1]) : 0;
                        BaseElement focus = tileData.length > 2 && !tileData[2].equals("NONE") ? BaseElement.valueOf(tileData[2]) : null;
                        tile = new CultivationTile(orientation, type);
                        tile.setFocusElement(focus);
                    }
                }
                AxialCordinate axiom = new AxialCordinate(x, y, tile);
                cult.Grid.add(axiom);
            }
        }
        return cult;
    }
}
