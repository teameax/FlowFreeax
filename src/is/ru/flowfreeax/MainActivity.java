package is.ru.flowfreeax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import org.w3c.dom.Element;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private Global mGlobals = Global.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView image = (ImageView) findViewById(R.id.mainImgage);
        try {
            List<Pack> packs = new ArrayList<Pack>();
            readPack(getAssets().open("packs/packs.xml"), packs);
            mGlobals.mPacks = packs;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void buttonClick(View view){
        Button button = (Button) view;
        int id = button.getId();
        String openRegular  = mGlobals.mPacks.get(0).getFile();
        String openMania    = mGlobals.mPacks.get(1).getFile();

        try {
            if (id == R.id.button_play) {
                List<RegularChallenge> packs = new ArrayList<RegularChallenge>();
                openRegular(getAssets().open(openRegular), packs);
                startActivity(new Intent(this, PlayActivity.class));
            } else if (id == R.id.button_options) {
                startActivity(new Intent(this, OptionsActivity.class));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openRegular(InputStream is, List<RegularChallenge> packs){
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
    private void readPack(InputStream is, List<Pack> packs) {
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
