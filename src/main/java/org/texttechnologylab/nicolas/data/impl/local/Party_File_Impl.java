package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.models.Party;

public class Party_File_Impl extends Group_File_Impl implements Party {

    public Party_File_Impl(String name, BundestagFactory pFactory){
        super(pFactory);
        setName(name);
    }

}
