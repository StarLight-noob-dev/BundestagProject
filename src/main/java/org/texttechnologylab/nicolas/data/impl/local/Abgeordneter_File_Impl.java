package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.*;
import org.texttechnologylab.nicolas.data.models.*;
import org.texttechnologylab.nicolas.data.parsers.XMLParser;
import org.w3c.dom.Node;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Abgeordneter
 * @author Nicolas Calderon
 */
public class Abgeordneter_File_Impl extends ClassObject_Impl implements Abgeordneter {

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
    private Faction pFaction = null;
    private List<Speech> Speeches = new ArrayList<>(0);
    private List<Comment> Comments = new ArrayList<>(0);

    /**
     * Contructor to use for the basic version of Abgeordneter
     * @param pFactory
     */
    public Abgeordneter_File_Impl(BundestagFactory pFactory){
        super(pFactory);
    }

    /**
     * Constructor for Abgeordneter
     * @param pFactory
     */
    public Abgeordneter_File_Impl(Node node, BundestagFactory pFactory) throws ParseException {
        super(pFactory);
        init(node);
        pFactory.pAbgeordneters.addAbgeordneter(this);
    }

    /**
     * Init method for the class, helps to organize how and what data is gathered.
     * @param pNode to get the data from.
     */
    private void init(Node pNode) throws ParseException {

        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");

        Node FirstName = XMLParser.getSingleNodeFromNodesXML(pNode, "VORNAME");
        Node LastName = XMLParser.getSingleNodeFromNodesXML(pNode, "NACHNAME");
        Node Sex = XMLParser.getSingleNodeFromNodesXML(pNode, "GESCHLECHT");
        Node BornOnDate = XMLParser.getSingleNodeFromNodesXML(pNode, "GEBURTSDATUM");
        Node OrtZusatz = XMLParser.getSingleNodeFromNodesXML(pNode, "ORTZUSATZ");
        Node AdelSuffix = XMLParser.getSingleNodeFromNodesXML(pNode, "ADEL");
        Node Anrede = XMLParser.getSingleNodeFromNodesXML(pNode, "ANREDE_TITEL");
        Node AcademicTitle = XMLParser.getSingleNodeFromNodesXML(pNode, "AKAD_TITEL");

        Node DeathOnDate = XMLParser.getSingleNodeFromNodesXML(pNode, "STERBEDATUM");
        Node BornInCity = XMLParser.getSingleNodeFromNodesXML(pNode, "GEBURSTORT");
        Node BornInCountry = XMLParser.getSingleNodeFromNodesXML(pNode, "GEBURSTSLAND");
        Node Religion = XMLParser.getSingleNodeFromNodesXML(pNode, "RELIGION");
        Node Work = XMLParser.getSingleNodeFromNodesXML(pNode, "BERUF");
        Node VitaShort = XMLParser.getSingleNodeFromNodesXML(pNode, "VITA_KURZ");

        // Sets the party
        Node PartyShort = XMLParser.getSingleNodeFromNodesXML(pNode, "PARTEI_KURZ");
        String pshort = PartyShort != null ? PartyShort.getTextContent() : "NONE";

        Party p = pFactory.pGroups.getPartyByNameOrCreate(pshort);
        setParty(p);


        // Looks like the faction of the person usually doesn't change so im going to get them from institution
        // and then take the first one and add it as his Faction. This MAY leave some EMPTY
        List<Node> Nodes = XMLParser.getAllNodesFromXML(pNode, "INSTITUTION");
        List<String> factionNames = new ArrayList<>(0);

        for (Node institution : Nodes) {
            Node info = XMLParser.getSingleNodeFromNodesXML(institution, "INSART_LANG");
            Node name = XMLParser.getSingleNodeFromNodesXML(institution, "INS_LANG");

            if (info.getTextContent().equalsIgnoreCase("Fraktion/Gruppe")){
                assert name != null;
                factionNames.add(name.getTextContent()) ;
            }
        }

        // So that is not null
        if (factionNames.isEmpty()){factionNames.add("NONE");}

        // Created all the factions
        factionNames.forEach(s -> pFactory.pGroups.getFactionByNameOrCreate(s));
        setFaction(pFactory.pGroups.getFactionByNameOrCreate(factionNames.get(factionNames.size()-1)));

        if (FirstName != null){setFirstName(FirstName.getTextContent());}
        if (LastName != null){setLastName(LastName.getTextContent());}
        if (Sex != null){this.Sex = SexHelper(Sex.getTextContent());}
        if (BornOnDate != null && BornOnDate.getTextContent().length()!=0){
            this.BornOnDate = new Date(sdfDate.parse(BornOnDate.getTextContent()).getTime());}
        if (OrtZusatz != null){setOrtszusatz(OrtZusatz.getTextContent());}
        if (AdelSuffix != null){setAdelssuffix(AdelSuffix.getTextContent());}
        if (Anrede != null){setAnrede(Anrede.getTextContent());}
        if (AcademicTitle != null){setTitle(AcademicTitle.getTextContent());}
        if (DeathOnDate!=null && DeathOnDate.getTextContent().length()!=0){
            this.DeathOnDate = new Date(sdfDate.parse(DeathOnDate.getTextContent()).getTime());}
        if (BornInCity!=null){this.BornInCity = BornInCity.getTextContent();}
        if (BornInCountry!= null){this.BornInCountry = BornInCountry.getTextContent();}
        if (Religion!=null){this.Religion = Religion.getTextContent();}
        if (Work!=null){this.Work = Work.getTextContent();}
        if (VitaShort!=null){this.VitaShort = VitaShort.getTextContent();}
    }

