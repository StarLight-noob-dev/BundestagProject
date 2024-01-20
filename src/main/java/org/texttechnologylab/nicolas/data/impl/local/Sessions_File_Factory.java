package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.models.AgendaItem;
import org.texttechnologylab.nicolas.data.models.PlenarySession;
import org.texttechnologylab.nicolas.data.models.Speech;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Sessions_File_Factory {

    protected BundestagFactory upper_factory;
    private Set<PlenarySession> Sessions;
    private Set<AgendaItem> Items;
    private List<Speech> Speeches;

    public Sessions_File_Factory(BundestagFactory f){
        this.upper_factory = f;
        this.Sessions = new HashSet<>(0);
        this.Items = new HashSet<>(0);
        this.Speeches = new ArrayList<>(0);
    }

    /**
     * Get all Plenary Sessions
     * @return plenary sessions
     */
    public Set<PlenarySession> getSessions(){
        return Sessions;
    }

    /**
     * Add one Session to the set and also its information to the list.
     * @param ps to be added
     */
    public void addSession(PlenarySession ps){
        this.Sessions.add(ps);
        this.Items.addAll(ps.getAgendaItems());
        ps.getAgendaItems().stream()
                .map(AgendaItem::getSpeeches)
                .flatMap(List::stream)
                .forEach(this::addSpeech);
    }

    /**
     * Add multiple sessions to the set
     * @param pss sessions to be added
     */
    public void addManySessions(Set<PlenarySession> pss){
        pss.forEach(this::addSession);
    }

    /**
     * Get all agenda Items
     * @return agenda items
     */
    public Set<AgendaItem> getItems(){
        return Items;
    }

    /**
     * Add a single item
     * @param a item to be added
     */
    public void addItem(AgendaItem a){
        this.Items.add(a);
    }

    /**
     * Add multiple items
     * @param as
     */
    public void addManyItem(Set<AgendaItem> as){
        as.forEach(this::addItem);
    }

    /**
     * Get all speeches
     * @return
     */
    public List<Speech> getSpeeches(){
        return Speeches;
    }

    /**
     * Add a single speech to the list
     * @param s to be added
     */
    public void addSpeech(Speech s){
        this.Speeches.add(s);
    }

    /**
     * Add multiple Speeches to the list
     * @param ss
     */
    public void addManySpeech(List<Speech> ss){
        ss.forEach(this::addSpeech);
    }
}
