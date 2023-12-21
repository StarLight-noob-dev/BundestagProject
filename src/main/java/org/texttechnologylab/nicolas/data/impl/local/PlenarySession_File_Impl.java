package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.helper.Generator;
import org.texttechnologylab.nicolas.data.models.Abgeordneter;
import org.texttechnologylab.nicolas.data.models.AgendaItem;
import org.texttechnologylab.nicolas.data.models.PlenarySession;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Plenary Session Interface
 * @author Nicolas Calderon
 */
public class PlenarySession_File_Impl extends ClassObject_Impl implements PlenarySession {


    private String place;
    private Date date;
    private Time sessionStart;
    private Time sessionEnd;
    private int legislativePeriod;
    private int sessionNumber;
    private String title;
    private List<Abgeordneter> sessionChairs;
    private List<Abgeordneter> excusedDeputies;
    private List<AgendaItem> agendaItems;

    /**
     * Constructor
     */
    public PlenarySession_File_Impl(BundestagFactory pFactory) {
        super(pFactory);
        setID(Generator.generateID());
        this.excusedDeputies = new ArrayList<>(0);
        this.agendaItems = new ArrayList<>(0);
        this.sessionChairs = new ArrayList<>(0);
    }


    @Override
    public String getPlace(){
        return place;
    }

    @Override
    public void setPlace(String place){
        this.place = place;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "PlenarySession_File_Impl{" +
                "date=" + date +
                ", sessionStart=" + sessionStart +
                ", sessionEnd=" + sessionEnd +
                ", legislativePeriod=" + legislativePeriod +
                ", sessionNumber=" + sessionNumber +
                ", title='" + title + '\'' +
                ", sessionChairs=" + sessionChairs +
                ", excusedDeputies=" + excusedDeputies +
                ", agendaItems=" + agendaItems +
                '}';
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Time getSessionStart() {
        return sessionStart;
    }

    @Override
    public void setSessionStart(Time sessionStart) {
        this.sessionStart = sessionStart;
    }

    @Override
    public Time getSessionEnd() {
        return sessionEnd;
    }

    @Override
    public void setSessionEnd(Time sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    @Override
    public int getLegislativePeriod() {
        return legislativePeriod;
    }

    @Override
    public void setLegislativePeriod(int legislativePeriod) {
        this.legislativePeriod = legislativePeriod;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title.replaceAll("[\n\t]+$", "");
    }

    @Override
    public List<Abgeordneter> getSessionChairs() {
        return sessionChairs;
    }

    @Override
    public void setSessionChairs(List<Abgeordneter> sessionChairs) {
        this.sessionChairs = sessionChairs;
    }

    @Override
    public List<AgendaItem> getAgendaItems() {
        return agendaItems;
    }

    @Override
    public void setAgendaItems(List<AgendaItem> agendaItems) {
        this.agendaItems = agendaItems;
    }

    @Override
    public int getSessionNumber() {
        return sessionNumber;
    }

    @Override
    public void setSessionNumber(int sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    @Override
    public List<Abgeordneter> getExcusedDeputies() {
        return excusedDeputies;
    }

    @Override
    public void setExcusedDeputies(List<Abgeordneter> excusedDeputies) {
        this.excusedDeputies = excusedDeputies;
    }
}
