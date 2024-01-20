package org.texttechnologylab;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.texttechnologylab.nicolas.data.exceptions.DBException;
import org.texttechnologylab.nicolas.data.exceptions.StammDatenParserException;
import org.texttechnologylab.nicolas.data.helper.FileReader;
import org.texttechnologylab.nicolas.data.impl.local.BundestagFactory;
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
import java.util.function.Consumer;

public class Main {

    //TODO
    /*
    1. Abgeordneter MongoDB                 |   Done
    2. Parties MongoDB                      |   Done
    3. Plenary sessions MongoDB             |   Done
    4. Speeches MongoDB                     |   Done
    5. Check all info works both ways       |   Done
    6. Write the freacking test             |
    7. Some some menu and finish up docu    |
        - Write the readme                  |
    8. Upload
     */

    private static final String RELATIV_PATH_PC = "D:\\Programmier-Praktikum23\\";
    private static final String REDE_FILE_PATH = "Bundestagsreden20";
    private static final String MASTER_DATA_FILE_PATH = "MdB-Stammdaten/MDB_STAMMDATEN.xml";


    public static void main(String[] args) throws StammDatenParserException, IOException, SAXException, DBException {

        BundestagFactory t = new BundestagFactory();

        Scanner sc =  new Scanner(System.in);

        GetMenu(sc, t);


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

    /**
     * Prints a menu for the user
     * @param sc scanner to get inputs
     * @param factory for data
     */
    public static void GetMenu(Scanner sc, BundestagFactory factory) throws StammDatenParserException, IOException, SAXException {

        int choice;
        do {

            System.out.println(
                    "\n\n\n\n\n\nWelcome, what would you like to do\n" +
                            "\t1. Import Data from Stamm daten (This muss not be chosen is going to crash)\n" +
                            "\t2. Connect to the Data base\n" +
                            "\t3. Upload data\n" +
                            "\t4. Disconnect and stop the program\n" +
                            "Please enter your choice (the number next to what you want to do): "
            );

            choice = sc.nextInt();

            if (choice < 1 || choice > 4) {
                System.out.println("The selected number is invalid. Please choose between 1-6");
            }
        } while (choice < 1 || choice > 4);

        switch (choice){
            case 1:
                System.out.println("Starting Import of Stammdaten");
                StartImportStammdaten(RELATIV_PATH_PC+MASTER_DATA_FILE_PATH, factory);
                System.out.println("[INFO] Stammdaten was Imported");

                try {
                    System.out.println("Starting Import of Protocols");
                    SetUpProtocols(RELATIV_PATH_PC + REDE_FILE_PATH, factory);
                    System.out.println("[Info] Protocols Imported");
                } catch (ParserConfigurationException | ParseException | SAXException | IOException e) {
                    e.printStackTrace();
                }
                GetMenu(sc, factory);
                break;
            case 2:
                MongoDBConnectionHandler.connect();
                GetMenu(sc, factory);
                break;
            case 3:
                MongoDBConnectionHandler.UploadData(factory);
                GetMenu(sc, factory);
                break;
            case 4:
                MongoDBConnectionHandler.close();
                CloseMessage();
                break;
        }
    }

    /**
     * Prints the end message
     */
    public static void CloseMessage(){
        System.out.println("The program will end now");
    }

    public static void CopyData(){

        // MongoDB connection URIs for both databases
        String sourceUri = "mongodb://PPR_WiSe23_144:GLJc87MY@lehre.texttechnologylab.org:27020";
        String targetUri = "mongodb://InsightBundestag_ro:bD;QI1>J$kAV8eO?@lehre.texttechnologylab.org:27020";

        // Collection names
        String sourceCollectionName = "comment";
        String targetCollectionName = "Muster-comment";

        // Connect to source and target databases
        try (MongoClient sourceClient = new MongoClient(new MongoClientURI(sourceUri));
             MongoClient targetClient = new MongoClient(new MongoClientURI(targetUri))) {

            MongoDatabase sourceDatabase = sourceClient.getDatabase("PPR_WiSe23_144");
            MongoDatabase targetDatabase = targetClient.getDatabase("Musterloesung");

            // Get the source and target collections
            MongoCollection<Document> sourceCollection = sourceDatabase.getCollection(sourceCollectionName);
            MongoCollection<Document> targetCollection = targetDatabase.getCollection(targetCollectionName);

            // Drop the target collection if it already exists
            targetCollection.drop();

            // Copy documents from source to target collection
            sourceCollection.find().forEach((Consumer<? super Document>) targetCollection::insertOne);

            System.out.println("Collection copied successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}