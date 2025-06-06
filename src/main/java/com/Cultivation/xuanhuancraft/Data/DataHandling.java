package com.Cultivation.xuanhuancraft.Data;

import com.mojang.logging.LogUtils;
import net.minecraft.entity.player.PlayerEntity;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;

public class DataHandling {
    public static HashMap<PlayerEntity, Cultivation> DataList = new HashMap<>();
    private static final Logger LOGGER = LogUtils.getLogger();

    // This method should run every time the player joins a world
    public static void CreateDataIfMissing(PlayerEntity player) {
        File data = new File(player.getUuid() + ".cult");
        try {
            if (data.createNewFile()) {
                LOGGER.info("'{}.cult' Was Not Found, New Save Was Created", player.getUuid());
                GenerateDefaults(player);
            }
            else {
                LOGGER.info("File was detected, Loading '{}.cult'", player.getUuid());
                GenerateDefaults(player);
                Load(player);
                LOGGER.info("'{}.cult' Has Loaded Succesfully", player.getUuid());

            }
        }
        catch (IOException e) {
            LOGGER.error("ERROR: IOException catch was Triggered On CreateDataIfMissing(), Data will NOT load correctly (DataHandling.java)");
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }
    }
    // Needs rework? Defaults are set in Cultivation Class
    public static void GenerateDefaults(PlayerEntity player) {
        if (!DataList.containsKey(player)) {
            DataList.put(player, new Cultivation());
        }
    }

    // Both Save and Load methods are pretty self-explanatory, Caution when changing bc java file handling is a mess...
    public static void Save(PlayerEntity player) {
        String encode = Base64Util(SaveAndLoadHandling.GetDataOnString(player), true);
        try {
            FileWriter myWriter = new FileWriter(player.getUuid() + ".cult");
            myWriter.write(encode);
            myWriter.close();
        } catch (IOException e) {
            LOGGER.error("ERROR: IOException catch was Triggered On Save(), Data will NOT load correctly (DataHandling.java)");
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }
        LOGGER.info("'{}.clicker' was updated (Data Save)", player.getUuid());
    }

    public static void Load(PlayerEntity player) {
        String Base64Data = "0";
        try {
            File myObj = new File(player.getUuid() + ".cult");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                Base64Data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("ERROR: FileNotFoundException catch was Triggered On Load(), Data will NOT load correctly (DataHandling.java)");
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }

        DataList.replace(player, SaveAndLoadHandling.StringToStats(Base64Util(Base64Data, false)));
    }
    // Base64 Util for turning a string to base 64 and back to a string
    // ENCODE = True
    // DECODE = False
    public static String Base64Util(String input, boolean EncodeOrDecode) {
        if (EncodeOrDecode) {
            return Base64.getEncoder().encodeToString(input.getBytes());
        }
        else {
            return new String(Base64.getDecoder().decode(input.getBytes()));
        }
    }
}
