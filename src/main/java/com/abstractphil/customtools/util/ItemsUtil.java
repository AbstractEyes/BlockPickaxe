package com.abstractphil.customtools.util;

import org.bukkit.inventory.ItemStack;

/**
 * TODO: Port this to warcore or something
 *
 * @author Redmancometh
 *
 */
public class ItemsUtil {
	public static boolean isChestplate(ItemStack item) {
		switch (item.getType()) {
		case IRON_CHESTPLATE:
			return true;
		case GOLD_CHESTPLATE:
			return true;
		case DIAMOND_CHESTPLATE:
			return true;
		default:
			return false;
		}
	}

	public static boolean isLegs(ItemStack item) {
		switch (item.getType()) {
		case IRON_LEGGINGS:
			return true;
		case GOLD_LEGGINGS:
			return true;
		case DIAMOND_LEGGINGS:
			return true;
		default:
			return false;
		}
	}

	public static boolean isBoots(ItemStack item) {
		switch (item.getType()) {
		case IRON_BOOTS:
			return true;
		case GOLD_BOOTS:
			return true;
		case DIAMOND_BOOTS:
			return true;
		default:
			return false;
		}
	}

	public static boolean isHelmet(ItemStack item) {
		switch (item.getType()) {
		case IRON_HELMET:
			return true;
		case GOLD_HELMET:
			return true;
		case DIAMOND_HELMET:
			return true;
		default:
			return false;
		}
	}
}
