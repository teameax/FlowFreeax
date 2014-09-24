package is.ru.flowfreeax.services;

import android.content.Context;
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
    //region Member Variables
    public Global mGlobals = Global.getInstance();
    private Context context;
    //endregion

    public XmlReader(Context context) {
        this.context = context;
    }

    /**
     * Reads the regular puzzles into memory and database
     * @param is
     */
    public List<Puzzle> openRegular(InputStream is){

        List<Puzzle> puzzles                = new ArrayList<Puzzle>();
        try{
            DocumentBuilderFactory dbFactory    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder            = dbFactory.newDocumentBuilder();
            Document doc                        = dBuilder.parse(is);
            NodeList cList                      = doc.getElementsByTagName( "challenge" );


            for (int i = 0; i < cList.getLength(); i++) {
                puzzles.addAll(readIntoDatabase(cList.item(i).getChildNodes(), "regular"));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return puzzles;
    }

    /**
     * Reads the time trial a.k.a mania puzzles into memory and database
     * @param is
     */
    public List<Puzzle> openMania(InputStream is) {

        List<Puzzle> puzzles                = new ArrayList<Puzzle>();
        try{
            DocumentBuilderFactory dbFactory    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder            = dbFactory.newDocumentBuilder();
            Document doc                        = dBuilder.parse( is );
            NodeList cList                      = doc.getElementsByTagName( "challenge" );

            for (int i = 0; i < cList.getLength(); i++) {
                puzzles.addAll(readIntoDatabase(cList.item(i).getChildNodes(), "mania"));
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return puzzles;
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //region Private Helpers

    private List<String> readFlows(String flows) {

        String[] strings = flows.split(",");
        List<String> result = new ArrayList<String>();

        for (String s : strings) {
            result.add(s.substring(s.indexOf("(") + 1, s.indexOf(")")));
        }

        return result;
    }

    private List<Puzzle> readIntoDatabase(NodeList nodeList, String type) {
        PuzzlesAdapter puzzlesAdapter       = new PuzzlesAdapter( context );
        List<Puzzle> puzzleList             = new ArrayList<Puzzle>();

        for ( int c=0; c<nodeList.getLength(); ++c ) {
            Node nNode = nodeList.item(c);

            if ( nNode.getNodeType() == Node.ELEMENT_NODE ) {
                Element eNode = (Element) nNode;
                String size = eNode.getElementsByTagName( "size" ).item(0).getFirstChild().getNodeValue();
                String flows = eNode.getElementsByTagName( "flows" ).item(0).getFirstChild().getNodeValue();
                puzzleList.add( new Puzzle( Integer.parseInt(size), readFlows(flows), type, Integer.parseInt(eNode.getAttribute("id")) ));
                long value = puzzlesAdapter.insertPuzzleIfNew(Integer.parseInt(eNode.getAttribute("id")),
                        Integer.parseInt(size), type, false, -1);
                System.out.println(value);
            }
        }
        return puzzleList;
    }
    //endregion
}
