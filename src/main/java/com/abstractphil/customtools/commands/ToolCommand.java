package com.abstractphil.customtools.commands;

import com.abstractphil.customtools.ToolMain;
import com.abstractphil.customtools.effects.AbstractToolEffect;
import com.abstractphil.customtools.util.AbsPhilItemUtils;
import com.redmancometh.reditems.RedItems;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToolCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isOp())
			return false;
		switch(args[0]) {
			case "create":
				return createCustomTool(sender, args);
			case "levelup":
				return levelAttachEffect(sender, args);
			case "debug":
				return debugTool(sender, args);
		}
		if(sender instanceof Player && sender.isOp()) {
			sender.sendMessage("Your command was invalid.");
			sender.sendMessage("customtool <create/levelup/debug> <player> <args>");
		}
		return false;
	}

	private boolean createCustomTool(CommandSender sender, String[] args){
		Player player = Bukkit.getPlayer(args[1]);
		if(player != null)  {
			AbsPhilItemUtils utils =  ToolMain.getInstance().getMainController().getUtils();
			ItemStack stack = utils.createBlockPickaxe(player);
			player.getInventory().addItem(stack);
			if(sender instanceof Player && sender.isOp()){
				sender.sendMessage("OP rewarded Custom Tool; Block Pickaxe successfully added to " + player.getName() + "'s inventory!");
			}
			return true;
		} else {
			sender.sendMessage("Player " + args[1] + " does not exist.");
			return false;
		}
	}

	private boolean levelAttachEffect(CommandSender sender, String[] args) {
		Player player = Bukkit.getPlayer(args[1]);
		System.out.println(args[1] + " " + player);
		AbsPhilItemUtils utils = ToolMain.getInstance().getMainController().getUtils();
		if(player != null && utils.getAbsTool(player, "blockpickaxe") != null) {
			try {
				//player.setItemInHand(RedItems.getInstance().getEnchantManager().attachEffect(getPumpkinAxe(player), "psell", 1));
				ItemStack item =  utils.getAbsTool(player, "blockpickaxe");
				item = utils.levelCustomToolAttachment(player, item, args[2], Integer.parseInt(args[3]));
				utils.commitReplaceHeldItem(player, item);
			} catch (Exception ex) {
				ex.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	private int getPumpkinAxeIndexPosition(Player player) {
		if(player != null) {
			AbsPhilItemUtils utils =  ToolMain.getInstance().getMainController().getUtils();
			ItemStack[] list = player.getInventory().getContents();
			for (int i = 0; i < list.length; i++) {
				ItemStack item = list[i];
				if (utils.isPumpkinAxe(item)) return i;
			}
		}
		return -1;
	}

	private boolean debugTool(CommandSender sender, String[] args) {
		Player player = Bukkit.getPlayer(args[1]);
		if (player != null) {
			try {
				AbsPhilItemUtils utils = ToolMain.getInstance().getMainController().getUtils();
				System.out.println(args[1] + " " + player.getName());
				//player.sendMessage("Custom Tool Attachments: ");
				if(utils.getAbsTool(player, "blockpickaxe") != null) {
					System.out.println(RedItems.getInstance().getEnchantManager().getData( utils.getAbsTool(player, "blockpickaxe")));
					RedItems.getInstance().getEnchantManager().getEffects(
							utils.getAbsTool(player, "blockpickaxe")).forEach( effect -> {
						System.out.println((AbstractToolEffect)effect.getEffect());
					});
					System.out.println("Dumping internal statistics: " );
					System.out.println(utils.getJsonStatistics(utils.getAbsTool(player, "blockpickaxe")));
				}
				return true;

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}

}
