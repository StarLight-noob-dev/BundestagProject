package org.texttechnologylab.nicolas.data.impl.database;

import org.bson.Document;
import org.texttechnologylab.nicolas.data.models.Abgeordneter;
import org.texttechnologylab.nicolas.data.models.AgendaItem;
import org.texttechnologylab.nicolas.data.models.ClassObject;
import org.texttechnologylab.nicolas.data.models.PlenarySession;
import org.texttechnologylab.nicolas.database.MongoDBConnectionHandler;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation for MongoDB
 * @author Nicolas Calderon
 */
public class PlenarySession_MongoDB_Impl extends MongoDBConnectionHandler implements PlenarySession {

    private static final String COLLECTION_NAME = "PlenarySession";

    private String bankID;
    private String id;
    private String label;
    private String place;
    private Date date;
    private Time sessionStart;
    private Time sessionEnd;
    private int legislativePeriod;
    private int sessionNumber;
    private String title;
    private List<Abgeordneter> sessionChairs = new ArrayList<>(0);
    private List<Abgeordneter> excusedDeputies = new ArrayList<>(0);
    private List<AgendaItem> agendaItems = new ArrayList<>(0);
    private List<String> agendaitemsID;

    public PlenarySession_MongoDB_Impl(Document ps){

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

        this.bankID = ps.get("_id").toString();
        this.id = (String) ps.get("id");
        this.label = (String) ps.get("label");
        this.place = (String) ps.get("place");

        try {
            this.date = new Date(sdfDate.parse((String) ps.get("date")).getTime());
        } catch (ParseException e) {
            this.date = null;
        }
        try {
            this.sessionStart = new Time(sdfTime.parse((String) ps.get("start")).getTime());
        } catch (ParseException e) {
            this.sessionStart = null;
        }
        try {
            this.sessionEnd = new Time(sdfTime.parse((String) ps.get("end")).getTime());
        } catch (ParseException e) {
            this.sessionEnd = null;
        }

        this.legislativePeriod = (int) ps.get("period");
        this.sessionNumber = (int) ps.get("number");
        this.title = (String) ps.get("title");
        this.agendaitemsID = (List<String>) ps.get("agendaitems");
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
        return this.id.equals(o.getID());
    }

    @Override
    public String getPlace() {
        return place;
    }

    @Override
    public void setPlace(String place) {
        updateField(COLLECTION_NAME, bankID, "place", place);
        this.place = place;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        updateField(COLLECTION_NAME, bankID, "date", date);
        this.date = date;
    }

    @Override
    public Time getSessionStart() {
        return sessionStart;
    }

    @Override
    public void setSessionStart(Time sessionStart) {
        updateField(COLLECTION_NAME, bankID, "start", sessionStart);
        this.sessionStart = sessionStart;
    }

    @Override
    public Time getSessionEnd() {
        return sessionEnd;
    }

    @Override
    public void setSessionEnd(Time sessionEnd) {
        updateField(COLLECTION_NAME, bankID, "end", sessionEnd);
        this.sessionEnd = sessionEnd;
    }

    @Override
    public int getLegislativePeriod() {
        return legislativePeriod;
    }

    @Override
    public void setLegislativePeriod(int legislativePeriod) {
        updateField(COLLECTION_NAME, bankID, "legislativperiod", legislativePeriod);
        this.legislativePeriod = legislativePeriod;
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
    public List<Abgeordneter> getSessionChairs() {
        return sessionChairs;
    }

    @Override
    public void setSessionChairs(List<Abgeordneter> sessionChairs) {
        updateField(COLLECTION_NAME, bankID, "sesionchairs", sessionChairs.stream()
                .map(ClassObject::getID).collect(Collectors.toList()));
        this.sessionChairs = sessionChairs;
    }

    @Override
    public List<AgendaItem> getAgendaItems() {
        return agendaItems;
    }

    @Override
    public void setAgendaItems(List<AgendaItem> agendaItems) {
        this.agendaItems.addAll(agendaItems);
        this.agendaitemsID.addAll(agendaItems.stream()
                .map(ClassObject::getID)
                .collect(Collectors.toList()));
        updateField(COLLECTION_NAME, bankID, "agendaitem", agendaitemsID);
    }

    @Override
    public int getSessionNumber() {
        return sessionNumber;
    }

    @Override
    public void setSessionNumber(int sessionNumber) {
        updateField(COLLECTION_NAME, bankID, "sessionnumber", sessionNumber);
        this.sessionNumber =  sessionNumber;
    }

    @Override
    public List<Abgeordneter> getExcusedDeputies() {
        return excusedDeputies;
    }

    @Override
    public void setExcusedDeputies(List<Abgeordneter> excusedDeputies) {
        updateField(COLLECTION_NAME, bankID, "excuse", excusedDeputies.stream()
                .map(ClassObject::getID).collect(Collectors.toList()));
        this.excusedDeputies.addAll(excusedDeputies);
    }

    /**
     * Transform a Plenary Session into a Document which can be store in a MongoDB
     * @param ps to be transformed
     * @return Document of the plenary session
     */
    public static Document toDocument(PlenarySession ps){
        Document doc = new Document();

        doc.put("id", ps.getID());
        doc.put("label", ps.getLabel());
        doc.put("place", ps.getPlace());
        doc.put("date", String.valueOf(ps.getDate()));
        doc.put("start", String.valueOf(ps.getSessionStart()));
        doc.put("end", String.valueOf(ps.getSessionEnd()));
        doc.put("period", ps.getLegislativePeriod());
        doc.put("number", ps.getSessionNumber());
        doc.put("title", ps.getTitle());
        doc.put("sessionchairs", ps.getSessionChairs().stream()
                .map(ClassObject::getID)
                .collect(Collectors.toList()));
        doc.put("agendaitems", ps.getAgendaItems().stream()
                .map(ClassObject::getID)
                .collect(Collectors.toList()));

        return doc;
    }
}
