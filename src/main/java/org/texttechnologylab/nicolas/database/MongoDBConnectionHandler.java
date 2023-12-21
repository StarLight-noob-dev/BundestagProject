package org.texttechnologylab.nicolas.database;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.texttechnologylab.nicolas.data.exceptions.DBException;
import org.texttechnologylab.nicolas.data.impl.database.*;
import org.texttechnologylab.nicolas.data.impl.local.BundestagFactory;
import org.texttechnologylab.nicolas.data.models.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

public class MongoDBConnectionHandler {

    private static final String DB_PROPERTIES_PATH = "db.properties";
    private static final Properties DB_PROPERTIES = new Properties();

    private static MongoClient connection = null;
    private static MongoDatabase db = null;
    private static boolean connected = false;

    /**
     * Connect to the database with properties from the resources
     */
    public static void connect() {
        if (connection != null) {
            try {
                System.out.println("[INFO] Closing existing connection");
                connection.close();
            } catch (Exception ignored) {};
        }
        try {
            DB_PROPERTIES.load(MongoDBConnectionHandler.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_PATH));
        } catch (IOException e) {
            System.out.println("[ERROR] Cannot load the db.properties resource file. Place the file there and restart the program to try again");
        }
        String host = DB_PROPERTIES.getProperty("remote_host");
        String database = DB_PROPERTIES.getProperty("remote_database");
        String user = DB_PROPERTIES.getProperty("remote_user");
        String password = DB_PROPERTIES.getProperty("remote_password");
        String portStr = DB_PROPERTIES.getProperty("remote_port");
        if (host == null || database == null || user == null || password == null || portStr == null) {
            System.out.println("[ERROR] The db.properties file does not contain all the necessary data (remote_host, remote_database, remote_user, remote_password, remote_port)");
            return;
        }
        int port;
        try {
            port = Integer.parseInt(portStr);
            if (port <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] The remote_port value in the db.properties value is not an unsigned integer");
            return;
        }
        MongoCredential credential = MongoCredential.createCredential(user, database, password.toCharArray());
        try {
            connection = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder ->
                                    builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                            .credential(credential)
                            .build()
            );
            db = connection.getDatabase(database);
        } catch (Exception e) {
            System.out.println("[ERROR] Cannot connect to database");
            e.printStackTrace();
            return;
        }
        System.out.println("Successfully connected to database, those collection are available");
        db.listCollectionNames().forEach((Consumer<String>) System.out::println);
        connected = true;
    }

    /**
     * Close the connection to the db
     */
    public static void close() {
        connection.close();
        System.out.println("Connection closed successfully");
        connected = false;
    }

    /**
     * Insert something into the database
     * @param collectionName the name of the collection
     * @param data the data to insert
     */
    public static void insert(String collectionName, Document data) {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        collection.insertOne(data);
    }

    /**
     * Delete something from the database
     * @param collectionName the name of the collection
     * @param id the id to delete
     */
    protected static void delete(String collectionName, String id) {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        collection.deleteOne(Filters.eq("_id", id));
    }

    /**
     * Aggregate over a given collection
     * @param collectionName the name of the collection
     * @param pipeline the pipeline to aggregate with
     * @return the result
     */
    protected static List<Document> aggregate(String collectionName, List<Bson> pipeline) {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        List<Document> result = new ArrayList<>();
        collection.aggregate(pipeline).forEach((Consumer<Document>) result::add);
        return result;
    }

    /**
     * Updates the value stored in a field
     * @param collectionName where the change is going to be made
     * @param bankID Id of the object in the database
     * @param field to be changed
     * @param val new value
     */
    protected static void updateField(String collectionName, String bankID, String field, Object val){
        MongoCollection<Document> collection = db.getCollection(collectionName);
        Bson query = Filters.eq("_id", bankID);
        Bson update = Updates.set(field, val);
        UpdateResult result = collection.updateOne(query, update);
    }

    /**
     * Find a document by its id
     * @param collectionName the name of the collection
     * @param id the id of the document
     * @return the resolved document
     * @throws DBException if the document does not exist
     */
    public static Document findDocumentById(String collectionName, String id) throws DBException {
        ObjectId objectId;
        objectId = new ObjectId(id);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        FindIterable<Document> iter = collection.find(Filters.eq("_id", objectId));
        try (MongoCursor<Document> cursor = iter.cursor()) {
            if (!cursor.hasNext()) throw new DBException(String.format("Collection %s has multiple objects related to %s", collectionName, id));
            return cursor.next();
        }
    }

    /**
     * Find mutliple documents by their id
     * @param collectionName the name of the collection
     * @param ids the ids of the documents
     * @return the documents
     * @throws DBException if one or multiple of the documents do not exist
     */
    public static List<Document> findMultipleDocumentById(String collectionName, List<String> ids) throws DBException {
        MongoCollection<Document> collection = db.getCollection(collectionName);
        FindIterable<Document> iter = collection.find(Filters.in("_id", ids));
        List<Document> result = new ArrayList<>();
        try (MongoCursor<Document> cursor = iter.cursor()) {
            cursor.forEachRemaining(result::add);
        }
        if (result.size() != ids.size()) throw new DBException(String.format("Collection %s some of %s", collectionName, String.join(", ", ids)));
        return result;
    }

    /**
     * Manages the upload of all data
     * @param factory to get data from local
     */
    public static void UploadData(BundestagFactory factory){

        if (!connected){
            System.out.println("Looks like you are not connected to the database, starting connection...");
            connect();
        }

        System.out.println("Starting the upload of Abgeordneter");
        for (Abgeordneter a : factory.listAbgeordneter()) {
            Document doc = Abgeordneter_MongoDB_Impl.toDocument(a);
            insert("Abgeordneter", doc);
        }
        System.out.println("Upload of Abgeordneter has finish");
        System.out.println("====================================\n");

        System.out.println("Starting the upload of Parties");
        for (Party p : factory.listParties()) {
            Document doc = Party_MongoDB_Impl.toDocument(p);
            insert("Party", doc);
        }
        System.out.println("Upload of Parties has finish");
        System.out.println("====================================\n");

        System.out.println("Starting the upload of Factions");
        for (Faction f : factory.listFactions()) {
            Document doc = Faction_MongoDB_Impl.toDocument(f);
            insert("Faction", doc);
        }
        System.out.println("Upload of Factions has finish");
        System.out.println("====================================\n");

        System.out.println("Starting the upload of Plenary Sessions");
        for (PlenarySession ps : factory.listSessions()) {
            Document doc = PlenarySession_MongoDB_Impl.toDocument(ps);
            insert("PlenarySession", doc);
        }
        System.out.println("Upload of Plenary Sessions has finish");
        System.out.println("====================================\n");

        System.out.println("Starting the upload of Agenda Items");
        for (AgendaItem ag : factory.listItems()) {
            Document doc = AgendaItem_MongoDB_Impl.toDocument(ag);
            insert("AgendaItems", doc);
        }
        System.out.println("Upload of Agenda Items has finish");
        System.out.println("====================================\n");

        System.out.println("Starting the upload of Speeches");
        for (Speech s : factory.listSpeeches()) {
            Document doc = Speech_MongoDB_Impl.toDocument(s);
            insert("Speech", doc);
        }
        System.out.println("Upload of Speeches has finish");
        System.out.println("====================================\n");
        System.out.println("[INFO] All data was uploaded correctly");
    }
}
