package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.models.Faction;

public class Faction_File_File_Impl extends Group_File_Impl implements Faction {
    public Faction_File_File_Impl(String name, BundestagFactory pFactory) {
        super(pFactory);
        setName(name);
    }
}
