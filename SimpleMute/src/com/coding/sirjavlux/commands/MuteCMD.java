package com.coding.sirjavlux.commands;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.coding.sirjavlux.core.MuteManager;

public class MuteCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length < 1) {
			sender.sendMessage(ChatColor.GRAY + "Invalid args, please use /mute <player> (time)");
			return true;
		} 
		if (Bukkit.getPlayer(args[0]) == null) {
			sender.sendMessage(ChatColor.GRAY + "The specified player " + ChatColor.RED + args[0] + ChatColor.GRAY + " wasn't found!");
			return true;
		} 
		Player target = Bukkit.getPlayer(args[0]);
		if (sender instanceof Player) {
			if (((Player) sender).getUniqueId().equals(target.getUniqueId())) {
				sender.sendMessage(ChatColor.GRAY + "You can't mute yorself!");
				return true;
			}
		}
		if (args.length > 1) {
			String type = "m";
			String timeStr = args[1];
			//get time type
			if (timeStr.contains("h")) type = "h";
			else if (timeStr.contains("d")) type = "d";
			//get amount
			String amountStr = timeStr.contains("m") || timeStr.contains("h") || timeStr.contains("d") ? timeStr.substring(0, timeStr.indexOf(type)) : timeStr;
			if (!isInteger(amountStr)) {
				sender.sendMessage(ChatColor.GRAY + "The specified time " + ChatColor.RED + args[1] + ChatColor.GRAY + " wasn't valid!");
			}
			int amount = Integer.parseInt(amountStr);
			//mute player
			Date muteDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(muteDate);
			switch (type) {
			case "m": 
				calendar.add(Calendar.MINUTE, amount);
				target.sendMessage(ChatColor.GRAY + "You were muted by " + ChatColor.GREEN + sender.getName() + ChatColor.GRAY + " for " + ChatColor.YELLOW + amount + ChatColor.GRAY + " minutes!");
				sender.sendMessage(ChatColor.GRAY + "You muted the player " + ChatColor.GREEN + target.getName() + ChatColor.GRAY + " for " + ChatColor.YELLOW + amount + ChatColor.GRAY + " minutes!");
				break;
			case "h": 
				calendar.add(Calendar.HOUR, amount);
				target.sendMessage(ChatColor.GRAY + "You were muted by " + ChatColor.GREEN + sender.getName() + ChatColor.GRAY + " for " + ChatColor.YELLOW + amount + ChatColor.GRAY + " hours!");
				sender.sendMessage(ChatColor.GRAY + "You muted the player " + ChatColor.GREEN + target.getName() + ChatColor.GRAY + " for " + ChatColor.YELLOW + amount + ChatColor.GRAY + " hours!");
				break;
			case "d": 
				calendar.add(Calendar.DAY_OF_YEAR, amount);
				target.sendMessage(ChatColor.GRAY + "You were muted by " + ChatColor.GREEN + sender.getName() + ChatColor.GRAY + " for " + ChatColor.YELLOW + amount + ChatColor.GRAY + " days!");
				sender.sendMessage(ChatColor.GRAY + "You muted the player " + ChatColor.GREEN + target.getName() + ChatColor.GRAY + " for " + ChatColor.YELLOW + amount + ChatColor.GRAY + " days!");
				break;
			}
			muteDate = calendar.getTime();
			MuteManager.mute(target, muteDate);
		} else {
			MuteManager.unmute(target);
			target.sendMessage(ChatColor.GRAY + "You were successfully unmuted by " + ChatColor.GREEN + sender.getName() + ChatColor.GRAY + "!");
			sender.sendMessage(ChatColor.GRAY + "You successfully unmuted the player " + ChatColor.GREEN + target.getName() + ChatColor.GRAY + "!");
		}
		return true;
	}

	private boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
}
