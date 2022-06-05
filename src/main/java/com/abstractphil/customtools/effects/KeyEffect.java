package com.abstractphil.customtools.effects;

import com.abstractphil.customtools.cfg.LootCommand;
import com.redmancometh.reditems.abstraction.BlockBreakEffect;
import com.redmancometh.reditems.abstraction.HeldEffect;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;

public class KeyEffect extends AbstractToolEffect implements HeldEffect, BlockBreakEffect {

    @Override
    public void broke(BlockBreakEvent blockBreakEvent, int i) {
        if(blockBreakEvent.isCancelled()) return;
        if(isBlacklistedBroke(blockBreakEvent.getBlock(), blockBreakEvent.getPlayer(), this.getName())) return;
        if(blockBreakEvent.getPlayer() != null
                && randomCheck(blockBreakEvent.getPlayer())) {
            // Dispatch command for monthly crate.
            for (Map.Entry<String, LootCommand> entry : this.getData().getLoot().entrySet()) {
                String name = entry.getKey();
                LootCommand loots = entry.getValue();
                if (lootRandomCheck(blockBreakEvent.getPlayer(), loots)) {
                    Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(),
                            getUtils().prepareCommand(
                                    blockBreakEvent.getPlayer(), loots.getCommand(),
                                    null));
                    break;
                }
            }

        }
    }
}