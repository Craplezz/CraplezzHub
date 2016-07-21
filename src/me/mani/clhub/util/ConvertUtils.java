package me.mani.clhub.util;

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class ConvertUtils {

	public static Location toLocation(Document document) {
		return new Location(Bukkit.getWorld(document.getString("world")), document.getDouble("x"), document.getDouble("y"), document.getDouble("z"));
	}
	
	public static Document toDocument(Location location) {
		return new Document("world", location.getWorld().getName()).append("x", Double.valueOf(location.getX())).append("y", Double.valueOf(location.getY())).append("z", Double.valueOf(location.getZ()));
	}
	
}
