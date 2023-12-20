package org.texttechnologylab.nicolas.data.models;

/**
 * Base class for all objects
 * @author Nicolas Calderon
 */
public interface ClassObject {

    /**
     * Get id of the OBJECT
     * @return
     */
    String getID();

    /**
     * Set the id of the OBJECT
     * @param id to be assigned
     */
    void setID(String id);

    /**
     * Get label of the OBJECT
     * @return
     */
    String getLabel();

    /**
     * Set the label of the OBJECT
     * @param label
     */
    void setLabel(String label);

    /**
     * Compares if 2 objects are the same (based on the ID)
     * @param o object to be compare with
     * @return TRUE if ID is the same, else FALSE
     */
    boolean compareObject(ClassObject o);
}
