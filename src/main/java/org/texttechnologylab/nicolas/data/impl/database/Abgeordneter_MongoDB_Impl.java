package org.texttechnologylab.nicolas.data.impl.database;

import org.bson.Document;
import org.texttechnologylab.nicolas.data.Types;
import org.texttechnologylab.nicolas.data.impl.local.Abgeordneter_File_Impl;
import org.texttechnologylab.nicolas.data.models.*;
import org.texttechnologylab.nicolas.database.MongoDBConnectionHandler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Abgeordneter_MongoDB_Impl extends MongoDBConnectionHandler implements Abgeordneter {

    // Name of the collection
    public static final String COLLECTION_NAME = "Abgeordneter";

    // IDs
    private String ID;
    private String bankID;
    private String label;

    // Personal Information
    // Name Info
    private String FirstName = "";
    private String LastName = "";
    private String OrtZusatz = "";
    private String AdelSuffix = "";
    private String Anrede = "";
    private String AcademicTitle = "";


    // Biographic Info
    private Date BornOnDate = null;
    private Date DeathOnDate = null;
    private String BornInCity = "";
    private String BornInCountry = "";
    private Types.SEX Sex = null;
    private String Religion = "";
    private String Work = "";
    private String VitaShort = "";


    // Variables from MOP
    private Role pRole = null;
    private Party pParty = null;
    private String partyID;
    private Faction pFaction = null;
    private String factionID;
    private List<Speech> Speeches = new ArrayList<>(0);
    private List<String> speechesID = new ArrayList<>(0);
    private List<Comment> Comments = new ArrayList<>(0);
    private List<String> commentsID = new ArrayList<>(0);


    public Abgeordneter_MongoDB_Impl(Document ab){
        this.bankID = (String) ab.get("_id");
        this.ID = (String) ab.get("id");

        this.FirstName = (String) ab.get("name");
        this.LastName = (String) ab.get("lastname");
        this.OrtZusatz = (String) ab.get("ortzusatz");
        this.AdelSuffix = (String) ab.get("adelsuffix");
        this.Anrede = (String) ab.get("anrede");
        this.AcademicTitle = (String) ab.get("title");
        this.BornOnDate = (Date) ab.get("born");
        this.BornInCity = (String) ab.get("bornplace");
        this.DeathOnDate = (Date) ab.get("death");
        this.Sex = SexHelper((String) ab.get("sex"));
        this.Religion = (String) ab.get("religion");
        this.partyID = (String) ab.get("party");
        this.factionID = (String) ab.get("faction");
        this.speechesID = (List<String>) ab.get("speech");
        this.commentsID = (List<String>) ab.get("comments");
    }

    @Override
    public Types.SEX SexHelper(String sex) {
        return sex.startsWith("m") ? Types.SEX.MEN : Types.SEX.WOMAN;
    }

    @Override
    public String getFirstName() {
        return FirstName;
    }

    @Override
    public void setFirstName(String firstName) {
        updateField(COLLECTION_NAME, bankID, "name", firstName);
        this.FirstName = firstName;
    }

    @Override
    public String getLastName() {
        return LastName;
    }

    @Override
    public void setLastName(String lastName) {
        updateField(COLLECTION_NAME, bankID, "lastname", lastName);
        this.LastName = lastName;
    }

    @Override
    public String getTitle() {
        return AcademicTitle;
    }

    @Override
    public void setTitle(String title) {
        updateField(COLLECTION_NAME, bankID, "academictitle", title);
        this.AcademicTitle = title;
    }

    @Override
    public String getOrtszusatz() {
        return OrtZusatz;
    }

    @Override
    public void setOrtszusatz(String ort) {
        updateField(COLLECTION_NAME, bankID, "ortzusatz", ort);
        this.OrtZusatz = ort;
    }

    @Override
    public String getAdelssuffix() {
        return AdelSuffix;
    }

    @Override
    public void setAdelssuffix(String adelssuffix) {
        updateField(COLLECTION_NAME, bankID, "adelsuffix", adelssuffix);
        this.AdelSuffix = adelssuffix;
    }

    @Override
    public String getAnrede() {
        return Anrede;
    }

    @Override
    public void setAnrede(String anrede) {
        updateField(COLLECTION_NAME, bankID, "anrede", anrede);
        this.Anrede = anrede;
    }

    @Override
    public Date getGeburtsDatum() {
        return BornOnDate;
    }

    @Override
    public void setGeburtsDatum(Date born) {
        updateField(COLLECTION_NAME, bankID, "lastname", born);
        this.BornOnDate = born;
    }

    @Override
    public String getGeburtsOrt() {
        return BornInCity;
    }

    @Override
    public void setGeburtsOrt(String ort) {
        updateField(COLLECTION_NAME, bankID, "bornplace", ort);
        this.BornInCity = ort;
    }

    @Override
    public Date getSterbeDatum() {
        return DeathOnDate;
    }

    @Override
    public void setSterbeDatum(Date died) {
        updateField(COLLECTION_NAME, bankID, "death", died);
        this.DeathOnDate = died;
    }

    @Override
    public Types.SEX getGeschlecht() {
        return Sex;
    }

    @Override
    public void setGeschlecht(Types.SEX sex) {
        updateField(COLLECTION_NAME, bankID, "sex", sex);
        this.Sex = sex;
    }

    @Override
    public String getReligion() {
        return Religion;
    }

    @Override
    public void setReligion(String religion) {
        updateField(COLLECTION_NAME, bankID, "religion", religion);
        this.Religion = religion;
    }

    @Override
    public String getBeruf() {
        return Work;
    }

    @Override
    public void setBeruf(String beruf) {
        updateField(COLLECTION_NAME, bankID, "work", beruf);
        this.Work = beruf;
    }

    @Override
    public Faction getFaction() {
        if (this.factionID == null) return null;
        if (this.pFaction == null){

            this.pFaction = new Faction_MongoDB_Impl();
        }
        return pFaction;
    }

    @Override
    public void setFaction(Faction faction) {
        updateField(COLLECTION_NAME, bankID, "faction", faction.getID());
        this.pFaction = faction;
        this.factionID = faction.getID();
    }

    @Override
    public Party getParty() {
        //TODO check this
        if (this.partyID == null) return null;
        //if (this.pParty == null) this.pParty = Party_MongoDB_Impl.retrieveById(this.partyID);
        return pParty;
    }

    @Override
    public void setParty(Party party) {
        updateField(COLLECTION_NAME, bankID, "party", party.getID());
        this.pParty = party;
        this.partyID = party.getID();
    }

    @Override
    public List<Comment> getComments() {
        return Comments;
    }

    @Override
    public void setComments(List<Comment> comments) {
        updateField(COLLECTION_NAME, bankID, "comments", comments);
        this.Comments = comments;
        this.commentsID = comments.stream().map(ClassObject::getID).collect(Collectors.toList());
    }

    @Override
    public List<Speech> getSpeeches() {

        //TODO CHECK THIS
        return this.Speeches;
        /*
        if (this.speechesID.isEmpty()){return null;}

        if (this.Speeches.isEmpty()){

        }

         */
    }

    @Override
    public void setSpeeches(List<Speech> speeches) {
        updateField(COLLECTION_NAME, bankID, "speech", speeches);
        this.Speeches = speeches;
        this.speechesID = speeches.stream().map(ClassObject::getID).collect(Collectors.toList());
    }

    @Override
    public Role getRole() {
        // TODO not implemented
        return pRole;
    }

    @Override
    public void setRole(Role role) {
        this.pRole = role;
    }

    @Override
    public boolean isRepresentative() {
        // TODO not implemented
        return false;
    }

    @Override
    public void setRepresentative(boolean representative) {
        // TODO not implemented
    }

    @Override
    public String asNiceString() {
        return String.format("%1$s %2$s %3$s, in der Partei %4$s, in der Fraktion %5$s", Anrede, FirstName, LastName,
                (pParty == null) ? "UNKNOWN" : pParty.getName(),
                (pFaction == null) ? "UNKNOWN" : pFaction.getName());
    }

    @Override
    public String fullNameWithoutTitle() {
        return FirstName + " " + LastName;
    }

    @Override
    public String fullName() {
        return ((Anrede == "") ? "" : (Anrede + " ")) + FirstName + " " + LastName;
    }

    @Override
    public void addSpeech(Speech speech) {
        this.Speeches.add(speech);
    }

    @Override
    public void addCommnet(Comment comment) {
        this.Comments.add(comment);
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void setID(String id) {
        updateField(COLLECTION_NAME, bankID, "id", id);
        this.ID = id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean compareObject(ClassObject o) {
        return this.ID.equalsIgnoreCase(o.getID());
    }

    /**
     * Transform an Abgeordneter into a Document which can be store in a MongoDB
     * @param abgeordneter to be transformed
     * @return Document of the abgeordneter
     */
    public static Document toDocument(Abgeordneter abgeordneter){
        Document doc = new Document();

        doc.put("id", abgeordneter.getID());
        doc.put("name", abgeordneter.getFirstName());
        doc.put("lastname", abgeordneter.getLastName());
        doc.put("ortzusatz", abgeordneter.getOrtszusatz());
        doc.put("adelsuffix", abgeordneter.getAdelssuffix());
        doc.put("anrede", abgeordneter.getAnrede());
        doc.put("academictitle", abgeordneter.getTitle());
        doc.put("born", abgeordneter.getGeburtsDatum());
        doc.put("bornplace", abgeordneter.getGeburtsOrt());
        doc.put("death", abgeordneter.getSterbeDatum());
        doc.put("sex", String.valueOf(abgeordneter.getGeschlecht()));
        doc.put("religion", abgeordneter.getReligion());
        doc.put("work", abgeordneter.getBeruf());
        doc.put("party", abgeordneter.getParty().getID());
        doc.put("faction", abgeordneter.getFaction().getID());
        doc.put("speech", abgeordneter.getSpeeches().stream()
                .map(ClassObject::getID)
                .collect(Collectors.toList()));
        doc.put("comments", abgeordneter.getComments().stream()
                .map(ClassObject::getID)
                .collect(Collectors.toList()));

        return doc;
    }
}
