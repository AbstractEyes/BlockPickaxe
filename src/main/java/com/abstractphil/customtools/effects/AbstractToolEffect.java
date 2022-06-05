package com.abstractphil.customtools.effects;

import com.abstractphil.customtools.cfg.LootCommand;
import com.abstractphil.customtools.cfg.ToolEffectData;
import com.abstractphil.customtools.util.AbsPhilJsonUtil;
import com.abstractphil.customtools.util.AbsPhilItemUtils;
import com.google.gson.JsonObject;
import com.redmancometh.reditems.EnchantType;
import com.redmancometh.reditems.abstraction.Effect;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public abstract class AbstractToolEffect implements Effect {
    private ToolEffectData data;
    public void setData(ToolEffectData dataIn) { data = dataIn; }
    public ToolEffectData getData() { return data; }
    private AbsPhilItemUtils utils;
    public void setUtils(AbsPhilItemUtils utilsIn) { utils = utilsIn;}
    public AbsPhilItemUtils getUtils() { return utils; }

    public JsonObject getStatistics(Player player) {
        return getUtils().getJsonStatistics(player.getItemInHand());
    }

    @Override
    public ArrayList<String> getLore() {
        return (ArrayList<String>)data.getItemLore();
    }

    public ArrayList<String> getBakeLore(Player player) {
        ArrayList<String> outLore = new ArrayList<>();
        for(String line : data.getItemLore()) { outLore.add(line.replace("%l", String.valueOf(getLevel(player)))); }
        return outLore;
    }

    public ArrayList<String> getDisplayLore(Player player) {
        ArrayList<String> outLore = new ArrayList<>();
        for(String line : data.getGuiLore()) { outLore.add(line.replace("%l", String.valueOf(getLevel(player)))); }
        return outLore;
    }

    public boolean isWhitelistedBroke(Block blockIn, Player player) {
        return (getUtils().isWhitelistedBroke(blockIn, player));//; //return (getUtils().isWhitelistedBroke(blockIn, player, getUtils().getAxeEffect("blockpickaxe").getData().getWhitelist()));
    }
    public boolean isBlacklistedBroke(Block blockIn, Player player, String skillName) {
        return (getUtils().isBlacklistedBroke(blockIn, player, skillName));
    }

    @Override
    public String getName() {
        return data.getEffectName();
    }

    public String getDisplayName(Player player) {
        return data.getGuiName().replace("%l", String.valueOf(getLevel(player)));
    }

    @Override
    public EnchantType getType() {
        return null;
    }

    @Override
    public int getMaxNaturalLevel() {
        return data.getMaxLevel();
    }

    public int getLevel(Player player) {
        return AbsPhilJsonUtil.getEffectLevel(getUtils().getJsonStatistics(player.getItemInHand()), this.getName());
    }

    public boolean randomCheck(Player player) {
        float getRandom = ThreadLocalRandom.current().nextFloat();
        int level = getLevel(player);
        float chance = getData().getChancePerLevel();
        return (getRandom <= (level * chance));
    }

    public boolean lootRandomCheck(Player player, LootCommand command){
        float getRandom = ThreadLocalRandom.current().nextFloat();
        int level = getLevel(player);
        double chance = command.getChancePerLevel();
        return (getRandom <= (level * chance));
    }

    public void dispatchCommand(Player playerIn, String command, @Nullable Integer amountIn) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                getUtils().prepareCommand(playerIn, command, amountIn));
    }

    public void dispatchCommandPlayer(Player playerIn, String command, @Nullable Integer amountIn) {
        Bukkit.dispatchCommand(playerIn, getUtils().prepareCommand(playerIn, command, amountIn));
    }

}
