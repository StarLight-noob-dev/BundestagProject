package org.texttechnologylab.nicolas.data.impl.local;

import org.texttechnologylab.nicolas.data.models.*;
import org.texttechnologylab.nicolas.data.parsers.XMLParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implements the speeches
 * @author Nicolas Calderon
 */
public class Speech_File_Impl extends ClassObject_Impl implements Speech {

    private Abgeordneter speaker;
    private PlenarySession plenarySession;
    private AgendaItem agendaItem;
    private List<String> text = new ArrayList<>(0);
    private List<Comment> comments = new ArrayList<>(0);

    /**
     * Constructor
     */
    public Speech_File_Impl(AgendaItem agendaItem, BundestagFactory pFactory, Node node) {
        super(pFactory);
        setAgendaItem(agendaItem);
        setPlenarySession(agendaItem.getPlenarySession());
        init(node);
    }

    /**
     * Initializer for the speech
     * @param node
     */
    private void init(Node node) {

        setID(node.getAttributes().getNamedItem("id").getTextContent());

        // get Redner
        NodeList nL = node.getChildNodes();

        boolean active = false;

        for (int a = 0; a < nL.getLength(); a++) {
            Node n = nL.item(a);

            switch (n.getNodeName()) {

                case "p":

                    String sCase = "";
                    if (n.hasAttributes()) {
                        sCase = n.getAttributes().getNamedItem("klasse").getTextContent();
                    }

                    switch (sCase) {
                        case "redner":

                            active = true;

                            Node name = XMLParser.getSingleNodeFromNodesXML(n,"vorname");
                            Node nachname = XMLParser.getSingleNodeFromNodesXML(n, "nachname");

                            assert name != null;
                            assert nachname != null;
                            Abgeordneter nSpeaker = pFactory.pAbgeordneters.getAbgeordneterByName(name.getTextContent(), nachname.getTextContent());

                            nSpeaker.addSpeech(this);
                            this.speaker = nSpeaker;
                            break;

                        default:
                            if (active){
                            setText(n.getTextContent());
                            }
                            break;
                    }

                    break;

                case "name":

                    active = false;

                    /* // Tried to do something here but was not sure so better leave outside
                    String s = n.getTextContent();
                    String[] words = s.split(" ");

                    for (int i = 0; i < words.length; i++) {
                        words[i] = words[i].trim();
                    }

                    Abgeordneter nSpeaker = pFactory.pAbgeordneters.getAbgeordneterByName(words[1], words[2]);

                     */
                    break;

                case "kommentar":
                    // As whom did the comment is not important I didn't really took the time to implement it.
                    Comment_File_Impl pComment = new Comment_File_Impl(pFactory);
                    pComment.setContent(n.getTextContent());
                    pComment.setSpeech(this);
                    comments.add(pComment);
                    break;
            }
        }
    }

    @Override
    public AgendaItem getAgendaItem() {
        return agendaItem;
    }

    @Override
    public void setAgendaItem(AgendaItem agendaItem) {
        this.agendaItem = agendaItem;
    }

    @Override
    public List<String> getText() {
        return text;
    }

    @Override
    public int getTextLength() {
        AtomicInteger l = new AtomicInteger();
        text.forEach(t -> l.addAndGet(t.length()));
        return l.get();
    }

    @Override
    public void setText(String text) {
        this.text.add(text);
    }

    @Override
    public Abgeordneter getSpeaker() {
        return speaker;
    }

    @Override
    public void setSpeaker(Abgeordneter speaker) {
        this.speaker = speaker;
    }

    @Override
    public PlenarySession getPlenarySession() {
        return plenarySession;
    }

    @Override
    public void setPlenarySession(PlenarySession plenarySession) {
        this.plenarySession = plenarySession;
    }

    @Override
    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
