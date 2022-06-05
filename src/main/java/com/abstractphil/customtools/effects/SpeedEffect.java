package com.abstractphil.customtools.effects;

import com.redmancometh.reditems.abstraction.HeldEffect;
import com.redmancometh.reditems.abstraction.TickingWeaponEffect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedEffect extends AbstractToolEffect implements HeldEffect, TickingWeaponEffect {

    @Override
    public void onTick(Player player, int i) {
        try {
            if(player != null)
                if(!player.getWorld().getName().equalsIgnoreCase("warzone"))
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, getLevel(player)-1), true);
        } catch (Exception ex ){
            ex.printStackTrace();
        }
    }

}