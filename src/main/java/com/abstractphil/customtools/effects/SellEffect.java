package com.abstractphil.customtools.effects;

import com.redmancometh.reditems.RedItems;
import com.redmancometh.reditems.abstraction.*;
import com.redmancometh.reditems.mediator.EnchantManager;
import com.redmancometh.reditems.storage.SimpleContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class SellEffect extends AbstractToolEffect implements HeldEffect, ClickedCharm {

    private boolean hasTicker(Player player) {
        return getStatistics(player).has("autosell-ticker");
    }

    private void setTicks(Player player, int ticks) {
        getStatistics(player).addProperty("autosell-ticker", ticks);
    }

    private int getTicks(Player player) {
        return getStatistics(player).get("autosell-ticker").getAsInt();
    }

    private void addTicks(Player player, int addTicks) {
        getStatistics(player).addProperty("autosell-ticker", getTicks(player) + addTicks);
    }

    @Override
    public void onTick(Player player, int i) {
        if(player == null || getUtils() == null) return;
        if(!getUtils().isAbsTool(player.getItemInHand())) return;
        boolean autosell = getStatistics(player).has("autosell") &&
                getStatistics(player).get("autosell").getAsBoolean();
        if(autosell) {
            if(!hasTicker(player))
                getStatistics(player).addProperty("autosell-ticker", getData().getCooldown());
            addTicks(player, -1);
            //System.out.println("Counting Ticks: " + getTicks(player));
            if(getTicks(player) <= 0) {
                setTicks(player, getData().getCooldown());
                dispatchCommandPlayer(player, getData().getCommands().get(0), null);
            }
        }
    }

    @Override
    public void onRightClick(PlayerInteractEvent playerInteractEvent, int i) {
        if(playerInteractEvent.getPlayer().isSneaking()) return;
        //System.out.println("RIGHT CLICK SELL");
        Player player = playerInteractEvent.getPlayer();
        EnchantManager em = RedItems.getInstance().getEnchantManager();
        SimpleContainer dataContainer = em.getData(player.getItemInHand()).get();
        //System.out.println(dataContainer.getData());
        boolean autosell = dataContainer.getData().has("autosell") &&
                !dataContainer.getData().get("autosell").getAsBoolean();
        dataContainer.getData().addProperty("autosell", autosell);

        player.setItemInHand(dataContainer.commit(player.getItemInHand()));
        setTicks(player, getData().getCooldown());
        String boolCheck; if (autosell) boolCheck = "ON"; else boolCheck = "OFF";
        playerInteractEvent.getPlayer().sendMessage("Auto-sell is now: " + boolCheck);
        dispatchCommandPlayer(player, getData().getCommands().get(0), null);
    }

    @Override
    public void onLeftClick(PlayerInteractEvent playerInteractEvent, int i) { }
}