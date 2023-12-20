package org.texttechnologylab.nicolas.data.parsers;

import org.texttechnologylab.nicolas.data.impl.local.AgendaItem_File_Impl;
import org.texttechnologylab.nicolas.data.impl.local.BundestagFactory;
import org.texttechnologylab.nicolas.data.impl.local.PlenarySession_File_Impl;
import org.texttechnologylab.nicolas.data.models.AgendaItem;
import org.texttechnologylab.nicolas.data.models.PlenarySession;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProtocolParser {

    /**
     * Initialization based on a file
     * @param pFile
     */
    public static void ParseProtocol(File pFile, BundestagFactory pFactory) throws ParserConfigurationException, IOException, SAXException, ParseException {

        // Pattern for number of protocol
        Pattern pattern = Pattern.compile("\\d+");

        // Define Date and Time Format
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document pDoc = db.parse(pFile);

        // Creates the Plenary Seassion where the informtion is going to be saved.
        PlenarySession ps = new PlenarySession_File_Impl(pFactory);


        // Extract information of the header of the file, by use of a helper method
        Node whlPeriode = getNodeFromXML(pDoc, "wahlperiode");
        Node nSitzungsnummer = getNodeFromXML(pDoc, "sitzungsnr");
        Node nTitle = getNodeFromXML(pDoc, "plenarprotokoll-nummer");
        Node nOrt = getNodeFromXML(pDoc, "ort");

        Matcher match = pattern.matcher(nSitzungsnummer.getTextContent());

        if (match.find()){
            ps.setSessionNumber(Integer.parseInt(match.group()));
        } else {
            ps.setSessionNumber(Integer.valueOf(nSitzungsnummer.getTextContent()));
        }

        ps.setLegislativePeriod(Integer.valueOf(whlPeriode.getTextContent()));
        ps.setTitle(nTitle.getTextContent());
        ps.setPlace(nOrt.getTextContent());

        Node nDatum = getNodeFromXML(pDoc, "datum");
        Date pDate = new Date(sdfDate.parse(nDatum.getAttributes().getNamedItem("date").getTextContent()).getTime());
        ps.setDate(pDate);

        // extract the start of a session, can't make start and end together because if the start fails the end is not
        // going to be extracted and/or stored.
        Node nStart = getNodeFromXML(pDoc, "sitzungsbeginn");
        String sStartTime = nStart.getAttributes().getNamedItem("sitzung-start-uhrzeit").getTextContent();
        Time pStartTime = null;
        sStartTime = sStartTime.replaceAll("\\.", ":");
        sStartTime = sStartTime.replace(" Uhr", "");
        try {
            pStartTime = new Time(sdfTime.parse(sStartTime).getTime());
        }
        catch (ParseException pe){
            pStartTime = new Time(sdfTime.parse(nStart.getAttributes().getNamedItem("sitzung-start-uhrzeit")
                    .getTextContent()
                    .replaceAll("\\.", ":")).getTime());
        }
        ps.setSessionStart(pStartTime);

        // extract the end of a session
        Node nEnde = getNodeFromXML(pDoc, "sitzungsende");
        String sEndTime = nEnde.getAttributes().getNamedItem("sitzung-ende-uhrzeit").getTextContent();
        sEndTime = sEndTime.replaceAll("\\.", ":");
        sEndTime = sEndTime.replace(" Uhr", "");
        Time pEndTime = null;
        try {
            pEndTime = new Time(sdfTime.parse(sEndTime).getTime());
        }
        catch (ParseException pe){
            try {
                pEndTime = new Time(sdfTime.parse(nEnde.getAttributes().getNamedItem("sitzung-ende-uhrzeit")
                        .getTextContent()
                        .replaceAll("\\.", ":")).getTime());
            }
            catch (ParseException e){
                System.err.println(e.getMessage());
            }
        }
        ps.setSessionEnd(pEndTime);

        // Get all the Agenda Items
        List<AgendaItem> agendaItemList = getAgendaItems(ps, pDoc, pFactory);
        ps.setAgendaItems(agendaItemList);

        pFactory.addSession(ps);
    }

    /**
     * Return a Node by his tag-name
     * @param pDocument where the node is
     * @param tag to look for
     * @return node in that document with that tag-name
     */
    private static Node getNodeFromXML(Document pDocument, String tag) {
        return pDocument.getElementsByTagName(tag).item(0);
    }

    /**
     * Get all the Agenda Items and process them
     * @param pDoc document to be worked
     * @param pFactory from super
     * @return list with all the items.
     */
    private static List<AgendaItem> getAgendaItems(PlenarySession ps,Document pDoc, BundestagFactory pFactory){

        List<AgendaItem> r = new ArrayList<>(0);

        NodeList blocks = pDoc.getElementsByTagName("ivz-block");

        for(int i=0; i<blocks.getLength(); i++){

            Node n = blocks.item(i);

            AgendaItem agendaItem = new AgendaItem_File_Impl(n, pFactory, pDoc, ps);
            r.add(agendaItem);

        }
        return r;
    }
}
