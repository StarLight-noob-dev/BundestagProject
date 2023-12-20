package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.Types;

/**
 * A simplier initialization for an Abgeordneter
 * @author Nicolas Calderon
 */
public class Abgeordneter_Simple_File_Impl extends Abgeordneter_File_Impl {

    /**
     * Contructor
     * @param fName name of the person
     * @param lName last name of the person
     * @param pFactory from super
     */
    public Abgeordneter_Simple_File_Impl(String fName, String lName, BundestagFactory pFactory){
        super(pFactory);
        setFirstName(fName);
        setLastName(lName);
        setParty(pFactory.pGroups.getPartyByNameOrCreate("NONE"));
        setFaction(pFactory.pGroups.getFactionByNameOrCreate("NONE"));
        setGeschlecht(Types.SEX.NONE);
    }

}
