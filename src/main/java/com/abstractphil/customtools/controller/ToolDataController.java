package com.abstractphil.customtools.controller;

import com.abstractphil.customtools.cfg.ToolEffectData;
import com.abstractphil.customtools.effects.AbstractToolEffect;
import com.abstractphil.customtools.util.AbsPhilItemUtils;
import com.redmancometh.configcore.config.ConfigManager;
import com.abstractphil.customtools.cfg.ToolConfig;

import com.redmancometh.reditems.RedItems;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class ToolDataController {
	private ConfigManager<ToolConfig> cfg = new ConfigManager("blockpickaxe.json", ToolConfig.class);
	private Map<String, AbstractToolEffect> effectDataMap = new ConcurrentHashMap();
	private AbsPhilItemUtils utils;

	public void init() {
		cfg.init();
		Map<String, ToolEffectData> effects = cfg.getConfig().getEffects();
		prepareEffects(effects, effectDataMap);
		utils = new AbsPhilItemUtils(cfg.getConfig(), effectDataMap);
	}

	private void prepareEffects(Map<String, ToolEffectData> effectDefinitionMap, Map<String, AbstractToolEffect> definedObjects) {
		effectDefinitionMap.forEach((name, attachment) -> {
			try {
				System.out.println("BUFF: " + attachment);
				System.out.println("NEW INSTANCE OF " + attachment.getClazz());
				AbstractToolEffect attachmentInst = attachment.getClazz().newInstance();
				attachmentInst.setData(attachment);
				attachmentInst.setUtils(new AbsPhilItemUtils(cfg.getConfig(), effectDataMap));
				System.out.println("THE INSTANCE: " + attachmentInst);
				System.out.println("NAME: " + attachmentInst.getName());
				effectDataMap.put(name, attachmentInst);
				RedItems.getInstance().getEnchantManager().registerEffect(attachmentInst);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
	}

	public AbsPhilItemUtils getUtils() {
		return utils;
	}

	public boolean hasPumpkinEffects() {
		return effectDataMap.values().size() > 0;
	}

	public Map<String, AbstractToolEffect> getPumpkinEffects() {
		return effectDataMap;
	}

	public ToolEffectData getAttachmentData(String attachmentName) {
		return cfg.getConfig().getEffects().get(attachmentName);
	}

	@Nullable
	public ItemStack getPumpkinAxe(Player player) {
		if(utils.isPumpkinAxe(player.getItemInHand()))
			return player.getItemInHand();
		return null;
	}

	@Nullable
	public ItemStack findPumpkinAxe(Player player) {
		if(player == null) return null;
		for( ItemStack item : player.getInventory().getContents()) {
			if(getUtils().isPumpkinAxe(item)) return item;
		}
		return null;
	}

	@Nullable
	public ItemStack holdingAnyCustomTool(Player player) {
		if(utils.isAbsTool(player.getItemInHand()))
			return player.getItemInHand();
		return null;
	}
	@Nullable
	public ItemStack getBlockPickaxe(Player player) {
		if(utils.isBlockPickaxe(player.getItemInHand()))
		if(utils.isBlockPickaxe(player.getItemInHand()))
			return player.getItemInHand();
		return null;
	}
	@Nullable
	public ItemStack findBlockPickaxe(Player player) {
		if(player == null) return null;
		for( ItemStack item : player.getInventory().getContents()) {
			if(getUtils().isBlockPickaxe(item)) return item;
		}
		return null;
	}

	public AbstractToolEffect getTemplateObject(String attachmentName) {
		return effectDataMap.get(attachmentName);
	}

	public ToolConfig cfg() {
		return cfg.getConfig();
	}

	public void terminate() {
		effectDataMap.values().forEach((effect) ->
				RedItems.getInstance().getEnchantManager().deregisterEffect(effect));
		utils.terminate();
	}
}
