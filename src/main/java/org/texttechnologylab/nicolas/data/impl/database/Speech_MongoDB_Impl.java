package org.texttechnologylab.nicolas.data.impl.database;

import org.bson.Document;
import org.texttechnologylab.nicolas.data.models.*;
import org.texttechnologylab.nicolas.database.MongoDBConnectionHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Implementation for MongoDB
 * @author Nicolas Calderon
 */
public class Speech_MongoDB_Impl extends MongoDBConnectionHandler implements Speech {

    private static final String COLLECTION_NAME = "Speech";

    private String bankID;
    private String id;
    private String label;
    private Abgeordneter speaker;
    private String speakerID;
    private PlenarySession plenarySession;
    private String plenaryID;
    private List<String> text;
    private AgendaItem agendaItem;
    private String agendaID;
    private List<Comment> comments;
    private List<String> commentsID;

    public Speech_MongoDB_Impl(Document speech){
        this.bankID = speech.get("_id").toString();
        this.id = (String) speech.get("id");
        this.label = (String) speech.get("label");
        this.speakerID = (String) speech.get("speaker");
        this.plenaryID = (String) speech.get("plenarysession");
        this.text = (List<String>) speech.get("content");
        this.agendaID = (String) speech.get("agendaitem");
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
        updateField(COLLECTION_NAME, bankID, "label", id);
        this.label = label;
    }

    @Override
    public boolean compareObject(ClassObject o) {
        return this.id.equals(o.getID());
    }

    @Override
    public List<String> getText() {
        return text;
    }

    @Override
    public int getTextLength() {
        AtomicInteger l = new AtomicInteger();
        text.forEach(t -> l.addAndGet(t.length()));
        return l.get();
    }

    @Override
    public void setText(String text) {
        this.text.add(text);
        updateField(COLLECTION_NAME, bankID, "content", text);
    }

    @Override
    public Abgeordneter getSpeaker() {
        return speaker;
    }

    @Override
    public void setSpeaker(Abgeordneter speaker) {
        updateField(COLLECTION_NAME, bankID, "label", speaker.getID());
        this.speaker = speaker;
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
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public void setComments(List<Comment> comments) {
        this.comments = comments;
        updateField(COLLECTION_NAME, bankID, "comment", comments.stream()
                .map(ClassObject::getID)
                .collect(Collectors.toList()));
    }

    @Override
    public void setAgendaItem(AgendaItem agendaItem) {
        updateField(COLLECTION_NAME, bankID, "angendaitem", agendaItem.getID());
        this.agendaItem = agendaItem;
    }

    @Override
    public AgendaItem getAgendaItem() {
        return agendaItem;
    }

    /**
     * Transform a Speech into a Document which can be store in a MongoDB
     * @param speech to be transformed
     * @return Document of the speech
     */
    public static Document toDocument(Speech speech){
        Document doc = new Document();

        doc.put("id", speech.getID());
        doc.put("label", speech.getLabel());
        doc.put("speaker", speech.getSpeaker().getID());
        doc.put("plenarysession", speech.getPlenarySession().getID());
        doc.put("agendaitem", speech.getAgendaItem().getID());
        doc.put("content", speech.getText());
        doc.put("comments", speech.getComments().stream().map(Comment::getContent).collect(Collectors.toList()));

        return doc;
    }
}
