package com.abstractphil.customtools.effects;

import com.abstractphil.customtools.util.AbsPhilItemUtils;
import com.redmancometh.reditems.abstraction.BlockBreakEffect;
import com.redmancometh.reditems.abstraction.HeldEffect;
import com.redmancometh.reditems.abstraction.TickingWeaponEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.concurrent.ThreadLocalRandom;

public class AutoSmeltEffect extends AbstractToolEffect implements HeldEffect, TickingWeaponEffect, BlockBreakEffect {

    @Override
    public void broke(BlockBreakEvent blockBreakEvent, int i) {
        if(blockBreakEvent.getPlayer() != null) {
            if(blockBreakEvent.isCancelled()) return;
            if(!isWhitelistedBroke(blockBreakEvent.getBlock(), blockBreakEvent.getPlayer())) return;
            if(isBlacklistedBroke(blockBreakEvent.getBlock(), blockBreakEvent.getPlayer(), this.getName())) return;
            int fortuneValue = (int)Math.ceil(getData().getMultiplierPerLevel() * getLevel(blockBreakEvent.getPlayer()));
            if(randomCheck(blockBreakEvent.getPlayer())) {
                //if(getUtils().isPumpkinMask(blockBreakEvent.getPlayer().getInventory().getHelmet())) fortuneValue += 3;
                double outCount = ThreadLocalRandom.current().nextInt(1, (int) Math.ceil(fortuneValue * 2));
                if(blockBreakEvent.getBlock().getDrops().size() > 0){
                    int distributedCount = (int)Math.ceil(outCount / blockBreakEvent.getBlock().getDrops().size());
                    for (ItemStack drop : blockBreakEvent.getBlock().getDrops()) {
                        MaterialData sanitized = AbsPhilItemUtils.sanitizeMaterial(blockBreakEvent.getBlock(), drop.getType());
                        blockBreakEvent.getPlayer().getInventory().addItem(
                                new ItemStack(sanitized.getItemType(), drop.getAmount() + distributedCount, sanitized.getData()));

                        ItemStack item = blockBreakEvent.getPlayer().getItemInHand();
                        getUtils().addSafeJsonAmountInt(blockBreakEvent.getPlayer(), item, getData().getEffectName(), (int)outCount, true);
                    }
                }
                blockBreakEvent.setDoesDrop(false);
            }
        }
    }

    @Override
    public void onTick(Player player, int i) {

    }
}
