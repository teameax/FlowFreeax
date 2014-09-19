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

//Called when the activity is first created.
public class MainActivity extends Activity {
    xmlReader reader = new xmlReader();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        try {
            List<Pack> packs = new ArrayList<Pack>();
            reader.readPack(getAssets().open("packs/packs.xml"), packs);
            reader.mGlobals.mPacks = packs;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void buttonClick(View view){
        Button button       = (Button) view;
        int id              = button.getId();
        String openRegular  = reader.mGlobals.mPacks.get(0).getFile();
        String openMania    = reader.mGlobals.mPacks.get(1).getFile();

        try {
            if (id == R.id.button_play) {
                List<RegularChallenge> pac = new ArrayList<RegularChallenge>();
                reader.openRegular(getAssets().open(openRegular), pac);
                startActivity(new Intent(this, PlayActivity.class));
            }

            else if (id == R.id.button_options) {
                startActivity(new Intent(this, OptionsActivity.class));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
