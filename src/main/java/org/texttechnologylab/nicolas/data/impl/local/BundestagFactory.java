package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.models.Abgeordneter;
import org.texttechnologylab.nicolas.data.models.Faction;
import org.texttechnologylab.nicolas.data.models.Party;
import org.texttechnologylab.nicolas.data.models.PlenarySession;

import java.util.HashSet;
import java.util.Set;

public class BundestagFactory {

    protected Abgeordneter_File_Factory pAbgeordneters = new Abgeordneter_File_Factory(this);
    protected Group_File_Factory pGroups = new Group_File_Factory(this);

    private Set<PlenarySession> Sessions = new HashSet<>(0);

    public BundestagFactory(){}


    public void addSession(PlenarySession s){
        this.Sessions.add(s);
    }

    public Set<Abgeordneter> listAbgeordneter(){
        return pAbgeordneters.getAbgeordneters();
    }

    public Abgeordneter listAbgeordneter(String id){
        return pAbgeordneters.getAbgeordneterByID(id);
    }

    public Set<Party> listParties(){
        return pGroups.getParties();
    }

    public Set<Faction> listFactions(){
        return pGroups.getFactions();
    }



}
