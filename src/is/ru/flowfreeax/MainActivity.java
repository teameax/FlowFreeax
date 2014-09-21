package is.ru.flowfreeax;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.*;
import org.w3c.dom.Element;
import android.view.View;
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

    public void playSound() {
        final MediaPlayer mp = new MediaPlayer();
        if(mp.isPlaying()){
            mp.stop();
            mp.reset();
        }
        try {
            mp.reset();
            AssetFileDescriptor afd;
            afd = getAssets().openFd("button3.mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            mp.start();
        }
        catch (IllegalStateException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        PreferenceManager.setDefaultValues(this, R.xml.options, false);
        try {
            List<Pack> packs = new ArrayList<Pack>();
            reader.readPack(getAssets().open("packs/packs.xml"), packs);
            reader.mGlobals.mPacks = packs;
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public void buttonClick(final View view){
        Button button       = (Button) view;
        int id              = button.getId();
        String openRegular  = reader.mGlobals.mPacks.get(0).getFile();
        String openMania    = reader.mGlobals.mPacks.get(1).getFile();

        try {
            if (id == R.id.button_play) {
                playSound();
                List<RegularChallenge> pac = new ArrayList<RegularChallenge>();
                reader.openRegular(getAssets().open(openRegular), pac);
                startActivity(new Intent(this, PlayActivity.class));
            }
            else if(id == R.id.button_timeTrial){
                playSound();
            }
            else if (id == R.id.button_options) {
                playSound();
                startActivity(new Intent(this, OptionsActivity.class));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
