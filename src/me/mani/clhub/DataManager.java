package me.mani.clhub;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import me.mani.clhub.portal.Portal;
import me.mani.clhub.util.ConvertUtils;

public class DataManager {

	private MongoDatabase mongoDatabase;
	private MongoCollection<Document> dataCollection;
	
	public DataManager(MongoClient mongoClient, String database, String collection) {
		mongoDatabase = mongoClient.getDatabase(database);
		dataCollection = mongoDatabase.getCollection(collection);
	}
	
	@SuppressWarnings("unchecked")
	public List<Portal> loadPortals() throws DataLoadException {
		List<Portal> portals = new ArrayList<>();
		FindIterable<Document> it = dataCollection.find(new Document("type", "portals"));
		if (it.first() == null)
			throw new DataLoadException("Failed to load portals. Check your Mongo.");
		Document document = it.first();
		for (Document value : document.get("data", (Class<ArrayList<Document>>) new ArrayList<Document>().getClass()))
			portals.add(Portal.createPortal(ConvertUtils.toLocation(value.get("location", Document.class)), value.getString("targetServerName")));
		return portals;	
	}
	
	public long savePortals(List<Portal> portals) {
		List<Document> documents = new ArrayList<>();
		for (Portal portal : portals)
			documents.add(new Document("location", ConvertUtils.toDocument(portal.getBaseLocation())).append("targetServerName", portal.getTargetServerName()));
		return dataCollection.updateOne(new Document("type", "portals"), new Document("$set", new Document("data", documents))).getModifiedCount();
	}
	
	public class DataLoadException extends Exception {

		private static final long serialVersionUID = 1L;
		
		public DataLoadException(String message) {
			super(message);
		}
		
	}
	
}
