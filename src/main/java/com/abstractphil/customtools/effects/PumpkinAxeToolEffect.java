package com.abstractphil.customtools.effects;

import com.abstractphil.customtools.util.AbsPhilItemUtils;
import com.redmancometh.reditems.abstraction.*;
import com.redmancometh.warcore.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Supplier;


public class PumpkinAxeToolEffect extends AbstractToolEffect implements HeldEffect, TickingEffect,  ClickEffect, BlockBreakEffect {

	@Override
	public void broke(BlockBreakEvent blockBreakEvent, int i) {
		//if(blockBreakEvent.isCancelled()) return;
		////if (!isPumpkinBroke(blockBreakEvent.getBlock(), blockBreakEvent.getPlayer()))
		////	return;
		//Player player = blockBreakEvent.getPlayer();
		//getUtils().addSafeJsonAmountInt(blockBreakEvent.getPlayer(),
		//		blockBreakEvent.getPlayer().getItemInHand(), getData().getEffectName(), 1, true);
		//blockBreakEvent.getPlayer().getInventory().addItem(new ItemStack(Material.PUMPKIN, 1));
		//if(!AbsPhilItemUtils.hasActiveEffect(player, "pfort") ||
		//		!AbsPhilItemUtils.hasActiveEffect(player, "pmask")){
		//	blockBreakEvent.setDoesDrop(false);
		//}
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
