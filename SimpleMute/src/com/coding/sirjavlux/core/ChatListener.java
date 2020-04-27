package com.coding.sirjavlux.core;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	@EventHandler
	public void onMessage(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (MuteManager.isMuted(p)) e.setCancelled(true);
	}
}
