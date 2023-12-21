package org.texttechnologylab.nicolas.data.impl.database;

import org.bson.Document;
import org.texttechnologylab.nicolas.data.models.*;
import org.texttechnologylab.nicolas.database.MongoDBConnectionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation for MongoDB
 * @author Nicolas Calderon
 */
public class AgendaItem_MongoDB_Impl extends MongoDBConnectionHandler implements AgendaItem {

    private static final String COLLECTION_NAME = "AgendaItems";

    private String bankID;
    private String id;
    private String title;
    private String label;
    private List<Speech> speeches;
    private List<String> speechesID;
    private List<Comment> comments;
    private List<String> commentsID;
    private PlenarySession plenarySession;
    private String plenaryID;

    public AgendaItem_MongoDB_Impl(Document ag){
        this.bankID = ag.get("_id").toString();
        this.id = (String) ag.get("id");
        this.title = (String) ag.get("title");
        this.label = (String) ag.get("label");
        this.speechesID = (List<String>) ag.get("speeches");
        this.commentsID = (List<String>) ag.get("comments");
        this.plenaryID = (String) ag.get("plenarysession");
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        updateField(COLLECTION_NAME, bankID, "title", title);
        this.title = title;
    }

    @Override
    public List<Speech> getSpeeches() {
        return speeches;
    }

    @Override
    public void setSpeeches(List<Speech> speeches) {
        if (this.speeches.isEmpty()){
            this.speeches = speeches;
        } else {
            this.speeches.addAll(speeches);
        }

        updateField(COLLECTION_NAME, bankID, "speeches", this.speeches);
    }

    @Override
    public List<Comment> getComments() {
        return null;
    }

    @Override
    public void setComments(List<Comment> comments) {
        if (this.comments.isEmpty()){
            this.comments = comments;
        } else {
            this.comments.addAll(comments);
        }
        updateField(COLLECTION_NAME, bankID, "comments", this.comments);
    }

    @Override
    public PlenarySession getPlenarySession() {
        return plenarySession;
    }

    @Override
    public void setPlenarySession(PlenarySession plenarySession) {
        updateField(COLLECTION_NAME, bankID, "plenarysession", plenarySession.getID());
        this.plenarySession = plenarySession;
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
        updateField(COLLECTION_NAME, bankID, "label", label);
        this.label = label;
    }

    @Override
    public boolean compareObject(ClassObject o) {
        return this.getID().equals(o.getID());
    }

    /**
     * Transform a Agenda Item into a Document which can be store in a MongoDB
     * @param ag to be transformed
     * @return Document of the agenda item
     */
    public static Document toDocument(AgendaItem ag){
        Document doc = new Document();

        doc.put("id", ag.getID());
        doc.put("title", ag.getTitle());
        doc.put("label", ag.getLabel());
        doc.put("speeches", ag.getSpeeches().stream()
                .map(ClassObject::getID)
                .collect(Collectors.toList()));
        doc.put("comments", ag.getComments().stream()
                .map(ClassObject::getID)
                .collect(Collectors.toList()));
        doc.put("plenarysession", ag.getPlenarySession().getID());

        return doc;
    }
}
