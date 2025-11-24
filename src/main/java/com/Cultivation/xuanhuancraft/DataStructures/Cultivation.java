package com.Cultivation.xuanhuancraft.DataStructures;

import java.util.ArrayList;
import java.util.HashMap;

import com.Cultivation.xuanhuancraft.Gameplay.HexGridGame;

public class Cultivation {
    // SHOULD ONLY BE CALLED THROUGH THE HASHMAP
    // Main Class where Data is stored and interactions cultivation related happen
    // Also add a variable here if you want to store it in the file

    public Double Qi = 0.0;
    public HashMap<Integer, String> Realms = new HashMap<>();
    public ArrayList<AxialCordinate> Grid = new ArrayList<AxialCordinate>();
    public HexGridGame hexGridGame = new HexGridGame(this);

    public HexGridGame getHexGridGame()
    {
        return hexGridGame;
    }
}
