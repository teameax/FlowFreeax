package is.ru.flowfreeax.services;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import is.ru.flowfreeax.database.PuzzlesAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joddsson on 18.9.2014.
 */
public class XmlReader {
    public Global mGlobals = Global.getInstance();
    private Context context;
    public XmlReader(Context context) {
        this.context = context;
    }

    public void openRegular(InputStream is){
        PuzzlesAdapter puzzlesAdapter = new PuzzlesAdapter( context );

        try{
            DocumentBuilderFactory dbFactory    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder            = dbFactory.newDocumentBuilder();
            Document doc                        = dBuilder.parse( is );
            Global global                       = Global.getInstance();
            global.setContext(context);

            Node cNode                          = doc.getElementsByTagName( "challenge" ).item(0);
            NodeList nList                      = cNode.getChildNodes();
            List<Puzzle> puzzleList             = new ArrayList<Puzzle>();

            for ( int c=0; c<nList.getLength(); ++c ) {
                Node nNode = nList.item(c);
                if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                    Element eNode = (Element) nNode;
                    String size = eNode.getElementsByTagName( "size" ).item(0).getFirstChild().getNodeValue();
                    String flows = eNode.getElementsByTagName( "flows" ).item(0).getFirstChild().getNodeValue();
                    puzzleList.add( new Puzzle( Integer.parseInt(size), readFlows(flows), "regular", Integer.parseInt(eNode.getAttribute("id")) ));
                    long value = puzzlesAdapter.insertPuzzleIfNew(Integer.parseInt(eNode.getAttribute("id")),
                           Integer.parseInt(size), "regular", false);
                    System.out.println(value);
                }
            }

            global.setPuzzles(puzzleList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // Read the xml files containing the bubble placements.
    public void readPack(InputStream is, List<Pack> packs) {
        try{
            DocumentBuilderFactory dbFactory    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder            = dbFactory.newDocumentBuilder();
            Document doc                        = dBuilder.parse( is );
            NodeList nList                      = doc.getElementsByTagName( "pack" );

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

    private List<String> readFlows(String flows) {

        String[] strings = flows.split(",");
        List<String> result = new ArrayList<String>();

        for (String s : strings) {
            result.add(s.substring(s.indexOf("(") + 1, s.indexOf(")")));
        }

        return result;
    }
}
