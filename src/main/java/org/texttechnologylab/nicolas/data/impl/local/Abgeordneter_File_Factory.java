package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.models.Abgeordneter;
import org.texttechnologylab.nicolas.data.models.Faction;
import org.texttechnologylab.nicolas.data.models.Party;
import org.w3c.dom.Node;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores and controlls all related to Abgeordneter
 * @author Nicolas Calderon
 */
public class Abgeordneter_File_Factory {

    protected BundestagFactory upper_factory;
    private Set<Abgeordneter> Abgeordneters = new HashSet<>(0);

    /**
     * Constructor
     */
    public Abgeordneter_File_Factory(BundestagFactory pFactory){
        this.upper_factory = pFactory;
    }


    /**
     * Return all Abgeorneter that exist
     * @return set of all abgeordneter
     */
    public Set<Abgeordneter> getAbgeordneters(){
        return Abgeordneters;
    }

    /**
     * Return all Abgeordneter that are members of a certain party
     * @param party they are members of
     * @return The set of members
     */
    public Set<Abgeordneter> getAbgeordneters(Party party){
        return party.getMembers();
    }

    /**
     * Return all Abgeordneter that are members of a certain faction
     * @param faction they are members of
     * @return The set of members
     */
    public Set<Abgeordneter> getAbgeorneters(Faction faction){
        return faction.getMembers();
    }

    /**
     * Add an abgeordneter to the List
     * @param abgeordneter to be added
     */
    public void addAbgeordneter(Abgeordneter abgeordneter){
        this.Abgeordneters.add(abgeordneter);
    }

    /**
     * Add many abgeorneter to the list
     * @param abgeordneters to be added
     */
    public void addManyAbgeorneter(Set<Abgeordneter> abgeordneters){
        abgeordneters.forEach(this::addAbgeordneter);
    }

    /**
     * Create a simple version of the Abgeordneter with just the name and last name
     * @param fName First name of the person
     * @param lName Last name of the person
     * @return an instance of Abgeordneter with the given data.
     */
    public Abgeordneter createAbgeordneterFromName(String fName, String lName){
        Abgeordneter t = new Abgeordneter_Simple_File_Impl(fName, lName, upper_factory);
        addAbgeordneter(t);
        return t;
    }

    /**
     * Looks by the name of the person and retrieves it if it exist, else create a new instance
     * @param firstName to look for
     * @param lastName to look for
     * @return the Abgeordneter
     */
    public Abgeordneter getAbgeordneterByName(String firstName, String lastName) {
        String temp = firstName + " " + lastName;
        return Abgeordneters.stream()
                .filter(a -> a.fullNameWithoutTitle().equals(temp))
                .findFirst()
                .orElseGet(() -> createAbgeordneterFromName(firstName, lastName));
    }

    /**
     * Returns an Abgeordneter based on a given ID.
     * @param id to look for
     * @return Abgeordneter with that ID
     */
    public Abgeordneter getAbgeordneterByID(String id){
        return Abgeordneters.stream()
                .filter(a -> a.getID().equals(id))
                .findFirst()
                .orElse(null);
    }
}
