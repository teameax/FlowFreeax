package is.ru.flowfreeax;

import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.List;

/**
 * Created by joddsson on 18.9.2014.
 */
public class xmlReader {
    public void openRegular(InputStream is, List<RegularChallenge> packs){
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( is );
            NodeList nList = doc.getElementsByTagName( "puzzle" );
            for ( int c=0; c<nList.getLength(); ++c ) {
                Node nNode = nList.item(c);

                if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eNode = (Element) nNode;
                    String size = eNode.getElementsByTagName( "size" ).item(0).getFirstChild().getNodeValue();
                    String flows = eNode.getElementsByTagName( "flows" ).item(0).getFirstChild().getNodeValue();
                    packs.add( new RegularChallenge( size, flows));
                }
            }
            for (RegularChallenge i : packs){
                Log.d("Flows", i.getFlows());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // Read the xml files containing the bubble placements.
    public void readPack(InputStream is, List<Pack> packs) {
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse( is );
            NodeList nList = doc.getElementsByTagName( "pack" );
            for ( int c=0; c<nList.getLength(); ++c ) {
                Node nNode = nList.item(c);

                if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eNode = (Element) nNode;
                    String name = eNode.getElementsByTagName( "name" ).item(0).getFirstChild().getNodeValue();
                    String description = eNode.getElementsByTagName( "description" ).item(0).getFirstChild().getNodeValue();
                    String file = eNode.getElementsByTagName( "file" ).item(0).getFirstChild().getNodeValue();
                    packs.add( new Pack( name, description, file ) );
                }
            }


            for (Pack i : packs){
                Log.d("Files", i.getFile());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
