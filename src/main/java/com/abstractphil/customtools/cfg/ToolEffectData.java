package com.abstractphil.customtools.cfg;

import java.util.List;
import java.util.Map;

import com.abstractphil.customtools.effects.AbstractToolEffect;
import lombok.Data;
import org.bukkit.Material;

@Data
public class ToolEffectData {
	private Class<? extends AbstractToolEffect> clazz;
	private String effectName, guiName, skullHost, vanillaEnchant;
	private List<String> guiLore, itemLore, commands;
	private Material material;
	private int maxLevel, cooldown, cost;
	private float chancePerLevel, multiplierPerLevel;
	private Map<String, LootCommand> loot;
	private List<String> whitelist;
	private List<String> blacklist;
}