    @Override
    public Types.SEX SexHelper(String sex){
        return sex.startsWith("m") ? Types.SEX.MEN : Types.SEX.WOMAN;
    }

    @Override
    public String getFirstName() {
        return FirstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    @Override
    public String getLastName() {
        return LastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    @Override
    public String getTitle() {
        return AcademicTitle;
    }

    @Override
    public void setTitle(String title) {
        this.AcademicTitle = title;
    }

    @Override
    public String getOrtszusatz() {
        return OrtZusatz;
    }

    @Override
    public void setOrtszusatz(String ort) {
        this.OrtZusatz = ort;
    }

    @Override
    public String getAdelssuffix() {
        return AdelSuffix;
    }

    @Override
    public void setAdelssuffix(String adelssuffix) {
        this.AdelSuffix = adelssuffix;
    }

    @Override
    public String getAnrede() {
        return Anrede;
    }

    @Override
    public void setAnrede(String anrede) {
        this.Anrede =  anrede;
    }

    @Override
    public Date getGeburtsDatum() {
        return BornOnDate;
    }

    @Override
    public void setGeburtsDatum(Date born) {
        this.BornOnDate = born;
    }

    @Override
    public String getGeburtsOrt() {
        return BornInCity;
    }

    @Override
    public void setGeburtsOrt(String ort) {
        this.BornInCity = ort;
    }

    @Override
    public Date getSterbeDatum() {
        return DeathOnDate;
    }

    @Override
    public void setSterbeDatum(Date died) {
        this.DeathOnDate = died;
    }

    @Override
    public Types.SEX getGeschlecht() {
        return Sex;
    }

    @Override
    public void setGeschlecht(Types.SEX sex) {
        this.Sex = sex;
    }

    @Override
    public String getReligion() {
        return Religion;
    }

    @Override
    public void setReligion(String religion) {
        this.Religion = religion;
    }

    @Override
    public String getBeruf() {
        return Work;
    }

    @Override
    public void setBeruf(String beruf) {
        this.Work = beruf;
    }

    @Override
    public Faction getFaction() {
        return pFaction;
    }

    @Override
    public void setFaction(Faction faction) {
        this.pFaction = faction;
        faction.addMember(this);
    }

    @Override
    public Party getParty() {
        return pParty;
    }

    @Override
    public void setParty(Party party) {
        this.pParty = party;
        party.addMember(this);
    }

    @Override
    public List<Comment> getComments() {
        return Comments;
    }

    @Override
    public void setComments(List<Comment> comments) {
        if (Comments.isEmpty()) {
            this.Comments = comments;
        } else {
            Comments.addAll(comments);
        }
    }

    @Override
    public List<Speech> getSpeeches() {
        return Speeches;
    }

    @Override
    public void setSpeeches(List<Speech> speeches) {
        if (Speeches.isEmpty()) {
            this.Speeches = speeches;
        } else {
            Speeches.addAll(speeches);
        }
    }

    @Override
    public Role getRole() {
        return pRole;
    }

    @Override
    public void setRole(Role role) {
        this.pRole = role;
    }

    @Override
    public boolean isRepresentative() {
        //TODO
        return false;
    }

    @Override
    public void setRepresentative(boolean representative) {
        //TODO
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
    public void addCommnet(Comment comment){
        this.Comments.add(comment);
    }
}
