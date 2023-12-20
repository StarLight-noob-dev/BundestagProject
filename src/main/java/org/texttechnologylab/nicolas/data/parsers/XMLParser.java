package org.texttechnologylab.nicolas.data.parsers;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to work on XML documents
 * @author Giuseppe Abrami
 */
public class XMLParser {
    public static List<Node> getAllNodesFromXML(Node pNode, String NodeName){

        List<Node> Nodes = new ArrayList<>(0);

        if (pNode.getNodeName().equals(NodeName)){
            Nodes.add(pNode);
        } else {
            if (pNode.hasChildNodes()){
                for (int i = 0; i < pNode.getChildNodes().getLength(); i++) {
                    Nodes.addAll(getAllNodesFromXML(pNode.getChildNodes().item(i), NodeName));
                }
            }else {
                if (pNode.getNodeName().equals(NodeName)){
                    Nodes.add(pNode);
                }
            }
        }
        return  Nodes;
    }

    public static Node getSingleNodeFromNodesXML(Node pNode, String NodeName){

        List<Node> Nodes = getAllNodesFromXML(pNode, NodeName);

        if(Nodes.size()>0){
            return Nodes.stream().findFirst().get();
        }
        return null;
    }
}
