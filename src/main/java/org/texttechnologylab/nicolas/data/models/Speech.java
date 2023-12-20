package org.texttechnologylab.nicolas.data.models;

import java.util.List;

public interface Speech extends ClassObject {

    /**
     * Get the text of the speech
     *
     * @return
     */
    List<String> getText();

    /**
     * Get the length of the text of the speech
     * @return
     */
    int getTextLength();

    /**
     * Set the text of the speech
     * @param text
     */
    void setText(String text);

    /**
     * Get the speaker
     * @return
     */
    Abgeordneter getSpeaker();

    /**
     * Set the speaker
     * @param speaker
     */
    void setSpeaker(Abgeordneter speaker);

    /**
     * Get the plenary session
     * @return
     */
    PlenarySession getPlenarySession();

    /**
     * Set the plenary session
     * @param plenarySession
     */
    void setPlenarySession(PlenarySession plenarySession);

    /**
     * Get all comments
     * @return
     */
    List<Comment> getComments();

    /**
     * Set all comments
     * @param comments
     */
    void setComments(List<Comment> comments);

    /**
     * Get the agenda item
     * @param agendaItem
     */
    void setAgendaItem(AgendaItem agendaItem);

    /**
     * Set the agenda item
     * @return
     */
    AgendaItem getAgendaItem();
}
