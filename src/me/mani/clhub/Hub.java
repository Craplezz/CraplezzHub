package me.mani.clhub;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import me.mani.clhub.DataManager.DataLoadException;
import me.mani.clhub.listener.*;
import me.mani.clhub.portal.Portal;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hub extends JavaPlugin {

	private static Hub instance;
	
	private MongoClient mongoClient;
	private DataManager dataManager;

	private List<Portal> portals = new ArrayList<>();
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		mongoClient = new MongoClient(new ServerAddress("craplezz.de", 27017), Arrays.asList(MongoCredential.createCredential("Overload", "admin", "1999mani123".toCharArray())));
		dataManager = new DataManager(mongoClient, "todo", "general");

		try {
			portals = dataManager.loadPortals();
		} catch (DataLoadException e) {
			e.printStackTrace();
		}
		
		Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
		
	}
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}
	
	public DataManager getDataManager() {
		return dataManager;
	}

	public List<Portal> getPortals() {
		return portals;
	}
	
	public static Hub getInstance() {
		return instance;
	}
	
}
