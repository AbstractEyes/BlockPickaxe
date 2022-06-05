package com.abstractphil.customtools.effects;

import com.abstractphil.customtools.ToolMain;
import com.abstractphil.customtools.util.AbsPhilItemUtils;
import com.redmancometh.reditems.abstraction.BlockBreakEffect;
import com.redmancometh.reditems.abstraction.ClickEffect;
import com.redmancometh.reditems.abstraction.HeldEffect;
import com.redmancometh.reditems.abstraction.TickingEffect;
import com.redmancometh.warcore.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.List;
import java.util.function.Supplier;

public class BlockPickaxeToolEffect extends AbstractToolEffect implements HeldEffect, TickingEffect, ClickEffect, BlockBreakEffect {

    @Override
    public void broke(BlockBreakEvent blockBreakEvent, int i) {
        if(blockBreakEvent.isCancelled()) return;
        if(!isWhitelistedBroke(blockBreakEvent.getBlock(), blockBreakEvent.getPlayer())) return;
        if(isBlacklistedBroke(blockBreakEvent.getBlock(), blockBreakEvent.getPlayer(), this.getName())) return;
        Player player = blockBreakEvent.getPlayer();
        getUtils().addSafeJsonAmountInt(blockBreakEvent.getPlayer(),
                blockBreakEvent.getPlayer().getItemInHand(), getData().getEffectName(), 1, true);
        for (ItemStack drop : blockBreakEvent.getBlock().getDrops()) {
            MaterialData sanitized = AbsPhilItemUtils.sanitizeMaterial(blockBreakEvent.getBlock(), drop.getType());
            blockBreakEvent.getPlayer().getInventory().addItem(
                    new ItemStack(sanitized.getItemType(), drop.getAmount(), sanitized.getData()));
            ItemStack item = blockBreakEvent.getPlayer().getItemInHand();
            getUtils().addSafeJsonAmountInt(blockBreakEvent.getPlayer(), item, getData().getEffectName(), drop.getAmount(), true);
        }

        //blockBreakEvent.getPlayer().getInventory().addItem(new ItemStack(Material.PUMPKIN, 1));
        if(!AbsPhilItemUtils.hasEffect(player.getItemInHand(), "toolfort")){
            blockBreakEvent.setDoesDrop(false);
        }
    }

    @Override
    public void onRightClick(PlayerInteractEvent playerInteractEvent, int i) {
        if (playerInteractEvent.getPlayer() != null) {
            if (playerInteractEvent.getPlayer().isSneaking()
                    && (playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR
                    || playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK)) {

                Bukkit.dispatchCommand(playerInteractEvent.getPlayer(), "abstoolshop");
            }
        }
    }

    @Override
    public void onLeftClick(PlayerInteractEvent playerInteractEvent, int i) {
    }

    @Override
    public List<Pair<String, Supplier<String>>> placeholders() {
        return super.placeholders();
    }

    @Override
    public Pair<Boolean, String> hasBuffType(ItemStack item) {
        return super.hasBuffType(item);
    }

    @Override
    public boolean applicableFor(ItemStack item) {
        return super.applicableFor(item);
    }

    @Override
    public void onTick(Player player, int i) {
    }
}
