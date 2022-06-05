package com.abstractphil.customtools;

import com.abstractphil.customtools.controller.ToolDataController;
import org.bukkit.plugin.java.JavaPlugin;

import com.abstractphil.customtools.commands.ToolCommand;

import lombok.Getter;

@Getter
public class ToolMain extends JavaPlugin {
	private ToolDataController mainController;

	@Override
	public void onEnable() {
		super.onEnable();
		this.mainController = new ToolDataController();
		this.mainController.init();
		System.out.println("Initialized pumpkin axe plugin.");
		getCommand("customtool").setExecutor(new ToolCommand());
	}

	@Override
	public void onDisable() {
		mainController.terminate();
		super.onDisable();
	}

	public static ToolMain getInstance() {
		return JavaPlugin.getPlugin(ToolMain.class);
	}
}
