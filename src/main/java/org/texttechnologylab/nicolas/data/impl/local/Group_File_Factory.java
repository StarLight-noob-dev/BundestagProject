package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.models.Faction;
import org.texttechnologylab.nicolas.data.models.Party;

import java.util.HashSet;
import java.util.Set;

public class Group_File_Factory {

    protected BundestagFactory upper_factory;
    private Set<Party> Parties = new HashSet<>(0);
    private Set<Faction> Factions = new HashSet<>(0);

    /**
     * Constructor
     * @param pFactory
     */
    public Group_File_Factory(BundestagFactory pFactory){
        this.upper_factory = pFactory;
    }

    /**
     * Get all Parties
     * @return
     */
    public Set<Party> getParties() {
        return Parties;
    }

    /**
     * Add a Party to the set
     * @param party
     */
    public void addParty(Party party){
        this.Parties.add(party);
    }

    /**
     * Created a party based on its name and adds it to the list.
     * @param name
     * @return
     */
    public Party CreateParty(String name){
        Party p = new Party_File_Impl(name, upper_factory);
        addParty(p);
        return p;
    }

    /**
     * Get a party based on its name
     * @param name
     * @return
     */
    public Party getPartyByName(String name){
        return Parties.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a party based on its name or creates it
     * @param name
     * @return
     */
    public Party getPartyByNameOrCreate(String name){
        return Parties.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> {
                    return CreateParty(name);
                });
    }

    /**
     * Get all Factions
     * @return
     */
    public Set<Faction> getFactions() {
        return Factions;
    }

    /**
     * Add a faction to the set
     * @param faction
     */
    public void addFaction(Faction faction){
        this.Factions.add(faction);
    }

    /**
     * Created a faction based on its name and adds it to the list.
     * @param name
     * @return
     */
    public Faction CreateFaction(String name){
        Faction p = new Faction_File_File_Impl(name, upper_factory);
        addFaction(p);
        return p;
    }

    /**
     * Get a faction based on its name
     * @param name
     * @return
     */
    public Faction getFactionByNameOrCreate(String name){
        return Factions.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseGet(() -> {
                    return  CreateFaction(name);
                });
    }
}
