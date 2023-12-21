package org.texttechnologylab.nicolas.data.impl.database;

import org.bson.Document;
import org.texttechnologylab.nicolas.data.models.Abgeordneter;
import org.texttechnologylab.nicolas.data.models.ClassObject;
import org.texttechnologylab.nicolas.data.models.Faction;
import org.texttechnologylab.nicolas.database.MongoDBConnectionHandler;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation for MongoDB
 * @author Nicolas Calderon
 */
public class Faction_MongoDB_Impl extends MongoDBConnectionHandler implements Faction {

    private static final String COLLECTION_NAME = "Faction";

    // ID
    private String bankID;
    private String id;
    private String name;
    private String label;
    private Set<Abgeordneter> Members = new HashSet<>(0);
    private List<String> membersID;

    public Faction_MongoDB_Impl(Document f){
        this.bankID = f.get("_id").toString();
        this.id = (String) f.get("id");
        this.name = (String) f.get(name);
        this.membersID = (List<String>) f.get("members");
    }
    @Override
    public String getID() {
        return id;
    }

    @Override
    public void setID(String id) {
        updateField(COLLECTION_NAME, bankID, "id", id);
        this.id = id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean compareObject(ClassObject o) {
        return this.id.equals(o.getID());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        updateField(COLLECTION_NAME, bankID, "name", name);
        this.name = name;
    }

    @Override
    public void addMember(Abgeordneter abgeordneter) {
        this.Members.add(abgeordneter);
        this.membersID.add(abgeordneter.getID());
        updateField(COLLECTION_NAME, bankID, "members", membersID);
    }

    @Override
    public void addMembers(Set<Abgeordneter> abgeordneters) {
        for (Abgeordneter a:abgeordneters) {
            this.Members.add(a);
            this.membersID.add(a.getID());
        }
        updateField(COLLECTION_NAME, bankID, "members", membersID);
    }

    @Override
    public Set<Abgeordneter> getMembers() {
        //TODO maybe have to exhange for something done by the DB.
        return Members;
    }

    @Override
    public Abgeordneter getMember(String vorname, String nachname) {
        String n = vorname + " " + nachname;
        return Members.stream()
                .filter(m -> m.fullNameWithoutTitle().equals(n))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Abgeordneter getMember(String id) {
        return Members.stream()
                .filter(m -> m.getID().equals(id))
                .findFirst()
                .orElse(null);
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
