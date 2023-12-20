package org.texttechnologylab.nicolas.data.models;

import java.util.Set;

public interface Group extends ClassObject{

    /**
     * Get the name of the Group
     * @return
     */
    String getName();

    /**
     * Set the name of the group
     * @param name
     */
    void setName(String name);

    /**
     * Adds an Abgordneter to the Group
     * @param abgeordneter
     */
    void addMember(Abgeordneter abgeordneter);

    /**
     * Add many Abgeordneters to the group
     * @param abgeordneters
     */
    void addMembers(Set<Abgeordneter> abgeordneters);

    /**
     * Get all members of the group
     * @return
     */
    Set<Abgeordneter> getMembers();

    /**
     * Get abgeordneter base on name
     * @param vorname
     * @param nachname
     * @return
     */
    Abgeordneter getMember(String vorname, String nachname);

    /**
     * Get abgeordneter based on ID
     * @param id
     * @return
     */
    Abgeordneter getMember(String id);
}
