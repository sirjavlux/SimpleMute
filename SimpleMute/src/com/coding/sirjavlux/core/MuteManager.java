package com.coding.sirjavlux.core;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class MuteManager extends SimpleMute {
	
	private static HashMap<UUID, Date> mutedPlayers = new HashMap<>();
	
	protected static void loadData() {
	    FileConfiguration muteFile = SimpleMute.getMuteFile();
	    if (muteFile == null) return;
		for (String key : muteFile.getKeys(true)) {
			UUID uuid = UUID.fromString(key);
			try {
				Date date = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").parse(muteFile.getString(key));
				mutedPlayers.put(uuid, date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveData() {
	    File file = new File(SimpleMute.instance().getDataFolder() + "/mute.yml");
	    FileConfiguration muteFile = SimpleMute.getMuteFile();
	    SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
	    //set data
		for (Entry<UUID, Date> entry : mutedPlayers.entrySet()) {
			Date date = entry.getValue();
			if (date.after(new Date())) {
				UUID uuid = entry.getKey();
				muteFile.set(uuid.toString(), format.format(date));
			}
		}
		//save data
		try {
			muteFile.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isMuted(Player p) {
		UUID uuid = p.getUniqueId();
		if (mutedPlayers.containsKey(uuid)) {
			Date currentDate = new Date();
			if (currentDate.after(mutedPlayers.get(uuid))) {
				mutedPlayers.remove(uuid);
			} else {
				return true;
			}
		}
		return false;
	}
	
	public static void mute(Player p, Date date) {
		UUID uuid = p.getUniqueId();
		mutedPlayers.put(uuid, date);
	}
	
	public static void unmute(Player p) {
		UUID uuid = p.getUniqueId();
		if (mutedPlayers.containsKey(uuid)) {
			mutedPlayers.remove(uuid);
			SimpleMute.getMuteFile().set(uuid.toString(), null);
		}
	}
}
