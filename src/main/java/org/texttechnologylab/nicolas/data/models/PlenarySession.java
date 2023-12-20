package org.texttechnologylab.nicolas.data.models;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface PlenarySession extends ClassObject{

    String getPlace();

    void setPlace(String place);

    /**
     * Get the date of the session
     * @return
     */
    Date getDate();

    /**
     * Set the date of the session
     * @param date
     */
    void setDate(Date date);

    /**
     * Get the session start time
     *
     * @return
     */
    Time getSessionStart();

    /**
     * Set the session start time
     * @param sessionStart
     */
    void setSessionStart(Time sessionStart);

    /**
     * Get the session end time
     *
     * @return
     */
    Time getSessionEnd();

    /**
     * Set the session end time
     * @param sessionEnd
     */
    void setSessionEnd(Time sessionEnd);

    /**
     * Set the legislative period
     * @return
     */
    int getLegislativePeriod();

    /**
     * Set the legislative period
     * @param legislativePeriod
     */
    void setLegislativePeriod(int legislativePeriod);

    /**
     * Get the title
     * @return
     */
    String getTitle();

    /**
     * Set the title
     * @param title
     */
    void setTitle(String title);

    /**
     * Get the session chairs
     * @return
     */
    List<Abgeordneter> getSessionChairs();

    /**
     * Set the session chairs
     * @param sessionChairs
     */
    void setSessionChairs(List<Abgeordneter> sessionChairs);

    /**
     * Get the agenda items
     * @return
     */
    List<AgendaItem> getAgendaItems();

    /**
     * Set the agenda items
     * @param agendaItems
     */
    void setAgendaItems(List<AgendaItem> agendaItems);

    /**
     * Get the session number
     * @return
     */
    public int getSessionNumber();

    /**
     * Set the session number
     * @param sessionNumber
     */
    public void setSessionNumber(int sessionNumber);

    /**
     * Get the excused deputies
     * @return
     */
    public List<Abgeordneter> getExcusedDeputies();

    /**
     * Set the excused deputies
     * @param excusedDeputies
     */
    public void setExcusedDeputies(List<Abgeordneter> excusedDeputies);
}
