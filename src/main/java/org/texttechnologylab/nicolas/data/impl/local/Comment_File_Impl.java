package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.models.*;

/**
 * Implements the Comment Interface
 * @author Nicolas Calderon
 */
public class Comment_File_Impl extends ClassObject_Impl implements Comment {

    private String sContent;
    private Speech pSpeech;
    private Abgeordneter pCaller;
    private Faction pFaction;
    private AgendaItem pAgendaItem;

    private boolean byFaction;
    private boolean inSpeech;

    /**
     * Contructor for Comments
     * @param pFactory
     */
    public Comment_File_Impl(BundestagFactory pFactory) {
        super(pFactory);
    }

    @Override
    public String getContent() {
        return sContent;
    }

    @Override
    public void setContent(String content) {
        this.sContent = content;
    }

    @Override
    public Speech getSpeech() {
        return pSpeech;
    }

    @Override
    public void setSpeech(Speech speech) {
        this.pSpeech = speech;
    }

    @Override
    public Abgeordneter getCaller() {
        return pCaller;
    }

    @Override
    public void setCaller(Abgeordneter caller) {
        this.pCaller = caller;
    }

    @Override
    public Faction getFaction() {
        return pFaction;
    }

    @Override
    public void setFaction(Faction faction) {
        this.pFaction = faction;
    }

    @Override
    public boolean isByFaction() {
        return byFaction;
    }

    @Override
    public void setByFaction(boolean byFaction) {
        this.byFaction = byFaction;
    }

    @Override
    public AgendaItem getAgendaItem() {
        return pAgendaItem;
    }

    @Override
    public void setAgendaItem(AgendaItem agendaItem) {
        this.pAgendaItem = agendaItem;
    }

    @Override
    public boolean isInSpeech() {
        return inSpeech;
    }

    @Override
    public void setInSpeech(boolean inSpeech) {
        this.inSpeech = inSpeech;
    }
}
