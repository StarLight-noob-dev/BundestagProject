package org.texttechnologylab.nicolas.data.parsers;

import org.texttechnologylab.nicolas.data.exceptions.StammDatenParserException;
import org.texttechnologylab.nicolas.data.impl.local.Abgeordneter_File_Impl;
import org.texttechnologylab.nicolas.data.impl.local.BundestagFactory;
import org.texttechnologylab.nicolas.data.models.Abgeordneter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * Class that gets all the information from the Stammdaten all centralized for easy readability
 * @author Nicolas Calderon
 */
public class StammdatenParser {
    public static void StammdatenMDBParserBasic(File file, BundestagFactory pFactory) throws IOException, SAXException {

        try {
            DocumentBuilderFactory DocFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder DocBuild = DocFactory.newDocumentBuilder();

            Document document = DocBuild.parse(file);

            NodeList Nodes = document.getElementsByTagName("MDB");

            for (int i = 0; i < Nodes.getLength(); i++) {

                Node Parent = Nodes.item(i);

                Node nodeID = XMLParser.getSingleNodeFromNodesXML(Parent, "ID");

                Abgeordneter a = pFactory.listAbgeordneter(nodeID.getTextContent());

                if (a != null){
                    // If not null the person was already created.
                    try {
                        throw new StammDatenParserException("This person was already found");
                    } catch (StammDatenParserException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("This person %s was already found", a.fullNameWithoutTitle());
                } else {
                    // Creates the Abgeordneter and add it to the factory
                    Abgeordneter pAbgeordneter = new Abgeordneter_File_Impl(Parent, pFactory);
                    pAbgeordneter.setID(nodeID.getTextContent());
                }
            }
        } catch (ParserConfigurationException | ParseException e) {
            e.printStackTrace();
        }
    }
}
