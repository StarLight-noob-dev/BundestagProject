package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.helper.Generator;
import org.texttechnologylab.nicolas.data.models.ClassObject;

/**
 * Implementation of the ClassObject
 * @author Nicolas Calderon
 */
public class ClassObject_Impl implements ClassObject {

    private String sID;
    private String sLabel;
    protected BundestagFactory pFactory;

    public ClassObject_Impl(BundestagFactory pFactory) {
        this.pFactory = pFactory;
    }

    public ClassObject_Impl(String id, BundestagFactory pFactory){
        this.pFactory = pFactory;
        this.sID = id;
    }

    @Override
    public String getID() {
        return sID;
    }

    @Override
    public void setID(String id) {
        this.sID = id;
    }

    @Override
    public String getLabel() {
        return sLabel;
    }

    @Override
    public void setLabel(String label) {
        this.sLabel = label;
    }

    @Override
    public boolean compareObject(ClassObject o) {
        return this.getID() == o.getID();
    }
}
