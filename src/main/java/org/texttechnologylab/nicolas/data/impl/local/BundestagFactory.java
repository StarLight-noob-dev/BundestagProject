package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.models.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BundestagFactory {

    protected Abgeordneter_File_Factory pAbgeordneters = new Abgeordneter_File_Factory(this);
    protected Group_File_Factory pGroups = new Group_File_Factory(this);
    protected Sessions_File_Factory pSessions = new Sessions_File_Factory(this);

    /**
     * Constructor
     */
    public BundestagFactory(){}


    /**
     * Add asession
     * @param s
     */
    public void addSession(PlenarySession s){
        pSessions.addSession(s);
    }

    /**
     * List all abgeordneters
     * @return
     */
    public Set<Abgeordneter> listAbgeordneter(){return pAbgeordneters.getAbgeordneters();}

    /**
     * List all Abgeordneter by id
     * Was thought more for testing than anything else
     * @param id
     * @return
     */
    public Abgeordneter listAbgeordneter(String id){
        return pAbgeordneters.getAbgeordneterByID(id);
    }

    /**
     * List all Parties
     * @return
     */
    public Set<Party> listParties(){
        return pGroups.getParties();
    }

    /**
     * List all Factions
     * @return
     */
    public Set<Faction> listFactions(){
        return pGroups.getFactions();
    }

    /**
     * List all Plenary Sessions
     * @return
     */
    public Set<PlenarySession> listSessions(){return pSessions.getSessions();}

    /**
     * List all Speeches
     * @return
     */
    public List<Speech> listSpeeches(){
        return pSessions.getSpeeches();
    }

    /**
     * List all Agenda Items
     * @return
     */
    public Set<AgendaItem> listItems(){
        return pSessions.getItems();
    }



}
