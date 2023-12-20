package org.texttechnologylab;

import org.bson.Document;
import org.texttechnologylab.nicolas.data.exceptions.StammDatenParserException;
import org.texttechnologylab.nicolas.data.helper.FileReader;
import org.texttechnologylab.nicolas.data.impl.database.Abgeordneter_MongoDB_Impl;
import org.texttechnologylab.nicolas.data.impl.local.BundestagFactory;
import org.texttechnologylab.nicolas.data.models.Abgeordneter;
import org.texttechnologylab.nicolas.data.parsers.ProtocolParser;
import org.texttechnologylab.nicolas.data.parsers.StammdatenParser;
import org.texttechnologylab.nicolas.database.MongoDBConnectionHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Set;

public class Main {

    //TODO
    /*
    1. Abgeordneter MongoDB                 |   Done
    2. Partys MongoDB                       |   working on.
    3. Plenary seesions MongoDB             |
    4. Speeches MongoDB                     |
    5. Check all info works both ways       |
    6. Write the freacking test             |
    7. Some some menu and finish up docu    |
        - Write the readme                  |
    8. Upload
     */

    private static final String RELATIV_PATH_PC = "D:\\Programmier-Praktikum23\\";
    private static final String REDE_FILE_PATH = "Bundestagsreden20";
    private static final String MASTER_DATA_FILE_PATH = "MdB-Stammdaten/MDB_STAMMDATEN.xml";


    public static void main(String[] args) throws StammDatenParserException, IOException, SAXException {
        //MongoDBConnectionHandler.connect();
        BundestagFactory t = new BundestagFactory();

        StartImportStammdaten(RELATIV_PATH_PC+MASTER_DATA_FILE_PATH, t);

        try {
            SetUpProtocols(RELATIV_PATH_PC + REDE_FILE_PATH, t);
        } catch (ParserConfigurationException | ParseException e) {
            e.printStackTrace();
        }

        //MongoDBConnectionHandler.connect();

        for (Abgeordneter a:t.listAbgeordneter()){
            Document doc = Abgeordneter_MongoDB_Impl.toDocument(a);
            //MongoDBConnectionHandler.insert("Abgeordneter", doc);
            Abgeordneter_MongoDB_Impl asaa = new Abgeordneter_MongoDB_Impl(doc);
            asaa.asNiceString();
        }

        //MongoDBConnectionHandler.close();


        Scanner sc =  new Scanner(System.in);

        while (true){

            System.out.println("Enter q to finish");
            String a = sc.next();

            if (a.equalsIgnoreCase("q")){break;}

        }
    }



    public static void StartImportStammdaten(String path, BundestagFactory factory) throws IOException, SAXException, StammDatenParserException {

        // I dont know why sometimes this works only with suffix xml and other just with XML please check each time,
        Set<File> fileSet = FileReader.getFiles(path, "xml");

        if (fileSet.size() != 1) {
            throw new StammDatenParserException("Tried to parse the Stammdaten but multiple or no resources were found");
        }

        for (File file : fileSet) {
            if (file.getName().equalsIgnoreCase("MDB_STAMMDATEN.xml")) {
                StammdatenParser.StammdatenMDBParserBasic(file, factory);
            } else {throw new StammDatenParserException("The file 'MDB_STAMMDATEN.xml' was not found");}
        }
    }

    public static void SetUpProtocols(String path, BundestagFactory factory) throws ParserConfigurationException, IOException, ParseException, SAXException {

        Set<File> fileSet = FileReader.getFiles(path, "xml");

        int i = 10; // For testing purposes.

        for (File f:fileSet) {
            ProtocolParser.ParseProtocol(f, factory);
        }
    }
}