package org.texttechnologylab.nicolas.data.impl.database;

import org.bson.Document;
import org.texttechnologylab.nicolas.data.models.Abgeordneter;
import org.texttechnologylab.nicolas.data.models.ClassObject;
import org.texttechnologylab.nicolas.data.models.Faction;
import org.texttechnologylab.nicolas.database.MongoDBConnectionHandler;

import java.util.Set;
import java.util.stream.Collectors;

public class Faction_MongoDB_Impl extends MongoDBConnectionHandler implements Faction {

    private static final String COLLECTION_NAME = "Faction";

    // ID
    private String bankID;
    private String id;
    private String label;

    @Override
    public String getID() {
        return null;
    }

    @Override
    public void setID(String id) {

    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public void setLabel(String label) {

    }

    @Override
    public boolean compareObject(ClassObject o) {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void addMember(Abgeordneter abgeordneter) {

    }

    @Override
    public void addMembers(Set<Abgeordneter> abgeordneters) {

    }

    @Override
    public Set<Abgeordneter> getMembers() {
        return null;
    }

    @Override
    public Abgeordneter getMember(String vorname, String nachname) {
        return null;
    }

    @Override
    public Abgeordneter getMember(String id) {
        return null;
    }


    /**
     * Transform a Faction into a Document which can be store in a MongoDB
     * @param faction to be transformed
     * @return Document of the faction
     */
    public static Document toDocument(Faction faction){
        Document doc = new Document();

        doc.put("id", faction.getID());
        doc.put("type", "faction");
        doc.put("name", faction.getName());
        doc.put("members", faction.getMembers().stream()
                .map(Abgeordneter::getID)
                .collect(Collectors.toList()));

        return doc;
    }
}
