package com.callippoonet.callippoosmp.data;

import com.callippoonet.callippoosmp.lore.PlayerLore;
import com.callippoonet.callippoosmp.lore.PlayerLoreState;

public class PlayerLoreData {

    public PlayerLore playerLore;
    public PlayerLoreState playerLoreState;

    public PlayerLoreData(PlayerLore playerLore, PlayerLoreState playerLoreState){
        this.playerLore = playerLore;
        this.playerLoreState = playerLoreState;
    }
}
