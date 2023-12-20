package org.texttechnologylab.nicolas.data.models;

public interface Comment extends ClassObject{

    /**
     * Get the content of the comment
     * @return
     */
    String getContent();

    /**
     * Set the content of the comment
     * @param content
     */
    void setContent(String content);

    /**
     * Get the comment the speech is from
     * @return
     */
    Speech getSpeech();

    /**
     * Set the comment the speech is from
     * @param speech
     */
    void setSpeech(Speech speech);

    /**
     * Get the person who called the comment
     * @return
     */
    Abgeordneter getCaller();

    /**
     * Set the person who called the comment
     * @param caller
     */
    void setCaller(Abgeordneter caller);

    /**
     * Get the faction who called the comment
     * @return
     */
    Faction getFaction();

    /**
     * Set the faction who called the comment
     * @param faction
     */
    void setFaction(Faction faction);

    /**
     * True if the comment was called by a faction and not a person
     * @return
     */
    boolean isByFaction();

    /**
     * Set whether the comment was called by a faction
     * @param byFaction
     */
    void setByFaction(boolean byFaction);

    /**
     * Get the agenda item the comment was called under
     * @return
     */
    AgendaItem getAgendaItem();

    /**
     * Set the agenda item the comment was called under
     * @param agendaItem
     */
    void setAgendaItem(AgendaItem agendaItem);

    /**
     * True if the comment was in a speech
     * @return
     */
    boolean isInSpeech();

    /**
     * Set whether the comment was in a speech
     * @param inSpeech
     */
    void setInSpeech(boolean inSpeech);
}
