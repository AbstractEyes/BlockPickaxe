package com.abstractphil.customtools.effects;

import com.redmancometh.reditems.abstraction.AttachmentEffect;
import com.redmancometh.reditems.abstraction.BlockBreakEffect;
import com.redmancometh.reditems.abstraction.HeldEffect;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;

public class CrateEffect extends AbstractToolEffect implements HeldEffect, BlockBreakEffect, AttachmentEffect {

    @Override
    public void broke(BlockBreakEvent blockBreakEvent, int i) {
        if(blockBreakEvent.isCancelled()) return;
        if(isBlacklistedBroke(blockBreakEvent.getBlock(), blockBreakEvent.getPlayer(), this.getName())) return;
        if(blockBreakEvent.getBlock().hasMetadata("BLOCK_PLACED")) return;
        if(blockBreakEvent.getPlayer() != null
                && randomCheck(blockBreakEvent.getPlayer())) {
            // Dispatch command for monthly crate.
            Bukkit.dispatchCommand(
                    Bukkit.getConsoleSender(),
                    getUtils().prepareCommand(
                            blockBreakEvent.getPlayer(),
                            getData().getCommands().get(0),
                            null));

        }
    }

}