package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.helper.Generator;
import org.texttechnologylab.nicolas.data.models.Abgeordneter;
import org.texttechnologylab.nicolas.data.models.Group;

import java.util.HashSet;
import java.util.Set;

public class Group_File_Impl extends ClassObject_Impl implements Group {

    private String sName;
    private Set<Abgeordneter> Members =  new HashSet<>(0);

    public Group_File_Impl(BundestagFactory pFactory){
        super(pFactory);
        setID(Generator.generateID());
    }

    @Override
    public String getName() {
        return sName;
    }

    @Override
    public void setName(String name) {
        this.sName = name;
    }

    @Override
    public void addMember(Abgeordneter abgeordneter) {
        this.Members.add(abgeordneter);
    }

    @Override
    public void addMembers(Set<Abgeordneter> abgeordneters) {
        abgeordneters.forEach(this::addMember);
    }

    @Override
    public Set<Abgeordneter> getMembers() {
        return Members;
    }

    @Override
    public Abgeordneter getMember(String vorname, String nachname) {
        //TODO
        return null;
    }

    @Override
    public Abgeordneter getMember(String id) {
        return null;
        //TODO
    }
}
