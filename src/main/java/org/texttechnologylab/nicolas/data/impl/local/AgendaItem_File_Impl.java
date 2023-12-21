package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.helper.Generator;
import org.texttechnologylab.nicolas.data.models.AgendaItem;
import org.texttechnologylab.nicolas.data.models.Comment;
import org.texttechnologylab.nicolas.data.models.PlenarySession;
import org.texttechnologylab.nicolas.data.models.Speech;
import org.texttechnologylab.nicolas.data.parsers.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class AgendaItem_File_Impl extends ClassObject_Impl implements AgendaItem {

    private String title;
    private List<Speech> speeches;
    private List<Comment> comments;
    private PlenarySession plenarySession;

    /**
     * Constructor
     */
    public AgendaItem_File_Impl(Node node, BundestagFactory pFactory, Document pDoc, PlenarySession ps) {
        super(pFactory);
        this.plenarySession = ps;
        this.speeches = new ArrayList<>();
        this.comments = new ArrayList<>();
        init(node, pDoc);
        setID(Generator.generateID());
    }

    private void init(Node node, Document pDoc){

        List<Node> Nodes = XMLParser.getAllNodesFromXML(node, "ivz-block-titel");

        if(Nodes.size()==1){
            Node current = Nodes.stream().findFirst().get();

            this.setLabel(current.getTextContent().replace(":", ""));

            List<Node> contentNodes = XMLParser.getAllNodesFromXML(current, "ivz-eintrag-inhalt");
            if(contentNodes.size()>0) {
                this.setTitle(contentNodes.stream().findFirst().get().getTextContent());
            }
        }

        NodeList tagesordnungspunkt = pDoc.getElementsByTagName("tagesordnungspunkt");

        // The speeches are saved here to be pass to the class in the end.
        List<Speech> s = new ArrayList<>();

        for(int i=0; i<tagesordnungspunkt.getLength(); i++){
            Node punkt = tagesordnungspunkt.item(i);
            if(punkt.getAttributes().getNamedItem("top-id").getTextContent().equals(this.getLabel())){

                List<Node> pRede = XMLParser.getAllNodesFromXML(punkt, "rede");
                // Create a speech for each speech in the agenda
                pRede.forEach(rede -> {
                    Speech pSpeech = new Speech_File_Impl(this, pFactory, rede);
                    s.add(pSpeech);
                });
            }
        }
        this.setSpeeches(s);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<Speech> getSpeeches() {
        return speeches;
    }

    @Override
    public void setSpeeches(List<Speech> speeches) {
        if (speeches.isEmpty()){
            this.speeches = speeches;
        } else {
            this.speeches.addAll(speeches);
        }
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public PlenarySession getPlenarySession() {
        return this.plenarySession;
    }

    @Override
    public void setPlenarySession(PlenarySession plenarySession) {
        this.plenarySession = plenarySession;
    }
}
