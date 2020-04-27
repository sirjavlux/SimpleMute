package com.coding.sirjavlux.core;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.coding.sirjavlux.commands.MuteCMD;

public class SimpleMute extends JavaPlugin {

	private String consolePrefix = ChatColor.GRAY + "[SimpleMute]";
	private static FileConfiguration muteFile;
	private static SimpleMute main;
	
	@Override
	public void onEnable() {
		this.getCommand("mute").setExecutor(new MuteCMD());
		this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
		
	    File file = new File(this.getDataFolder() + "/mute.yml");
	    muteFile = YamlConfiguration.loadConfiguration(file);
		MuteManager.loadData();
		
	    main = this;
	    
	    getServer().getConsoleSender().sendMessage(String.valueOf(this.consolePrefix) + ChatColor.GREEN + " Successfully enabled!");
	}

	@Override
	public void onDisable() { 
		MuteManager.saveData();
		getServer().getConsoleSender().sendMessage(String.valueOf(this.consolePrefix) + ChatColor.RED + " Disabled!"); 
	}
	
	public static FileConfiguration getMuteFile() {
		return muteFile;
	}
	
	public static SimpleMute instance() {
		return main;
	}
}
