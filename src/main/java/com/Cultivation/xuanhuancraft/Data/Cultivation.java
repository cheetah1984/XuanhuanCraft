package com.Cultivation.xuanhuancraft.Data;

import java.util.HashMap;
import java.util.Objects;

public class Cultivation {
    public Double Qi = 0.0;
    public HashMap<Integer, String> Realms = new HashMap<>();
    public String CurrentRealm = "Mortal";
    public int CurrentReealmID = 0;

    public Double QiCostToNextRealm = 0.0;
    public int BaseCost = 100;

    public void CheckForBreakthrough() {
        if(Objects.equals(Qi, QiCostToNextRealm)) {
            CurrentReealmID++;
            CurrentRealm = Realms.get(CurrentReealmID);

            QiCostToNextRealm = (BaseCost * (Math.pow(CurrentReealmID, 2)));
        }
    }


    public void SetValues() {
        Realms.put(0, "Mortal");
    }

}
