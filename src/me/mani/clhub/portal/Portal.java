package me.mani.clhub.portal;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public class Portal {

	public static final Material PORTAL_MATERIAL = Material.PORTAL;
	
	private Location baseLocation;
	private List<Location> locations = new ArrayList<>();
	private String targetServerName;
	
	public Portal(Location baseLocation, List<Location> locations, String targetServerName) {
		this.baseLocation = baseLocation;
		this.locations = locations;
		this.targetServerName = targetServerName;
	}
	
	public Location getBaseLocation() {
		return baseLocation;
	}
	
	public String getTargetServerName() {
		return targetServerName;
	}
	
	public boolean contains(Location location) {
		return locations.contains(location);
	}
	
	public static Portal createPortal(Location location, String targetServerName) {
		List<Location> locations = new ArrayList<>();
		locations.add(location);
		List<Location> cachedLocations = new ArrayList<>(locations);
		for (Location location2 : locations)
			recursiveAddingX(location2, cachedLocations);
		locations.addAll(cachedLocations);
		for (Location location2 : locations)
			recursiveAddingY(location2, cachedLocations);
		locations.addAll(cachedLocations);
		for (Location location2 : locations)
			recursiveAddingZ(location2, cachedLocations);
		locations.addAll(cachedLocations);
		return new Portal(location, locations, targetServerName);
	}
	
	private static void recursiveAddingX(Location baseLocation, List<Location> foundLocations) {
		for (int x = 1; ; x++) {
			Location location = baseLocation.clone().add(x, 0, 0);
			if (location.getBlock().getType() == PORTAL_MATERIAL && !foundLocations.contains(location)) {
				foundLocations.add(location);
				recursiveAddingX(location, foundLocations);
			}
			else
				break;
		}
		for (int x = -1; ; x--) {
			Location location = baseLocation.clone().add(x, 0, 0);
			if (location.getBlock().getType() == PORTAL_MATERIAL && !foundLocations.contains(location)) {
				foundLocations.add(location);
				recursiveAddingX(location, foundLocations);
			}
			else
				break;
		}
	}
	
	private static void recursiveAddingY(Location baseLocation, List<Location> foundLocations) {
		for (int y = 1; ; y++) {
			Location location = baseLocation.clone().add(0, y, 0);
			if (location.getBlock().getType() == PORTAL_MATERIAL && !foundLocations.contains(location)) {
				foundLocations.add(location);
				recursiveAddingY(location, foundLocations);
			}
			else
				break;
		}
		for (int y = -1; ; y--) {
			Location location = baseLocation.clone().add(0, y, 0);
			if (location.getBlock().getType() == PORTAL_MATERIAL && !foundLocations.contains(location)) {
				foundLocations.add(location);
				recursiveAddingY(location, foundLocations);
			}
			else
				break;
		}
	}
	
	private static void recursiveAddingZ(Location baseLocation, List<Location> foundLocations) {
		for (int z = 1; ; z++) {
			Location location = baseLocation.clone().add(0, 0, z);
			if (location.getBlock().getType() == PORTAL_MATERIAL && !foundLocations.contains(location)) {
				foundLocations.add(location);
				recursiveAddingZ(location, foundLocations);
			}
			else
				break;
		}
		for (int z = -1; ; z--) {
			Location location = baseLocation.clone().add(0, 0, z);
			if (location.getBlock().getType() == PORTAL_MATERIAL && !foundLocations.contains(location)) {
				foundLocations.add(location);
				recursiveAddingZ(location, foundLocations);
			}
			else
				break;
		}
	}
	
}
