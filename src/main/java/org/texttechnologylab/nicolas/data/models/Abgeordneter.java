package org.texttechnologylab.nicolas.data.models;

import org.texttechnologylab.nicolas.data.Types;

import java.sql.Date;
import java.util.List;

public interface Abgeordneter extends ClassObject{

    /**
     * Helper Function to determine the right Sex.
     * @param sex, String written in the file that has to be changed to the Type.SEX
     * @return the corresponding Type.Sex based on the given string
     */
    Types.SEX SexHelper(String sex);

    /**
     * Get the first name
     * @return
     */
    String getFirstName();

    /**
     * Set the first name
     * @param firstName
     */
    void setFirstName(String firstName);

    /**
     * Get the last name
     * @return
     */
    String getLastName();

    /**
     * Set the last name
     * @param lastName
     */
    void setLastName(String lastName);

    /**
     * Get the title
     * @return
     */
    String getTitle();

    /**
     * Set the title
     * @param title
     */
    void setTitle(String title);

    /**
     * Get the Ortszusatz
     * @return
     */
    String getOrtszusatz();

    /**
     * Set the Ortzusatz
     * @param ort to be added
     */
    void setOrtszusatz(String ort);

    /**
     * Get the Adel-suffix
     * @return
     */
    String getAdelssuffix();

    /**
     * Set the Adel-suffix
     * @param adelssuffix to be added
     */
    void setAdelssuffix(String adelssuffix);

    /**
     * Get the Anrede of the Abgeordneter
     * @return
     */
    String getAnrede();

    /**
     * Set the anrede
     * @param anrede
     */
    void setAnrede(String anrede);

    /**
     * Get the date the person was born
     * @return
     */
    Date getGeburtsDatum();

    /**
     * Set the date the person was born
     * @param born
     */
    void setGeburtsDatum(Date born);

    /**
     * Get the place the person was born
     * @return
     */
    String getGeburtsOrt();

    /**
     * Set the place the person was born on
     * @param ort
     */
    void setGeburtsOrt(String ort);

    /**
     * Get the date the person died
     * @return
     */
    Date getSterbeDatum();

    /**
     * Set the date the person died
     * @param died on this date
     */
    void setSterbeDatum(Date died);

    /**
     * Get the Sex of the person
     * @return
     */
    Types.SEX getGeschlecht();

    /**
     * Set the sex of the person
     * @param sex
     */
    void setGeschlecht(Types.SEX sex);

    /**
     * Get the religion of the person
     * @return
     */
    String getReligion();

    /**
     * Set the religion
     * @param religion
     */
    void setReligion(String religion);

    /**
     * Get the work of the person
     * @return
     */
    String getBeruf();

    /**
     * Set the work of the person
     * @param beruf
     */
    void setBeruf(String beruf);

    /**
     * Get the faction
     * @return
     */
    Faction getFaction();

    /**
     * Set the faction
     * @param faction
     */
    void setFaction(Faction faction);

    /**
     * Get the party
     * @return
     */
    Party getParty();

    /**
     * Set the party
     * @param party
     */
    void setParty(Party party);

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
     * Get the role
     * @return
     */
    Role getRole();

    /**
     * Set the role
     * @param role
     */
    void setRole(Role role);

    /**
     * Get whether the person is currently a representative
     * @return
     */
    public boolean isRepresentative();

    /**
     * Set whether the person is currently a representative
     * @param representative
     */
    public void setRepresentative(boolean representative);

    /**
     * Get a nice printable string representation of the person
     * @return
     */
    String asNiceString();

    /**
     * Get the full name of the person without the title
     * @return
     */
    String fullNameWithoutTitle();

    /**
     * Get the full name of the person with the title
     * @return
     */
    String fullName();

    /**
     * Add a single speech to the list
     * @param speech
     */
    void addSpeech(Speech speech);

    /**
     * Add single comment to the list
     * @param comment
     */
    void addCommnet(Comment comment);
}
