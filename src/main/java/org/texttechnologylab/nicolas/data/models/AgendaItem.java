package org.texttechnologylab.nicolas.data.models;

import java.util.List;

public interface AgendaItem extends ClassObject{

    /**
     * Get the title of the item
     * @return
     */
    String getTitle();

    /**
     * Set the title of the item
     * @param title
     */
    void setTitle(String title);

    /**
     * Get the speeches
     * @return
     */
    List<Speech> getSpeeches();

    /**
     * Set the speeches
     * @param speeches
     */
    void setSpeeches(List<Speech> speeches);

    /**
     * Get the comments
     * @return
     */
    List<Comment> getComments();

    /**
     * Set the comments
     * @param comments
     */
    void setComments(List<Comment> comments);

    /**
     * Get the plenary session
     * @return
     */
    PlenarySession getPlenarySession();

    /**
     * Set the plenary session
     * @param plenarySession
     */
    void setPlenarySession(PlenarySession plenarySession);

}
