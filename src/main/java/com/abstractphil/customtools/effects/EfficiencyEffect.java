package com.abstractphil.customtools.effects;

import com.redmancometh.reditems.abstraction.HeldEffect;
import com.redmancometh.reditems.abstraction.TickingWeaponEffect;
import org.bukkit.entity.Player;

public class EfficiencyEffect extends AbstractToolEffect implements TickingWeaponEffect, HeldEffect {

    @Override
    public void onTick(Player player, int i) {
        /*
        try {
            if(player != null && !player.getWorld().getName().equalsIgnoreCase("zone-1"))
                player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, getLevel(player)-1), true);
        } catch (Exception ex ){
            ex.printStackTrace();
        }*/
    }

}